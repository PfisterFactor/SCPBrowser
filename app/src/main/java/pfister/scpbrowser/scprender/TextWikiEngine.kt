package pfister.scpbrowser.scprender

import pfister.scpbrowser.scprender.renderrules.RuleStrong

class TextToken(val rule_name:String, val options:Map<String,String>)

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

    val render_rules = mapOf<String,RenderRule>(
            "strong" to RuleStrong(this)
    )

    fun transform(text:String): String {
        // Set the source text
        source = text

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
    private fun preRender() {

    }

    private fun render(): String {

        val output = StringBuilder(source.length)

        var in_delim = false

        var key_string = ""


        for (char in source) {
            if (in_delim) {
                if (char == delim) {

                    // Confusing way of finding the first number in a string
                    val int_key = key_string.toInt()
                    val token = tokens[int_key]
                    val rule = token.rule_name

                    output.append(render_rules[rule]?.render(token))
                    in_delim = false
                }
                else {
                    key_string += char
                }
            }
            else {
                if (char == delim) {
                    key_string = ""
                    in_delim = true
                }
                else {
                    output.append(char)
                }
            }
        }
        return output.toString()
    }

    fun addToken(rule:String, options:Map<String,String>, id_only:Boolean = false): String {
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