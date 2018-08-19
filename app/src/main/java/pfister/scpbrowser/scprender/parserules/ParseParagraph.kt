package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseParagraph(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Paragraph"
    override val regex: Regex = """^.*?\n\n""".toRegex(RegexOption.MULTILINE)
    override val conf: Config? = Config.mapOf(
            "skip" to arrayOf(
                    "blockquote",
                    "code",
                    "heading",
                    "horiz",
                    "deflist",
                    "table",
                    "list",
                    "toc"
            )
    )

    override fun process(match: MatchResult): CharSequence {
        val skip = conf?.get_array_string("skip")

        if (match.groupValues[0].trim().isEmpty())
            return ""

        val delimiter_regex = """(?:${TextWikiEngine.DELIM})(\d+?)(?:${TextWikiEngine.DELIM})""".toRegex()
        val delimiters = delimiter_regex.findAll(match.groupValues[0])

        for (delim_match in delimiters) {
            val token_id = delim_match.groupValues[1].toInt()
            val token_type = text_engine.getToken(token_id)?.rule_name
            if (skip?.contains(token_type?.toLowerCase())!!)
                return match.groupValues[0]
        }

        val start = text_engine.addToken(rule_name,Config.mapOf(
                "type" to "start"
        ))

        val end = text_engine.addToken(rule_name,Config.mapOf(
                "type" to "end"
        ))

        return "$start${match.groupValues[0].trim()}$end"
    }


}