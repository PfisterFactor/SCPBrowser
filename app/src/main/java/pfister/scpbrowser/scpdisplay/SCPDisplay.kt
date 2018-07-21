package pfister.scpbrowser.scpdisplay

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pfister.scpbrowser.scpdata.SCPPage
import pfister.scpbrowser.scpdata.SCPPageService

class SCPDisplay(context: Context,attr:AttributeSet?): WebView(context,attr) {
    companion object {
        const val HOME_PAGE:String = ""
        const val INVALID_PAGE:String = "¯\\_(ツ)_/¯"
        fun SeriesPage(i:Int): String = if (i <= 1) "scp-series" else "scp-series-$i"
    }
    constructor(context:Context):this(context,null)

    private fun SCPService(): SCPPageService? = (context as HomeActivity).SCPService()

    var CurrentSCPPage: String = SCPDisplay.INVALID_PAGE

    private fun injectLocalResources(scppage:SCPPage) {
        val STYLESHEET = """
            |<link href="scp-theme.old.css" type="text/css" rel="stylesheet">
            |
            """.trimMargin()
        val JAVASCRIPT = """
                |<script type="text/javascript" src="jquery.js"></script>
                |<script type="text/javascript" src="tooltip.js"></script>
                |<script type="text/javascript" src="scp.js"></script>
                |
            """.trimMargin()
        scppage.PageContent = STYLESHEET + JAVASCRIPT + "<body onload=\"onLoad()\">" + scppage.PageContent + "</body>"
    }
    private fun stripUnwantedElements(scppage: SCPPage) {
        val unwantedRatingBox = "<div class=\"page-rate-widget-box\">"

        val index = scppage.PageContent?.indexOf(unwantedRatingBox)
        if (index == null || index == -1) return

        val indexOfEndTag = scppage.PageContent?.indexOf("</div>",index)
        if (indexOfEndTag == null || indexOfEndTag == -1) return

        // 6 is the length of "</div>"
        scppage.PageContent = scppage.PageContent?.removeRange(index, indexOfEndTag + 6)
    }

    // Downloads the SCP page and injects the local CSS
    private fun downloadAndPrepareSCP(scp: String): SCPPage? {
        val response = SCPService()?.getSCPPage(scp)?.execute()
        if (response == null || !response.isSuccessful) return null
        if (response.body() == null) return null

        val page = response.body()!!

        stripUnwantedElements(page)
        injectLocalResources(page)
        return page

    }

    // Displays the SCP page from the database if its available, otherwise downloads and displays it
    // Todo: Implement database usage
    fun displaySCPPage(scp: String): Boolean {
        if (SCPService() == null) return false
        doAsync{
            val page = downloadAndPrepareSCP(scp)
            if (page != null) {
                uiThread {
                    this@SCPDisplay.loadDataWithBaseURL("file:///android_asset/", page.PageContent!!, "text/html", "UTF-8",null)

                    CurrentSCPPage = scp
                }
            }
        }
        return true
    }
}