package pfister.scpbrowser.scprender

interface Transformer {
    val text_engine: TextWikiEngine

    fun transform()
}