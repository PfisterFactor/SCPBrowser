package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderStrikethrough(override val text_engine:TextWikiEngine): RenderDefaultTag(text_engine,"<span style=\"text-decoration: line-through;\">","</span>") {
    override val conf: Config? = null
}