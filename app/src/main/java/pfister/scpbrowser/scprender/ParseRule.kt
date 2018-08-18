package pfister.scpbrowser.scprender

interface ParseRule
{
    // Name of the rule
    val rule_name:String

    // Regex for the rule
    val regex:Regex

    // Reference to the text engine
    val text_engine: TextWikiEngine

    // Custom conf for parsing with this rule
    val conf: Config?

    // Finds tokens corresponding to the rule using regex and pass them to process
    fun parse()

    // The callback for the regex replace, adds tokens usually
    fun process(match: MatchResult): CharSequence
}