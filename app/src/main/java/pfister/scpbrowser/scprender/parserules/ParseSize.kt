package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseSize(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Size"
    override val regex: Regex = """\[\[size\s([^\]]+)\]\]""".toRegex(setOf(RegexOption.IGNORE_CASE,RegexOption.DOT_MATCHES_ALL))
    override val conf: Config? = Config.mapOf(
            "allowedSizes" to arrayOf<Regex>(
                    """^[0-9\.]{1,5}(em|px|%)$""".toRegex(),
                    """^xx\-small$""".toRegex(),
                    """^x\-small$""".toRegex(),
                    """^small$""".toRegex(),
                    """^medium$""".toRegex(),
                    """^large$""".toRegex(),
                    """^x\-large$""".toRegex(),
                    """^xx\-large$""".toRegex(),
                    """^smaller$""".toRegex(),
                    """^larger$""".toRegex()
            )
    )
    override fun parse() {
        val start_regex = regex
        val end_regex = """\[\[/size\]\]""".toRegex(setOf(RegexOption.IGNORE_CASE,RegexOption.DOT_MATCHES_ALL))
        regexReplace(start_regex) {process(it)}
        regexReplace(end_regex) {process_end_size() }
    }

    override fun process(match: MatchResult): CharSequence {
        val size = match.groupValues[1].trim()

        val allowedSizes = conf?.get_array<Regex>("allowedSizes") ?: return match.groupValues[0]

        var good = false
        for (reg in allowedSizes) {
            good = reg.containsMatchIn(size)
            if (good) break
        }

        if (!good) return match.groupValues[0]

        val options = Config.mapOf(
                "size" to size,
                "type" to "start"
        )
        return text_engine.addToken(rule_name,options)
    }

    private fun process_end_size(): CharSequence = text_engine.addToken(rule_name,Config.mapOf("type" to "end"))


}