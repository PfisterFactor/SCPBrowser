package pfister.scpbrowser.scprender

// The text engine used to convert the SCP source input to an HTML output
class TextWikiEngine {

    val RuleArray:Array<RenderRule> = arrayOf()

    // Takes in SCP Source, outputs the rendered HTML
    fun parse(scp_source:String):String {
        var new_source = "" + scp_source
        for (rule in RuleArray) {
            new_source = rule.process(rule.parse(new_source))
        }
        return new_source
    }


}