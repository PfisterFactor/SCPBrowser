package pfister.scpbrowser.scpdata


class SCPPageDetails(
        val Page_ID:Int,
        val Category_ID:Int,
        val Page_Name:String,
        val Lang:String
) {
    companion object {
        val REGEX:Regex = """WIKIREQUEST\.info\.(\w*)\s?=\s?[",']?((?:[^;,",'])*)[",']?""".toRegex()
        val PROPERTIES:Array<String> = arrayOf(
                "pageId",
                "categoryId",
                "requestPageName",
                "lang"
        )

        // Scrapes a SCP webpage for a WIKIREQUEST object
        // Then steals all its info
        fun scrapePage(page:String):SCPPageDetails? {
            val map = mutableMapOf<String,String>()

            REGEX.findAll(page).forEach {
                map[it.groupValues[1]] = it.groupValues[2]
            }

            if (!PROPERTIES.all { map.containsKey(it) }) return null

            val page_id = map["pageId"]?.toIntOrNull()
            val category_id = map["categoryId"]?.toIntOrNull()

            if (page_id == null || category_id == null) return null

            return SCPPageDetails(page_id,category_id,map["requestPageName"]!!,map["lang"]!!)
        }
    }
}