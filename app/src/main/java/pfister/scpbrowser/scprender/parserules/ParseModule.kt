package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine

class ParseModule(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val conf: Config? = null
    override val regex: Regex = """^\[\[module\s([a-z0-9_\-\/]+)(\s+.*?)?\]\] *\n(?:(.*?)\[\[\/module\]\])?""".toRegex(setOf(RegexOption.IGNORE_CASE,RegexOption.MULTILINE,RegexOption.DOT_MATCHES_ALL))
    override val rule_name: String = "Module"


    // Overridden to provide support for nested modules
    override fun parse() {
        do {
            val old_text = "" + text_engine.source
            regexReplace(regex) { process(it) }
        } while (old_text != text_engine.source)
    }

    override fun process(match: MatchResult): CharSequence {
        val con = match.groupValues[0]

        val module_title_regex = """^\[\[module\s([a-z0-9_\-\/]+)(\s+.*?)?\]\]""".toRegex()
        if (module_title_regex.matches(con)) {
            val return_regex = """^\[\[module""".toRegex()
            return return_regex.replace(con,"[[module654")
        }


        val options = Config.mapOf(
                "moduleName" to match.groupValues[1].trim(),
                "attr" to match.groupValues[2].trim(),
                "body" to match.groupValues[3].trim()
        )

        return "\n\n${text_engine.addToken(rule_name,options)}\n\n"
    }
}