package pfister.scpbrowser.scprender.renderrules.modulerender

class ModuleCSS : ModuleRender {

    override val module_name: String = "css"

    override fun render(attr: Map<String,String>, body: String): String {
        if (attr["disable"] == "true")
            return ""
        if (attr["show"] == "true")
            return "(CSS Code Display -- TODO)"

        return "<style>$body</style>"
    }
}