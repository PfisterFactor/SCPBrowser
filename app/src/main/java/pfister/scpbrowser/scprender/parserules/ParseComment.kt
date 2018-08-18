package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseComment(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val regex: Regex = """\[!\-\-(?:[^]])*\-\-]""".toRegex()
    override val rule_name: String = "Comment"
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        return text_engine.addToken("Comment",null)
    }

}