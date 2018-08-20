package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine
import pfister.scpbrowser.scprender.Transformer

class ParseAlign(override val text_engine: TextWikiEngine) : ParseDefault(), Transformer {
    override val rule_name: String = "Align"
    override val regex: Regex = """\[\[(\/)?(=|<|>|==)\]\]""".toRegex(RegexOption.IGNORE_CASE)
    override val conf: Config? = null

    override fun transform() {
        regexReplace(regex) {
            val start = it.groupValues[1].isEmpty()

            if (start) {
                val align = when(it.groupValues[2]) {
                    "=" -> "center"
                    ">" -> "left"
                    "<" -> "right"
                    "==" -> "justify"
                    else -> return@regexReplace ""
                }
                """<div style="text-align: $align;">"""
            }
            else
                "</div>"
        }
    }

    override fun parse() {}
}