package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RuleDiv(override val text_engine: TextWikiEngine) : RuleDefault() {
    override val rule_name: String = "Div"
    override val regex: Regex = """(\[\[)(div\s.*?)(\]\])""".toRegex()

    val end_regex = """\[\[\/div]]""".toRegex()
    override fun parse() {
        regexReplace(regex) {x -> process(x)}
        regexReplace(end_regex) {x -> process_end_div(x)}

    }

    override fun process(match: MatchResult): CharSequence {

        val text = match.groupValues[2]

        val token_start = text_engine.addToken(rule_name, mapOf("type" to "start_div_start"))

        val token_end = text_engine.addToken(rule_name, mapOf("type" to "start_div_end"))

        return "$token_start$text$token_end"
    }

    fun process_end_div(match:MatchResult): CharSequence = text_engine.addToken(rule_name, mapOf("type" to "end_div"))

    override fun render(token: TextToken): String {
        return when (token.getString("type")) {
            "end_div" -> "</div>"
            "start_div_start" -> "<"
            "start_div_end" -> ">"
            else -> ""
        }
    }





}