package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

// Helper class to render basic tags with optional css
// "type" optionally can be specified in the token options for enclosing tags
// "type" can be "end" to get the end tag, and anything else for the start tag
// if "css" is defined in the conf variable, then it replaces CSS_SYMBOL in the start_tag string with the css
abstract class RenderDefaultTag(override val text_engine: TextWikiEngine, private val start_tag:String, private val end_tag:String = "") : RenderRule {
    companion object {
        const val CSS_SYMBOL = "%css%"
    }
    override fun render(token: TextToken): String {
        val type = token.options?.get_string("type")

        return when (type) {
            "end" -> end_tag
            else -> {
                val css_str = conf?.get_string("css")
                val css = when (css_str) {
                    "" -> ""
                    is String -> " class=\"$css_str\""
                    else -> ""
                }
                start_tag.replace(CSS_SYMBOL,css)
            }
        }

    }




}