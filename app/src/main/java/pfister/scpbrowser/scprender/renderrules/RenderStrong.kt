package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderStrong(override val text_engine: TextWikiEngine) :
        RenderDefaultTag(text_engine,"<strong%css%>","</strong>") {

    override val conf: Config? = Config.mapOf("css" to "")
}