package pfister.scpbrowser.scprender.parserules

import android.text.TextUtils
import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseHeading(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val rule_name: String = "Heading"
    override val regex: Regex = """^(\+{1,6}) (.*)""".toRegex(RegexOption.MULTILINE)
    override val conf: Config? = Config.mapOf(
            "id_prefix" to "toc"
    )

    var header_ID:Int = 0
    override fun parse() {
        header_ID = 0
        super.parse()
    }

    override fun process(match: MatchResult): CharSequence {
        val prefix = TextUtils.htmlEncode(conf!!.get_string("id_prefix").orEmpty())

        val start = text_engine.addToken(rule_name,Config.mapOf(
                "type" to "start",
                "level" to match.groupValues[1].length,
                "text" to match.groupValues[2],
                "id" to prefix + header_ID
        ))
        header_ID++

        val end = text_engine.addToken(rule_name,Config.mapOf(
                "type" to "end",
                "level" to match.groupValues[1].length
        ))

        return "$start${match.groupValues[2]}$end\n"
    }
}