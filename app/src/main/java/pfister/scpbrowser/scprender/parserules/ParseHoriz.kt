package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseHoriz(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Horiz"
    override val regex: Regex = """^([-]{4,})$""".toRegex(RegexOption.MULTILINE)
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        return "\n${text_engine.addToken(rule_name)}\n\n"
    }
}