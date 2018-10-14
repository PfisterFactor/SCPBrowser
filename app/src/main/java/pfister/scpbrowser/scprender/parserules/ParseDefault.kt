package pfister.scpbrowser.scprender.parserules

import pfister.scpbrowser.scprender.ParseRule

abstract class ParseDefault : ParseRule {

    override fun parse() {
        regexReplace(regex) {x -> process(x)}
    }

    override fun process(match: MatchResult): CharSequence {
        return match.groupValues[0]
    }


    fun regexReplace(reg: Regex,callback: (MatchResult) -> CharSequence) {
        text_engine.source = reg.replace(text_engine.source) { x -> callback(x) }
    }


}