package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scpdisplay.SCPDisplay
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextEngineUtils
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseFreelink(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Freelink"
    override val regex: Regex = """\[\[\[([^\]\|\[\#]+)\s*(\#[A-Za-z][-A-Za-z0-9_:.]*)?\s*(\|[^\]\|\[\#]*)?\]\]\]""".toRegex()
    override val conf: Config? = null

    override fun process(match: MatchResult): CharSequence {
        var page = match.groupValues[1]
        val anchor = match.groupValues.getOrNull(2)
        var text = match.groupValues[3].trim()

        val nonbr = page.startsWith("_")
        if (nonbr)
            page = page.substring(1)

        var site:String? = null
        val pos = page.indexOf("::")
        if (pos != -1) {
            site = TextEngineUtils.toUnixName(page.substring(0,pos))
            page = if (page.length-1 < pos+2)
                site
            else
                page.substring(pos+2)

        }
        var textFromTitle = false

        if (text.isEmpty()) {
            text = page
            val pos = text.indexOf(":")
            if (pos != -1)
                text = text.substring(pos+1)
        }
        else if (text == "|")
            textFromTitle = true
        else
            text = text.drop(1)

        if (page.startsWith(SCPDisplay.HOME_PAGE))
            page = page.replace(SCPDisplay.HOME_PAGE,"")

        page = TextEngineUtils.toUnixName(page)

        val options = Config.mapOf(
                "site" to site.orEmpty(),
                "page" to page,
                "text" to text.trim(),
                "anchor" to anchor.orEmpty(),
                "textFromTitle" to textFromTitle,
                "nonbr" to nonbr

        )

        return text_engine.addToken(rule_name,options)

    }
}