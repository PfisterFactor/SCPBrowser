package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RuleStrong(override val text_engine: TextWikiEngine) : RenderRule {
    override val regex: Regex = """\*\*([^\s\n](?:.*[^\s\n])?)\*\*""".toRegex()
    override val rule_name: String = "strong"

    override fun parse() {
        text_engine.source = regex.replace(text_engine.source) { x -> process(x) }

    }

    override fun process( match: MatchResult): CharSequence {
        val start = text_engine.addToken(rule_name, mapOf("type" to "start"))
        val end = text_engine.addToken(rule_name, mapOf("type" to "end"))

        val text = match.groupValues[1]
        return "$start$text$end"
    }

    override fun render(token: TextToken): String {
        return when (token.options["type"]) {
            "start" -> {
                val css = "" // Todo
                "<strong$css>"
            }
            "end" -> "</strong>"
            else -> ""
        }
    }

}