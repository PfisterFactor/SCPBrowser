package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

// This can probably be converted to a Transformer and remove the RenderRule
class ParseNewline(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Newline"
    override val regex: Regex = """([^\n])\n(?!\n)""".toRegex(RegexOption.MULTILINE)
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        return match.groupValues[0]  + text_engine.addToken(rule_name)
    }
}