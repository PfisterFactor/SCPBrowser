package pfister.scpbrowser.scpdata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SCPPageService {
    companion object {
        fun injectLocalResources(scppage:SCPPage) {
            val STYLESHEET = """
            |<link href="scp-theme1.css" type="text/css" rel="stylesheet">
            |<link href="scp-theme2.css" type="text/css" rel="stylesheet">
            |
            """.trimMargin()
            val JAVASCRIPT = """
                |<script type="text/javascript" src="scp.js"></script>
                |
            """.trimMargin()
            scppage.PageContent = STYLESHEET + JAVASCRIPT + "<body onload=\"onLoad()\">" + scppage.PageContent + "</body>"
        }
        fun stripUnwantedElements(scppage: SCPPage) {
            val unwantedRatingBox = "<div class=\"page-rate-widget-box\">"

            val index = scppage.PageContent?.indexOf(unwantedRatingBox)
            if (index == null || index == -1) return

            val indexOfEndTag = scppage.PageContent?.indexOf("</div>",index)
            if (indexOfEndTag == null || indexOfEndTag == -1) return

            // 6 is the length of "</div>"
            scppage.PageContent = scppage.PageContent?.removeRange(index, indexOfEndTag + 6)
        }
    }

    @GET("{scp}")
    fun getSCPPage(@Path("scp") scp:String) : Call<SCPPage>





}
