package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseCenter(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val conf: Config? = null
    override val regex: Regex = """\n\= (.*?)\n""".toRegex()
    override val rule_name: String = "Center"

    override fun process(match: MatchResult): CharSequence {
        val start = text_engine.addToken(rule_name,Config.mapOf("type" to "start"))

        val end = text_engine.addToken(rule_name,Config.mapOf("type" to "end"))

        return "\n\n$start${match.groupValues[1]}$end\n\n"
    }


}