package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine
import pfister.scpbrowser.scprender.Transformer

class ParseDiv(override val text_engine: TextWikiEngine) : ParseDefault(), Transformer {

    override val rule_name: String = "Div"
    override val regex: Regex = """(\n)?\[\[div\s(.*?)?\]\] *\n""".toRegex(setOf(RegexOption.MULTILINE,RegexOption.DOT_MATCHES_ALL,RegexOption.IGNORE_CASE))

    override val conf: Config? = null

    val div_end_regex = """\[\[\/div\]\] *""".toRegex(setOf(RegexOption.MULTILINE,RegexOption.DOT_MATCHES_ALL,RegexOption.IGNORE_CASE))
    val div_space_regex = """\[\[\/div\]\](?:\s{2,})?\[""".toRegex(setOf(RegexOption.IGNORE_CASE))

    // Remove spaces between divs
    override fun transform() {
        text_engine.source = div_space_regex.replace(text_engine.source,"[[/div]]\n[")
    }

    override fun parse() {
        regexReplace(regex) {x -> process(x)}
        regexReplace(div_end_regex) { process_end_div() }

    }

    override fun process(match: MatchResult): CharSequence {

        val text = match.groupValues[2]

        val token_start = text_engine.addToken(rule_name, Config.mapOf("type" to "start_div_start"))

        val token_end = text_engine.addToken(rule_name, Config.mapOf("type" to "start_div_end"))

        return match.groupValues[1] + match.groupValues[1] + "$token_start$text$token_end" + "\n\n"
    }

    private fun process_end_div(): CharSequence = "\n\n" + text_engine.addToken(rule_name, Config.mapOf("type" to "end_div"))







}