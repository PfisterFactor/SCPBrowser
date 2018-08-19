package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderHeading(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = Config.mapOf(
            "css" to arrayOf<String>()
    )

    override fun render(token: TextToken): String {
        val type = token.options?.get_string("type")!!
        val level = token.options.get_int("level")!!
        val text = token.options.get_string("text")
        val id = token.options.get_string("id")

        return when (type) {
            "start" -> {
                val css_str = conf!!.get_array_string("css")?.getOrNull(level)
                val css = when (css_str) {
                    "" -> ""
                    is String -> " class=\"$css_str\""
                    else -> ""
                }
                """<h$level$css id="$id">"""
            }
            "end" -> {
                "</h$level>"
            }
            else -> ""
        }
    }
}