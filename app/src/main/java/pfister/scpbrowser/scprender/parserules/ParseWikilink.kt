package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseWikilink(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val regex: Regex = """(?:\[\[\[)(([^#,\[,\],\|]*)(?:\|\s?(.*))?(?:#?(.*)?))(?:\]\]\])""".toRegex()
    override val rule_name: String = "Wikilink"
    override val conf: Config? = Config.mapOf(
        "ext_chars" to false,
            "utf-8" to false
    )

//    init {
//        val upper: String
//        val lower: String
//        val either: String
//
//        when {
//            conf?.get_bool("utf-8")!! -> {
//                upper  = """A-Z\p{Lu}"""
//                lower = """a-z0-9\p{Ll}"""
//                either = """A-Za-z0-9\p{L}"""
//            }
//            conf.get_bool("ext_chars")!! -> {
//                upper = """A-z\xc0-\xde"""
//                lower = """a-z0-9\xdf-\xfe"""
//                either = """A-Za-z0-9\xc0-\xfe"""
//            }
//            else -> {
//                upper = "A-Z"
//                lower = "a-z0-9"
//                either = upper + lower
//            }
//        }
//
//        regex = """(!?[$upper][$either]*[$lower]+[$upper][$either]*)((\#[$either]([-_$either:.]*[-_$either])?)?)""".toRegex()
//    }


    override fun process(match: MatchResult): CharSequence {


        val options = Config.mapOf(
                "text" to if (match.groupValues[3].isNotEmpty()) match.groupValues[3] else match.groupValues[1],
                "page" to match.groupValues[2],
                "anchor" to match.groupValues[4]
        )

        return text_engine.addToken(rule_name,options)
    }

}