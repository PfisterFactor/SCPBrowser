package pfister.scpbrowser.scprender

import pfister.scpbrowser.scprender.renderrules.RuleDiv
import pfister.scpbrowser.scprender.renderrules.RuleStrong
import pfister.scpbrowser.scprender.renderrules.RuleURL

// The text engine used to convert the SCP source input to an HTML output
class TextWikiEngine {

    // The default list of rules, in order, to apply to the source text
    private val rules = arrayOf(
            "Include",
            "Prefilter",
            "Delimiter",
            "Code",
            "Form",
            "Raw",
            "Rawold",
            "Modulepre",
            "Module",
            "Module654",
            "Iftags",
            "Comment",
            "Iframe",
            "Date",
            "Math",
            "Concatlines",
            "Freelink",
            "Equationreference",
            "Footnote",
            "Footnoteitem",
            "Footnoteblock",
            "Bibitem",
            "Bibliography",
            "Bibcite",
            //"Function",
            //"Html",
            "Divprefilter",
            //"Embed",
            "Anchor",
            "User",
            "Blockquote",
            "Heading",
            "Toc",
            "Horiz",
            "Separator",
            "Clearfloat",
            "Break",
            "Span",
            "Size",
            "Div",
            "Divalign",
            "Collapsible",
            "Tabview",
            "Note",
            "Gallery",
            "List",
            "Deflist",
            "Table",
            "Tableadv",
            "Button",
            "Image",
            "Embed",
            "Social",
            "File",
            //"Phplookup",
            "Center",
            "Newline",
            "Paragraph" ,
            "Url",
            "Email",
            "Mathinline",
            "Interwiki",
            //"Wikilink",
            "Colortext",
            "Strong",
            // "Bold",
            "Emphasis",
            //"Italic",
            "Underline",
            "Strikethrough",
            "Tt",
            "Superscript",
            "Subscript",
            "Typography",
            "Tighten"   
    )


    val delim = 0xff.toChar()

    private var nextTokenID:Int = 0
    private var tokens: List<TextToken> = listOf()

    var source = ""

    private val render_rules = mapOf<String,RenderRule>(
            "Div" to RuleDiv(this),
            "Url" to RuleURL(this),
            "Strong" to RuleStrong(this)
    )

    fun transform(text:String): String {
        // Set the source text
        source = "" + text

        // Clear the tokens
        tokens = listOf()

        // Clear the token IDs
        nextTokenID = 0

        parse()
        return render()
    }

    private fun parse() {
        for (rule in render_rules.values) {
            rule.parse()
        }
    }

    // Unsure if needed or not yet
    private fun preRender() {

    }

    // Steps through the source string and builds a new, formatted and rendered, html string
    private fun render(): String {
        preRender()

        // The builder we'll use to construct the rendered string
        val output = StringBuilder(source.length)

        // Is the current char inside a token deliminator
        var in_delim = false

        // The string within the deliminators
        // Should only be numeric, which allows us to cast it to an int later and use it as an index
        val token_id_string = StringBuilder()

        // Step through all the characters in the source
        for (char in source) {
            // If we're already inside a deliminator
            if (in_delim) {
                // Check if the current character is the ending to the token id
                if (char == delim) {

                    val int_key = token_id_string.toString().toInt()
                    val token = tokens[int_key]
                    val rule = token.rule_name

                    output.append(render_rules[rule]?.render(token))
                    in_delim = false
                }
                else {
                    token_id_string.append(char)
                }
            }
            else {
                // If we're starting a token ID
                if (char == delim) {
                    token_id_string.delete(0,token_id_string.capacity())
                    in_delim = true
                }
                else {
                    output.append(char)
                }
            }
        }
        return output.toString()
    }

    fun addToken(rule:String, options:Map<String,Any>, id_only:Boolean = false): String {
        val newToken = TextToken(rule,options)
        tokens += newToken
        nextTokenID++

        return if (id_only) {
            "${nextTokenID-1}"
        }
        else {
            "$delim${nextTokenID-1}$delim"
        }
    }



}