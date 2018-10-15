package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseStrikethrough(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Strikethrough"
    override val regex: Regex = """\-\-([^\s](?:.*[^\s])?)\-\-""".toRegex()
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        val start = text_engine.addToken(rule_name,Config.mapOf("type" to "start"))
        val end = text_engine.addToken(rule_name,Config.mapOf("type" to "end"))
        return "$start${match.groupValues[1]}$end"
    }
}