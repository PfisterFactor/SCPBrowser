package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine
import pfister.scpbrowser.scprender.Transformer

class ParseComment(override val text_engine: TextWikiEngine) : ParseDefault(), Transformer {
    override val regex: Regex = """\[!\-\-.+?\-\-]""".toRegex(RegexOption.DOT_MATCHES_ALL)
    override val rule_name: String = "Comment"
    override val conf: Config? = null


    override fun transform() {
        regexReplace(regex) { "" }
    }

    override fun parse() {}
    override fun process(match: MatchResult): CharSequence = ""

}