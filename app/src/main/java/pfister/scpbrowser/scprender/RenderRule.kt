package pfister.scpbrowser.scprender

interface RenderRule {

    // Name of the rule
    val Rule_Name:String

    // Regex for the rule
    val Regex:Regex

    // Finds tokens corresponding to the rule using regex and any additional processing
    fun parse(source:String): Array<String>

    // Replaces the matches with their html counterparts
    fun process(matches:Array<String>): String

}