package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderCenter(override val text_engine: TextWikiEngine) :
        RenderDefaultTag(text_engine,"<p style=\"text-align: center;\">","</p>") {

    override val conf: Config? = null

}
