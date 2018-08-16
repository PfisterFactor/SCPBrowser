package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine

abstract class RuleDefault : RenderRule {

    override fun parse() {
        regexReplace(regex) {x -> process(x)}
    }

    override fun process(match: MatchResult): CharSequence {
        return match.groupValues[0]
    }

    override fun render(token: TextToken): String =
        ""


    fun getAttrs(text:String): Map<String,String> {
        fun stripslashes(text:String):String =
            text.replace("\\\\", "!~!").replace("\\", "").replace("!~!", "\\")

        val tmp = text.trim().split("=\"")

        val attrs = mutableMapOf<String,String>()
        var key = ""

        for ((index,value) in tmp.withIndex()) {
            if (index == 0) {
                key = value.trim()
                continue
            }

            val pos = value.lastIndexOf('"')
            attrs[key] = stripslashes(value.substring(0,pos)).trim()
            key = value.substring(pos+1).trim()

        }
        return attrs.toMap()
    }

    fun regexReplace(reg: Regex,callback: (MatchResult) -> CharSequence) {
        text_engine.source = reg.replace(text_engine.source) { x -> callback(x) }
    }

}