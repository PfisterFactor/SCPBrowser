package pfister.scpbrowser.scprender.renderrules

import android.net.Uri
import android.text.TextUtils
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RenderImage(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = Config.mapOf(
            "base" to text_engine.local_files_base_url,
            "url_base" to "",
            "css" to "image",
            "css_link" to ""
    )

    override fun render(token: TextToken): String {
        if (token.options == null) return ""
        var src = token.options.get_string("src")!!
        val attr = token.options.get_map_string("attr")!!.toMutableMap()

        // Is the src pointing to an local file?
        if (!src.contains("://")) {
            src = "${conf?.get_string("base").orEmpty()}$src"
        }
        //

        // Is the image clickable?
        val href:String? = attr["link"]?.let { conf?.get_string("url_base") + it }

        // Todo: Wikilink support


        attr.remove("link")

        if (attr.containsKey("align")) {
            if (!attr.containsKey("style"))
                attr["style"] = ""
            else
                attr["style"] += " "

            if (attr["align"] == "center") {
                attr["style"] += "display: block; margin-left: auto; margin-right: auto;"
            }
            else {
                attr["style"] = "float: ${attr["align"]}"
            }

            attr.remove("align")
        }

        var output = """<img src="${TextUtils.htmlEncode(src)}""""

        val css = if (conf!!.containsKey("css")) " class=\"${conf.get_string("css")!!}\"" else ""

        var alt = false
        for ((key,value) in attr) {
            alt = alt || key.toLowerCase() == "alt"

            output += """${TextUtils.htmlEncode(key)}="${TextUtils.htmlEncode(value)}""""
        }
        if (!alt) {
            val alt_url = TextUtils.htmlEncode(Uri.parse(token.options.get_string("src")!!).lastPathSegment)
            output += """ alt="$alt_url""""
        }

        output += "$css />"

        if (href != null) {
            val encoded_href = TextUtils.htmlEncode(href)

            val css_link = if (conf.containsKey("css_link")) " class=\"${conf.get_string("css_link")!!}\"" else ""
            output = """<a$css_link href="$encoded_href">$output</a>""".trimMargin()
        }

        return output



    }
}