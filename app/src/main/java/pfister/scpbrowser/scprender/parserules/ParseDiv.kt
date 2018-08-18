package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseDiv(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Div"
    override val regex: Regex = """(\[\[)(div\s.*?)(\]\])""".toRegex()

    override val conf: Config? = null

    val end_regex = """\[\[\/div]]""".toRegex()
    override fun parse() {
        regexReplace(regex) {x -> process(x)}
        regexReplace(end_regex) { process_end_div() }

    }

    override fun process(match: MatchResult): CharSequence {

        val text = match.groupValues[2]

        val token_start = text_engine.addToken(rule_name, Config.mapOf("type" to "start_div_start"))

        val token_end = text_engine.addToken(rule_name, Config.mapOf("type" to "start_div_end"))

        return "$token_start$text$token_end"
    }

    private fun process_end_div(): CharSequence = text_engine.addToken(rule_name, Config.mapOf("type" to "end_div"))







}