package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderDiv(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = null

    override fun render(token: TextToken): String {
        return when (token.options?.get_string("type")) {
            "end_div" -> "</div>"
            "start_div_start" -> "<div "
            "start_div_end" -> ">"
            else -> ""
        }
    }
}