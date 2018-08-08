package pfister.scpbrowser.scprender.renderrules

import android.net.Uri
import android.text.TextUtils
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

class RuleURL(override val text_engine:TextWikiEngine) : RuleDefault() {
    override val regex: Regex
    override val rule_name: String = "Url"

    private val image_ext = arrayOf("jpg","jpeg","gif","png")

    private val schemas = arrayOf(
            "http://",
            "https://",
            "ftp://",
            "gopher://",
            "news://",
            "mailto:",
            "mms://"
    )

    private var footnote_count: Int = 0

    init {
        val quotedSchemas = schemas.map { x -> Regex.escape(x) }
        val regexstr = quotedSchemas.joinToString("|")
        regex = ("($regexstr)" +
                "(" +
                "[^ \\/\"\'${text_engine.delim}]*\\/" +
                ")*" +
                "[^ \\t\\n\\/\"\'${text_engine.delim}]*" +
                "[A-Za-z0-9\\/?=&~_#]").toRegex()

    }
    override fun parse() {
        // Described-reference (named) URLs
        val descr_regex = """\[($regex) ([^\]]+)\]""".toRegex()
        regexReplace(descr_regex) {x -> processDescr(x)}

        // Footnotes
        val footnote_regex = """\[($regex)\]""".toRegex()
        regexReplace(footnote_regex) {x -> processFootnote(x)}

        // Normal inline URLs
        val normal_regex = """(^|[^A-Za-z])($regex)(.*?)""".toRegex()
        regexReplace(normal_regex) { x -> process(x)}

    }

    override fun process(match: MatchResult): CharSequence {
        val options = mutableMapOf(
                "type" to "inline",
                "href" to match.groupValues[2],
                "text" to match.groupValues[2]
        )
        return "${match.groupValues[1]}${text_engine.addToken(rule_name,options)}${match.groupValues[5]}"
    }

    private fun processDescr(match:MatchResult):CharSequence {
        val options = mutableMapOf(
                "type" to "descr",
                "href" to match.groupValues[1],
                "text" to match.groupValues[4]
        )

        return text_engine.addToken(rule_name,options)
    }

    private fun processFootnote(match:MatchResult):CharSequence {
        footnote_count++
        val options = mutableMapOf(
                "type" to "footnote",
                "href" to match.groupValues[1],
                "text" to footnote_count
        )
        return text_engine.addToken(rule_name,options)
    }

    override fun render(token: TextToken): String {
        val type = token.getString("type")!!
        var href = token.getString("href")!!
        var text = token.getString("text")!!

        val pos = href.lastIndexOf('.')
        val ext = href.substring(pos+1)



        href = TextUtils.htmlEncode(href)

        var start = ""
        var end = ""

        // If its an image link
        if (image_ext.any { image_ext -> ext.contains(image_ext) }) {
            if (text == "") {
                text = Uri.parse(href).lastPathSegment
                text = TextUtils.htmlEncode(text)
            }
            start = "<img src=\"$href\" alt=\"$text\" title=\"$text\" /><!-- "
            end = " -->"
        }
        else {
            text = TextUtils.htmlEncode(text)
            start = "<a href=\"$href\""
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