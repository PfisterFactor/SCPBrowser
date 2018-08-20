package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderHoriz(override val text_engine: TextWikiEngine) :
        RenderDefaultTag(text_engine,"<hr%css% />\n") {

    override val conf: Config? = Config.mapOf("css" to "")

}