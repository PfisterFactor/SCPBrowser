package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RuleComment(override val text_engine: TextWikiEngine) : RuleDefault() {
    override val regex: Regex = """\[!\-\-(?:[^]])*\-\-]""".toRegex()
    override val rule_name: String = "Comment"

    override fun process(match: MatchResult): CharSequence {
        return text_engine.addToken("Comment",null)
    }

    override fun render(token: TextToken): String {
        return ""
    }
}