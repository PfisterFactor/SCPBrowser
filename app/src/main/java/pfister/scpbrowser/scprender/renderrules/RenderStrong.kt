package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderStrong(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = Config.mapOf(
            "css" to ""
    )

    override fun render(token: TextToken): String {
        return when (token.getString("type")) {
            "start" -> {
                val css = " class=${conf!!.get_string("css")}"
                if (conf.containsKey("css")) "<strong$css>" else "<strong>"
            }
            "end" -> "</strong>"
            else -> ""
        }
    }
}