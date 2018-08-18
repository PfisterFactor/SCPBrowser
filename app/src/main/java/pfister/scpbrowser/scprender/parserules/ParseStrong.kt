package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseStrong(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val regex: Regex = """\*\*([^\s\n](?:.*[^\s\n])?)\*\*""".toRegex()
    override val rule_name: String = "Strong"
    override val conf: Config? = null

    override fun process( match: MatchResult): CharSequence {
        val start = text_engine.addToken(rule_name, Config.mapOf("type" to "start"))
        val end = text_engine.addToken(rule_name, Config.mapOf("type" to "end"))

        val text = match.groupValues[1]
        return "$start$text$end"
    }


}