package pfister.scpbrowser.scprender

class TextToken(val rule_name:String, val options:Map<String,Any>) {
    fun getString(key:String): String? {
        return options[key] as? String
    }
    fun getMap(key:String): Map<String,String>? {
        return options[key] as? Map<String,String>
    }
}