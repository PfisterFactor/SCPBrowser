package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderParagraph(override val text_engine: TextWikiEngine) :
        RenderDefaultTag(text_engine,"<p%css%>","</p>\n\n") {

    override val conf: Config? = Config.mapOf("css" to "")

}