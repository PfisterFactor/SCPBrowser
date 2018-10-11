package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderSize(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = null

    override fun render(token: TextToken): String {
        val type = token.options?.get_string("type") ?: return ""
        return when (type) {
            "start" -> {
                val size = token.options.get_string("size")
                if (size != null) "<span style=\"font-size:$size;\">" else ""
            }
            "end" -> "</span>"
            else -> ""
        }
    }
}