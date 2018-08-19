package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseBreak(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Break"
    override val regex: Regex = """ _\n""".toRegex()
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        return text_engine.addToken(rule_name)
    }
}