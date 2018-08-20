package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderEmphasis(override val text_engine: TextWikiEngine) :
        RenderDefaultTag(text_engine,"<em%css%>","</em>") {

    override val conf: Config? = Config.mapOf("css" to "")
}