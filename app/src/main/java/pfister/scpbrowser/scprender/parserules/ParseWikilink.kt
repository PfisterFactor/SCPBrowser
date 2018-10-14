package pfister.scpbrowser.scprender.parserules

/*
class ParseWikilink(override val text_engine: TextWikiEngine) : ParseDefault() {
    override val regex: Regex = """(!?[A-Z][A-Za-z0-9]*[a-z0-9]+[A-Z][A-Za-z0-9]*)((\#[A-Za-z0-9]([-A-Za-z0-9:.]*[-_A-Za-z0-9])?)?)""".toRegex()
    override val rule_name: String = "Wikilink"
    override val conf: Config? = Config.mapOf(
            "ext_chars" to false,
            "utf-8" to false
    )

    override fun parse() {
        val desc_regex = """\[$regex (.+?)\]""".toRegex()
        regexReplace(desc_regex) {processDescr(it)}
    }

    fun processDescr(match:MatchResult): CharSequence {
        return ""
    }
    override fun process(match: MatchResult): CharSequence {


        val options = Config.mapOf(
                "page" to match.groupValues[1],
                "text" to match.groupValues[5],
                "anchor" to match.groupValues.getOrElse(3) {""}
        )
        return text_engine.addToken(rule_name,options)
    }

}
*/