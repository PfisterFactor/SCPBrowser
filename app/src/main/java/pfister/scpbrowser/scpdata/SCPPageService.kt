package pfister.scpbrowser.scpdata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SCPPageService {
    companion object {
        fun injectLocalResources(scppage:SCPPage): SCPPage {
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
            return scppage
        }
    }

    @GET("{scp}")
    fun getSCPPage(@Path("scp") scp:String) : Call<SCPPage>

    @GET("http://www.scp-wiki.net/component:theme/code/1")
    fun getSCPCSS(): Call<String>



}
