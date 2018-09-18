package pfister.scpbrowser.scprender.renderrules

import android.text.TextUtils
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderRaw(override val text_engine: TextWikiEngine) : RenderRule {

    override val conf: Config? = null

    override fun render(token: TextToken): String {
        val text = token.options?.get_string("text").orEmpty()
        return TextUtils.htmlEncode(text)
    }

}