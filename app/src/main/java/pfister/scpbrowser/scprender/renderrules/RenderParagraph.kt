package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderParagraph(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = Config.mapOf(
            "css" to ""
    )

    override fun render(token: TextToken): String {
        val type = token.options?.get_string("type")

        return when (type) {
            "start" -> {
                val css_str = conf?.get_string("css")
                val css = when (css_str) {
                    "" -> ""
                    is String -> " class=\"$css_str\""
                    else -> ""
                }
                "<p$css>"
            }
            "end" -> "</p>\n\n"
            else -> ""
        }
    }
}