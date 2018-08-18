package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseURL(override val text_engine:TextWikiEngine) : ParseDefault() {
    override val conf: Config? = Config.mapOf(
        "schemes" to arrayOf(
                "http://",
                "https://",
                "ftp://",
                "gopher://",
                "news://",
                "mailto:",
                "mms://"
        )
    )

    override val regex: Regex
    override val rule_name: String = "Url"

    private var footnote_count: Int = 0

    init {
        val quotedSchemas = (conf!!.get_array_string("schemes")!!).map { x -> Regex.escape(x) }
        val regexstr = quotedSchemas.joinToString("|")
        regex = ("($regexstr)" +
                "(" +
                "[^ \\/\"\'${text_engine.DELIM}]*\\/" +
                ")*" +
                "[^ \\t\\n\\/\"\'${text_engine.DELIM}]*" +
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
        val options = Config.mapOf(
                "type" to "inline",
                "href" to match.groupValues[2],
                "text" to match.groupValues[2]
        )
        return "${match.groupValues[1]}${text_engine.addToken(rule_name,options)}${match.groupValues[5]}"
    }

    private fun processDescr(match:MatchResult):CharSequence {
        val options = Config.mapOf(
                "type" to "descr",
                "href" to match.groupValues[1],
                "text" to match.groupValues[4]
        )

        return text_engine.addToken(rule_name,options)
    }

    private fun processFootnote(match:MatchResult):CharSequence {
        footnote_count++
        val options = Config.mapOf(
                "type" to "footnote",
                "href" to match.groupValues[1],
                "text" to footnote_count
        )
        return text_engine.addToken(rule_name,options)
    }


}