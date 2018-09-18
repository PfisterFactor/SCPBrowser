package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseRaw(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Raw"
    override val regex: Regex = """(?<!@)@@(.*?[^@]??)@@""".toRegex()
    override val conf: Config? = null


    override fun process(match: MatchResult): CharSequence {
        val options = Config.mapOf("text" to match.groupValues[1])
        return text_engine.addToken(rule_name,options)
    }
}