package pfister.scpbrowser.scprender

class TextToken(val rule_name:String, val options:Map<String,Any>?) {
    fun getString(key:String): String? {
        if (options == null) return null
        return options[key] as? String
    }
    fun getMap(key:String): Map<String,String>? {
        if (options == null) return null
        return options[key] as? Map<String,String>
    }
}