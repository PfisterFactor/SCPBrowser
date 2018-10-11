package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.TextWikiEngine
import pfister.scpbrowser.scprender.Transformer

class ParsePrefilter(override val text_engine: TextWikiEngine) : ParseDefault(), Transformer {
    override val rule_name: String = "Prefilter"
    override val regex: Regex = "".toRegex()
    override val conf: Config? = null

    override fun parse() {}
    override fun transform() {
        // Convert DOS Line Endings
        text_engine.source.replace("\r\n", "\n")

        // Convert Mac Line Endings
        text_engine.source.replace("\r","\n")

        // Trim whitespace lines
        regexReplace("""^\s+$""".toRegex(RegexOption.MULTILINE)) {""}

        // Convert tabs to four spaces
        text_engine.source.replace("\t","    ")

        // Add newlines to top and end of text
        text_engine.source = "\n" + text_engine.source + "\n"

        // Compress 3 or more new lines to 2
        val reg = """(\n[ ]*){3,}""".toRegex(RegexOption.MULTILINE)
        regexReplace(reg){"\n\n"}
    }
}
