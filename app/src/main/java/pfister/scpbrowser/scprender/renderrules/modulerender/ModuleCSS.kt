package pfister.scpbrowser.scprender.renderrules.modulerender

class ModuleCSS : ModuleRender {

    override val module_name: String = "css"

    override fun render(attr: Map<String,String>, body: String): String {
        // Todo: Support for attributes
        return "<style>$body</style>"
    }
}