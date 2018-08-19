package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseDelimiter(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Delimiter"
    override val regex: Regex = """${text_engine.DELIM}""".toRegex()
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        return text_engine.addToken(rule_name,Config.mapOf("delim" to text_engine.DELIM))
    }
}