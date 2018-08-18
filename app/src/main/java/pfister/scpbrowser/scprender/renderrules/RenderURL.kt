package pfister.scpbrowser.scprender.renderrules

import android.net.Uri
import android.text.TextUtils
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderURL(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = Config.mapOf(
            "target" to "_blank",
            "images" to true,
            "img_ext" to arrayOf("jpg","jpeg","gif","png"),
            "css_inline" to "",
            "css_footnote" to "",
            "css_descr" to "",
            "css_img" to ""
    )

    override fun render(token: TextToken): String {
        val type = token.options?.get_string("type")!!
        var href = token.options.get_string("href")!!
        var text = token.options.get_string("text")!!

        val pos = href.lastIndexOf('.')
        val ext = href.substring(pos+1)



        href = TextUtils.htmlEncode(href)

        var start = ""
        var end = ""


        val image_ext = conf!!.get_array_string("img_ext")!!



        // If its an image link
        if (image_ext.any { ext.contains(it) }) {
            if (text == "") {
                text = Uri.parse(href).lastPathSegment
                text = TextUtils.htmlEncode(text)
            }
            val css = """ class="$type""""
            start = "<img$css src=\"$href\" alt=\"$text\" title=\"$text\" /><!-- "
            end = " -->"
        }
        else {
            var target = if (href[0] == '#' || href.startsWith("mailto:")) {
                ""
            } else
                conf.get_string("target")

            text = TextUtils.htmlEncode(text)
            val css = """ class="$type" """
            start = "<a$css href=\"$href\""

            if (target != null && target != "_self") {
                target = TextUtils.htmlEncode(target)
                start += " onclick=\"window.open(this.href,'$target');"
                start += " return false;\""
            }
        }

        start += ">"
        end += "</a>"

        if (type == "footnote") {
            start = "<sup>$start"
            end = "$end</sup>"
        }

        return "$start$text$end"





    }
}