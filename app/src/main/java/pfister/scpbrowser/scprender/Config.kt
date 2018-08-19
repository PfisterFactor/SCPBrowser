package pfister.scpbrowser.scprender

class Config(initialCapacity:Int,loadFactor:Float = 0.75f) : HashMap<String,Any>(initialCapacity,loadFactor){

    companion object {
        val EMPTY:Config by lazy {
            Config(0)
        }

        fun mapOf(vararg pairs:Pair<String,Any>):Config =
            if (pairs.isNotEmpty()) pairs.toMap(Config(pairs.size)) else EMPTY
    }

    fun <T> get_class(key:String):T? = this[key] as? T

    fun get_string(key:String):String? = get_class<String>(key)

    fun get_char(key:String):Char? = get_class<Char>(key)

    fun get_bool(key:String):Boolean? = get_class<Boolean>(key)

    fun get_int(key:String):Int? = get_class<Int>(key)

    fun get_array(key:String):Array<Any>? = get_class<Array<Any>>(key)

    fun get_array_string(key:String):Array<String>? = get_class<Array<String>>(key)

    fun get_map(key:String):Map<String,Any>? = get_class<Map<String,Any>>(key)

    fun get_map_string(key:String):Map<String,String>? = get_class<Map<String,String>>(key)

}