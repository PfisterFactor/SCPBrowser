package pfister.scpbrowser.scprender.renderrules

import android.net.Uri
import android.text.TextUtils
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RuleImage(override val text_engine: TextWikiEngine) : RuleDefault() {
    override val regex: Regex = """(\[\[image\s+)(.+?)(\]\])""".toRegex(RegexOption.IGNORE_CASE)
    override val rule_name: String = "Image"

    val schemes: String = "http|https|ftp|gopher|news"
    val host_regex: Regex = """(?:[^.\s"\'<\\\${text_engine.delim}\ca-\cz]+\.)*[a-z](?:[-a-z0-9]*[a-z0-9])?\.?""".toRegex()
    val path_regex: Regex = """(?:[^\s"<\\\${text_engine.delim}\ca-\cz]*)?""".toRegex()

    val url_regex: Regex = """#(?:$schemes)://$host_regex$path_regex#""".toRegex()

    override fun process(match: MatchResult): CharSequence {
        val pos = match.groupValues[2].indexOf(' ')

        val options: Map<String,Any>
        if (pos == -1) {
            options = mapOf(
                    "src" to match.groupValues[2],
                    "attr" to mapOf<String,String>()
            )
        }
        else {
            options = mapOf(
                    "src" to match.groupValues[2].substring(0,pos),
                    "attr" to getAttrs(match.groupValues[2].substring(pos+1))
            )

            // If there is a valid link attribute present then don't handle this match
            if ((options["attr"]!! as Map<*, *>).containsKey("link")) {
                @Suppress("UNCHECKED_CAST")
                val link:String = (options["attr"] as Map<String,String>)["link"]!!

                if (link.contains("://")) {
                    if (!url_regex.matches("link"))
                        return match.groupValues[0]
                }
            }
            //
        }
        return text_engine.addToken(rule_name,options)
    }

    override fun render(token: TextToken): String {
        var src = token.getString("src")!!
        val attr = token.getMap("attr")!!.toMutableMap()

        // Is the src pointing to an local file?
        if (!src.contains("://")) {
            src = "${text_engine.local_files_base_url}$src"
        }
        //

        // Is the image clickable?
        val href:String? = attr["link"]

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

        var alt = false
        for ((key,value) in attr) {
            alt = alt || key.toLowerCase() == "alt"

            output += """${TextUtils.htmlEncode(key)}="${TextUtils.htmlEncode(value)}""""
        }
        if (!alt) {
            val alt_url = TextUtils.htmlEncode(Uri.parse(token.getString("src")!!).lastPathSegment)
            output += """ alt="$alt_url""""
        }

        output += "/>"

        if (href != null) {
            val encoded_href = TextUtils.htmlEncode(href)
            output = """<a href="$encoded_href">$output</a>"""
        }

        return output



    }
}