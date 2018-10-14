package pfister.scpbrowser.scprender

object TextEngineUtils {

    val CONVERT_ARRAY = arrayOf(
    "À" to "A","À" to "A","Á" to "A","Á" to "A","Â" to "A","Â" to "A",
    "Ã" to "A","Ã" to "A","Ä" to "Ae","Ä" to "A","Å" to "A","Å" to "A",
    "Æ" to "Ae","Æ" to "AE",
    "Ā" to "A","Ą" to "A","Ă" to "A",
    "Ç" to "C","Ç" to "C","Ć" to "C","Č" to "C","Ĉ" to "C","Ċ" to "C",
    "Ď" to "D","Đ" to "D","Ð" to "D","Ð" to "D",
    "È" to "E","È" to "E","É" to "E","É" to "E","Ê" to "E","Ê" to "E","Ë" to "E","Ë" to "E",
    "Ē" to "E","Ę" to "E","Ě" to "E","Ĕ" to "E","Ė" to "E",
    "Ĝ" to "G","Ğ" to "G","Ġ" to "G","Ģ" to "G",
    "Ĥ" to "H","Ħ" to "H",
    "Ì" to "I","Ì" to "I","Í" to "I","Í" to "I","Î" to "I","Î" to "I","Ï" to "I","Ï" to "I",
    "Ī" to "I","Ĩ" to "I","Ĭ" to "I","Į" to "I","İ" to "I",
    "Ĳ" to "IJ",
    "Ĵ" to "J",
    "Ķ" to "K",
    "Ł" to "K","Ľ" to "K","Ĺ" to "K","Ļ" to "K","Ŀ" to "K",
    "Ñ" to "N","Ñ" to "N","Ń" to "N","Ň" to "N","Ņ" to "N","Ŋ" to "N",
    "Ò" to "O","Ò" to "O","Ó" to "O","Ó" to "O","Ô" to "O","Ô" to "O","Õ" to "O","Õ" to "O",
    "Ö" to "Oe","Ö" to "Oe",
    "Ø" to "O","Ø" to "O","Ō" to "O","Ő" to "O","Ŏ" to "O",
    "Œ" to "OE",
    "Ŕ" to "R","Ř" to "R","Ŗ" to "R",
    "Ś" to "S","Š" to "S","Ş" to "S","Ŝ" to "S","Ș" to "S",
    "Ť" to "T","Ţ" to "T","Ŧ" to "T","Ț" to "T",
    "Ù" to "U","Ù" to "U","Ú" to "U","Ú" to "U","Û" to "U","Û" to "U",
    "Ü" to "Ue","Ū" to "U","Ü" to "Ue",
    "Ů" to "U","Ű" to "U","Ŭ" to "U","Ũ" to "U","Ų" to "U",
    "Ŵ" to "W",
    "Ý" to "Y","Ý" to "Y","Ŷ" to "Y","Ÿ" to "Y",
    "Ź" to "Z","Ž" to "Z","Ż" to "Z",
    "Þ" to "T","Þ" to "T",
    "à" to "a","á" to "a","â" to "a","ã" to "a","ä" to "ae",
    "ä" to "ae",
    "å" to "a","ā" to "a","ą" to "a","ă" to "a","å" to "a",
    "æ" to "ae",
    "ç" to "c","ć" to "c","č" to "c","ĉ" to "c","ċ" to "c",
    "ď" to "d","đ" to "d","ð" to "d",
    "è" to "e","é" to "e","ê" to "e","ë" to "e","ē" to "e",
    "ę" to "e","ě" to "e","ĕ" to "e","ė" to "e",
    "ƒ" to "f",
    "ĝ" to "g","ğ" to "g","ġ" to "g","ģ" to "g",
    "ĥ" to "h","ħ" to "h",
    "ì" to "i","í" to "i","î" to "i","ï" to "i","ī" to "i",
    "ĩ" to "i","ĭ" to "i","į" to "i","ı" to "i",
    "ĳ" to "ij",
    "ĵ" to "j",
    "ķ" to "k","ĸ" to "k",
    "ł" to "l","ľ" to "l","ĺ" to "l","ļ" to "l","ŀ" to "l",
    "ñ" to "n","ń" to "n","ň" to "n","ņ" to "n","ŉ" to "n",
    "ŋ" to "n",
    "ò" to "o","ó" to "o","ô" to "o","õ" to "o","ö" to "oe",
    "ö" to "oe",
    "ø" to "o","ō" to "o","ő" to "o","ŏ" to "o",
    "œ" to "oe",
    "ŕ" to "r","ř" to "r","ŗ" to "r",
    "š" to "s",
    "ù" to "u","ú" to "u","û" to "u","ü" to "ue","ū" to "u",
    "ü" to "ue",
    "ů" to "u","ű" to "u","ŭ" to "u","ũ" to "u","ų" to "u",
    "ŵ" to "w",
    "ý" to "y","ÿ" to "y","ŷ" to "y",
    "ž" to "z","ż" to "z","ź" to "z",
    "þ" to "t",
    "ß" to "ss",
    "ſ" to "ss",
    "à" to "a","á" to "a","â" to "a","ã" to "a","ä" to "ae",
    "å" to "a","æ" to "ae","ç" to "c","ð" to "d",
    "è" to "e","é" to "e","ê" to "e","ë" to "e",
    "ì" to "i","í" to "i","î" to "i","ï" to "i",
    "ñ" to "n",
    "ò" to "o","ó" to "o","ô" to "o","õ" to "o","ö" to "oe",
    "ø" to "o",
    "ù" to "u","ú" to "u","û" to "u","ü" to "ue",
    "ý" to "y","ÿ" to "y",
    "þ" to "t",
    "ß" to "ss",
    " " to "-",
    "," to "-",
    "/" to "-",
    "." to "-"
    )

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

    // Ugly as hell
    fun toUnixName(text:String): String {
        var result = text
        result = text.trim()
        CONVERT_ARRAY.forEach { result.replace(it.first,it.second) }

        result = result.toLowerCase()
        result = result.replace("""[^a-z0-9\-:_]""".toRegex(),"-")
        result = result.replace(""";^_;""".toRegex(),":_")
        result = result.replace(""";(?<!:)_;""".toRegex(),"-")
        result = result.replace("""^\-*""".toRegex(),"")
        result = result.replace("""\-*$""","")
        result = result.replace("""[\-]{2,}""".toRegex(),"-")
        result = result.replace("""[:]{2,}""".toRegex(),":")

        result = result.replace(":-",":")
        result = result.replace("-:",":")
        result = result.replace("_-","_")
        result = result.replace("-_","_")

        result = result.replace("""^:""".toRegex(),"")
        result = result.replace(""":$""".toRegex(),"")

        return result

    }
}