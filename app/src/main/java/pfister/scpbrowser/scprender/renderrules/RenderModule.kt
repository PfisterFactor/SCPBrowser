package pfister.scpbrowser.scprender.renderrules

import pfister.scpbrowser.scprender.Config
import pfister.scpbrowser.scprender.RenderRule
import pfister.scpbrowser.scprender.TextToken
import pfister.scpbrowser.scprender.TextWikiEngine
import pfister.scpbrowser.scprender.renderrules.modulerender.ModuleCSS
import pfister.scpbrowser.scprender.renderrules.modulerender.ModuleRate
import pfister.scpbrowser.scprender.renderrules.modulerender.ModuleRender

class RenderModule(override val text_engine: TextWikiEngine) : RenderRule {
    override val conf: Config? = null

    val modules:Array<ModuleRender> = arrayOf(
            ModuleCSS(),
            ModuleRate()
    )

    override fun render(token: TextToken): String {
        val moduleName = token.options?.get_string("moduleName")!!
        val attr = token.options.get_string("attr").orEmpty()
        val body = token.options.get_string("body").orEmpty()

        val module = modules.find { it.module_name.toLowerCase() == moduleName.toLowerCase() } ?: return ""

        // Split up the attributes into key, value pairs and throw them in a map
        val attr_array:Map<String,String> = if (attr.isNotEmpty())
            attr.split(" ").associate { str -> str.dropLastWhile { it != '=' }.dropLast(1) to str.dropWhile { it != '=' }.drop(1).removeSurrounding("\"") }
        else
            mapOf()

        return module.render(attr_array,body)




    }
}