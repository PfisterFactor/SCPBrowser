package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.ParseRule

class CustomMatch(override val value:String, override val range:IntRange, override val groups: MatchGroupCollection): MatchResult {
    override fun next(): MatchResult? {
        TODO("not implemented")
    }
    override val groupValues: List<String> = groups.fold(emptyList()) {acc,v -> if (v != null) acc.plus(v.value) else acc }
}



class ListMatchGroupCollection(private val elements: List<MatchGroup?>) : MatchGroupCollection {
    override val size: Int = elements.size
    override fun iterator(): Iterator<MatchGroup?> = elements.iterator()
    override fun isEmpty(): Boolean = elements.isEmpty()
    override fun get(index: Int): MatchGroup? = elements[index]
    override fun contains(element: MatchGroup?): Boolean = elements.contains(element)
    override fun containsAll(elements: Collection<MatchGroup?>): Boolean = elements.containsAll(elements)

}
abstract class ParseDefault : ParseRule {

    override fun parse() {
        regexReplace(regex) {x -> process(x)}
    }

    override fun process(match: MatchResult): CharSequence {
        return match.groupValues[0]
    }



    fun getAttrs(text:String): Map<String,String> {
        fun stripslashes(text:String):String =
            text.replace("\\\\", "!~!").replace("\\", "").replace("!~!", "\\")

        val tmp = text.trim().split("=\"")

        val attrs = mutableMapOf<String,String>()
        var key = ""

        for ((index,value) in tmp.withIndex()) {
            if (index == 0) {
                key = value.trim()
                continue
            }

            val pos = value.lastIndexOf('"')
            attrs[key] = stripslashes(value.substring(0,pos)).trim()
            key = value.substring(pos+1).trim()

        }
        return attrs.toMap()
    }
    // Looks for nested tags
    // Like <div> <div> </div> </div>
    // start_reg is the regex to match to the beginning tag
    // end_rag is the regex to match to the end tag
    // group_reg matches on the tag for groups
    fun recursiveMatch(text:String, start_reg:Regex, group_reg:Regex, end_reg:Regex):Array<MatchResult> {

        // If one range contains another
        fun containsRange(range:IntRange,inner_range:IntRange):Boolean =
                range.contains(inner_range.start) && range.contains(inner_range.endInclusive)

        // Contains start tag -> end tag pairings
        val match_map:MutableMap<MatchResult,MatchResult> = mutableMapOf()

        val start_matches = start_reg.findAll(text)
        val end_matches = end_reg.findAll(text).toMutableList()

        // Iterate through the start tags
        for (start_match in start_matches) {
            // Store the end tag that would make the tag's inner text contain the least amount of internal start tags
            var min:Pair<MatchResult?,Int> = (null to Int.MAX_VALUE)
            for (end_match in end_matches) {
                // Find the range between the start tag and end tag, inclusive
                val range = IntRange(start_match.range.endInclusive+1,end_match.range.start)
                // Count the number of start tags that are inside that range
                val num_of_tags = start_matches.filter { containsRange(range,it.range) }.count()

                if (num_of_tags <= min.second)
                    min = (end_match to num_of_tags)

            }

            // Store whatever end tag that resulted in the best pair
            if (min.first != null) {
                match_map[start_match] = min.first!!
                end_matches.remove(min.first!!)
            }


        }

        val returnList:MutableList<CustomMatch> = mutableListOf()

        for ((start,end) in match_map) {
            // Construct a range for the result
            val range = IntRange(start.range.start,end.range.endInclusive)
            val value = text.substring(range)
            // Take the groupings from the grouping regex
            val groups: MatchGroupCollection? = ListMatchGroupCollection(listOf<MatchGroup?>(MatchGroup(value,range))
                    .plus(group_reg.find(value)?.groups?.map {
                        if (it != null)
                            MatchGroup(it.value,IntRange(it.range.start + range.start,it.range.endInclusive+range.start-1))
                        else null
                    } ?: emptyList()))

            if (groups != null)
                returnList.add(CustomMatch(text,range,groups))
            else
                returnList.add(CustomMatch(text,range, ListMatchGroupCollection(emptyList())))

        }

        return returnList.toTypedArray()



    }

    fun regexReplace(reg: Regex,callback: (MatchResult) -> CharSequence) {
        text_engine.source = reg.replace(text_engine.source) { x -> callback(x) }
    }


}