package pfister.scpbrowser.scprender.renderrules

import android.text.TextUtils
import pfister.scpbrowser.scpdisplay.SCPDisplay
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderWikilink(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = Config.mapOf(
            "pages" to arrayOf<String>(),
            "view_url" to SCPDisplay.HOME_PAGE,
            "new_url" to "http://www.scp-wiki.net/new-page/",
            "new_text" to "?",
            "new_text_pos" to "after", // before, after, or none
            "css" to "",
            "css_new" to "",
            "exists_callback" to {page:String -> doesPageExist(page)}

    )

    // Stub
    fun doesPageExist(page:String): Boolean {
        return true
    }

    override fun render(token: TextToken): String {
        var page = token.options?.get_string("page")!!
        var text = token.options.get_string("text")!!
        var anchor = token.options.get_string("anchor")!!


        val exists: Boolean = if (conf!!["exists_callback"] is Function<*>)
            conf.get_class<(String) -> Boolean>("exists_callback")?.invoke(page)!!
        else
            true // assume it exists

        val textFromTitle = conf.get_bool("textFromTitle") == true
        if (exists && textFromTitle) {
            val url = SCPDisplay.HOME_PAGE + page
            val details = SCPDisplay.getPageDetails(url)
            text = details?.Page_Name ?: text // Todo: switch this to page title
        }
        else if (textFromTitle)
            text = page

        page = TextUtils.htmlEncode(page.trim())
        text = text.trim()
        anchor = anchor.trim()

        if (conf.get_bool("nonbr") == true)
            text = text.replace(" ", "&nbsp")


        var start:String
        val end:String

        if (exists) {
            val href = conf.get_string("view_url")!! + page + anchor

            val css_string = conf.get_string("css")
            val css = when (css_string) {
                "" -> ""
                is String -> """ class="${css_string}""""
                else -> ""
            }

            start = """<a$css href="$href">"""
            end = "</a>"
        }
        else {
            val href = conf.get_string("new_url")!! + page

            val css_string = conf.get_string("css_new")
            val css = when (css_string) {
                "" -> ""
                is String -> """ class="$css_string""""
                else -> ""
            }

            val new = conf.get_string("new_text")
            val pos = conf.get_string("new_text_pos")

            if (pos == null || new == null) {
                start = """<a$css href="${TextUtils.htmlEncode(href)}">"""
                end = "</a>"
            }
            else if (pos == "before") {
                start = """<a$css href="${TextUtils.htmlEncode(href)}">${TextUtils.htmlEncode(new)}</a>"""
                end = ""
            }
            else {
                start = ""
                end = """<a$css href="${TextUtils.htmlEncode(href)}">${TextUtils.htmlEncode(new)}</a>"""
            }

            if (text.isEmpty()) {
                start += page
            }

        }

        return start + text + end

    }
}