package pfister.scpbrowser.scprender

interface RenderRule {

    // Reference to the text engine
    val text_engine: TextWikiEngine

    // Any custom config required for rendering with this rule
    val conf: Config?

    // Turns the tokens into html to display
    fun render(token: TextToken): String


}