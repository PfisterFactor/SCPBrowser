package pfister.scpbrowser.scprender.parserules

import android.text.TextUtils
import pfister.scpbrowser.scpdisplay.SCPDisplay
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

// Todo: Convert to transformer
class ParseInclude(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Include"
    override val regex: Regex = """^\[\[include ([a-zA-Z0-9\s\-:]+?)(\s+.*?)?(?:\]\])""".toRegex(setOf(RegexOption.IGNORE_CASE,RegexOption.DOT_MATCHES_ALL,RegexOption.MULTILINE))
    override val conf: Config? = null

    override fun parse() {
        var level = 0
        do {
            val old_source = "" + text_engine.source
            text_engine.source = text_engine.source.replace(regex) {process(it)}
            level++

        } while (old_source != text_engine.source && level < 5)
    }

    override fun process(match: MatchResult): CharSequence {
        val url = SCPDisplay.HOME_PAGE + match.groupValues[1]

        val page = SCPDisplay.downloadAndPrepareSCP(text_engine.okhttp,url) ?: return "\n\n[[div class=\"error-block\"]]\nPage to be included ${TextUtils.htmlEncode(match.groupValues[1])} can not be found!\n[[/div]]\n\n"


        val subs = match.groupValues.getOrNull(2)?.split("|")?.map { it.trim() } ?: return page.Page_Source

        var output = page.Page_Source

        for (sub in subs) {
            val pos = sub.indexOf('=')
            if (pos != -1) {
                val field = sub.substring(0,pos)
                val field_value = sub.substring(pos+1)
                if (field.isNotEmpty() && field_value.isNotEmpty() && field.matches("""^[a-z0-9\-\_]+$""".toRegex(RegexOption.IGNORE_CASE))) {
                    output = output.replace("{$$field}",field_value)
                }
            }
        }

        return "\n\n" + output +"\n\n"




    }
}