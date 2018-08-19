package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderCenter(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = null

    override fun render(token: TextToken): String {
        return when (token.options!!["type"]) {
            "start" -> "<p style=\"text-align: center;\">"
            "end" -> "</p>"
            else -> ""
        }
    }
}
