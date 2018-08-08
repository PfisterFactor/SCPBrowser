package pfister.scpbrowser.scpdisplay

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import pfister.scpbrowser.SCPApplication
import pfister.scpbrowser.scpdata.SCPPage
import pfister.scpbrowser.scprender.TextWikiEngine

class SCPDisplay(context: Context,attr:AttributeSet?): WebView(context,attr) {
    companion object {
        const val HOME_PAGE:Int = 1946911
        const val INVALID_PAGE:Int = -1

        fun requestSource(page_ID: Int): Request {
            val body: RequestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"page_id=$page_ID&moduleName=viewsource%2FViewSourceModule")

            return Request.Builder()
                    .url("http://www.scp-wiki.net/ajax-module-connector.php?page_id=$page_ID&moduleName=viewsource%2FViewSourceModule")
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .addHeader("Connection","keep-alive")
                    .post(body)
                    .build()

        }
    }
    constructor(context:Context):this(context,null) {
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
    }

    var OkHTTP = ((context as HomeActivity).application as SCPApplication).OkHTTP

    var CurrentSCPPage: Int = SCPDisplay.INVALID_PAGE

    val TextEngine: TextWikiEngine = TextWikiEngine()

//    private fun injectLocalResources(scppage:SCPPage) {
//        val STYLESHEET = """
//            |<link href="scp-theme.old.css" type="text/css" rel="stylesheet">
//            |
//            """.trimMargin()
//        val JAVASCRIPT = """
//                |<script type="text/javascript" src="jquery.js"></script>
//                |<script type="text/javascript" src="tooltip.js"></script>
//                |<script type="text/javascript" src="scp.js"></script>
//                |
//            """.trimMargin()
//        scppage.PageContent = STYLESHEET + JAVASCRIPT + "<body onload=\"onLoad()\">" + scppage.PageContent + "</body>"
//    }
//    private fun stripUnwantedElements(scppage: SCPPage) {
//        val unwantedRatingBox = "<div class=\"page-rate-widget-box\">"
//
//        val index = scppage.PageContent?.indexOf(unwantedRatingBox)
//        if (index == null || index == -1) return
//
//        val indexOfEndTag = scppage.PageContent?.indexOf("</div>",index)
//        if (indexOfEndTag == null || indexOfEndTag == -1) return
//
//        // 6 is the length of "</div>"
//        scppage.PageContent = scppage.PageContent?.removeRange(index, indexOfEndTag + 6)
//    }

    // Downloads the SCP page and injects the local CSS
    private fun downloadAndPrepareSCP(page_ID:Int): SCPPage? {

        val request = requestSource(page_ID)

        val response = OkHTTP?.newCall(request)?.execute()!!

        if (!response.isSuccessful) return null

        val page = SCPPage()
        val json = JSONObject(response.body()?.string()!!)
        val decoded = Html.fromHtml(json.getString("body")).toString()
        page.Page_Source = decoded

        return page

    }

    // Displays the SCP page from the database if its available, otherwise downloads and displays it
    // Todo: Implement database usage
    fun displaySCPPage(page_ID:Int): Boolean {
        doAsync{
            val page = downloadAndPrepareSCP(page_ID)
            if (page != null) {
                val parsed = TextEngine.transform(page.Page_Source)
                uiThread {

                    this@SCPDisplay.loadDataWithBaseURL("file:///android_asset/", parsed, "text/html", "UTF-8",null)

                    CurrentSCPPage = page_ID
                }
            }
        }
        return true
    }
}