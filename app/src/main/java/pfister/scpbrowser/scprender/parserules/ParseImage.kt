package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseImage(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val conf: Config? = Config.mapOf(
            "schemes" to "http|https|ftp|gopher|news",
            "host_regex" to """(?:[^.\s"\'<\\\${text_engine.DELIM}\ca-\cz]+\.)*[a-z](?:[-a-z0-9]*[a-z0-9])?\.?""",
            "path_regex" to """(?:[^\s"<\\\${text_engine.DELIM}\ca-\cz]*)?"""
    )
    override val regex: Regex = """(\[\[image\s+)(.+?)(\]\])""".toRegex(RegexOption.IGNORE_CASE)
    override val rule_name: String = "Image"

    val url_regex: Regex = """#(?:${conf!!.get_string("schemes")!!})://${conf.get_string("host_regex")!!}${conf.get_string("path_regex")!!}#""".toRegex()

    override fun process(match: MatchResult): CharSequence {
        val pos = match.groupValues[2].indexOf(' ')

        val options: Config
        if (pos == -1) {
            options = Config.mapOf(
                    "src" to match.groupValues[2],
                    "attr" to mapOf<String,String>()
            )
        }
        else {
            options = Config.mapOf(
                    "src" to match.groupValues[2].substring(0,pos),
                    "attr" to getAttrs(match.groupValues[2].substring(pos+1))
            )

            // If there is a valid link attribute present then don't handle this match
            if ((options["attr"]!! as Map<*, *>).containsKey("link")) {
                val link:String = options.get_map_string("attr")!!["link"]!!

                if (link.contains("://")) {
                    if (!url_regex.matches("link"))
                        return match.groupValues[0]
                }
            }
            //
        }
        return text_engine.addToken(rule_name,options)
    }


}