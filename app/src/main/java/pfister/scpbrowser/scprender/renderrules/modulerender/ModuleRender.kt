package pfister.scpbrowser.scprender.renderrules.modulerender

interface ModuleRender {
    val module_name:String
    fun render(attr:Map<String,String>,body:String): String
}