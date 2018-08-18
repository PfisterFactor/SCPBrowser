package pfister.scpbrowser.scprender

class Config(initialCapacity:Int,loadFactor:Float = 0.75f) : HashMap<String,Any>(initialCapacity,loadFactor){
    companion object {
        fun mapOf(vararg pairs:Pair<String,Any>):Config =
            if (pairs.isNotEmpty()) pairs.toMap(Config(pairs.size)) else Config(0)

    }
    fun get_string(key:String):String? {
        return this[key] as? String
    }
    fun get_bool(key:String):Boolean? {
        return this[key] as? Boolean
    }
    fun get_int(key:String):Int? {
        return this[key] as? Int
    }
    fun get_array(key:String):Array<Any>? {
        return this[key] as? Array<Any>
    }
    fun get_array_string(key:String):Array<String>? {
        return get_array(key) as? Array<String>
    }
    fun get_map(key:String):Map<String,Any>? {
        return this[key] as? Map<String, Any>
    }
    fun get_map_string(key:String):Map<String,String>? {
        return get_map(key) as? Map<String,String>
    }
}