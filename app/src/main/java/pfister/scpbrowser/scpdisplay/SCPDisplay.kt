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
import pfister.scpbrowser.scpdata.SCPPageDetails
import pfister.scpbrowser.scprender.TextWikiEngine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class SCPDisplay(context: Context,attr:AttributeSet?): WebView(context,attr) {
    companion object {
        const val HOME_PAGE:String = "http://www.scp-wiki.net/"
        const val HOME_PAGE_ID:Int = 1946911
        const val INVALID_PAGE:String = "invalid_page"
        const val INVALID_PAGE_ID: Int = -1

        fun requestSource(page_ID: Int): Request {
            val body: RequestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"page_id=$page_ID&moduleName=viewsource%2FViewSourceModule")

            return Request.Builder()
                    .url("http://www.scp-wiki.net/ajax-module-connector.php?page_id=$page_ID&moduleName=viewsource%2FViewSourceModule")
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .addHeader("Connection","keep-alive")
                    .post(body)
                    .build()

        }
        // Returns the details for a page
        // Scrapes the webpage passed and tries to find a wikirequest object that has the page details assigned to it
        // Then passes the relevant chunk of the webpage to a regex and turns it into a SCPPageDetails object
        // Is blocking
        // TODO: Implement caching
        fun getPageDetails(url:String): SCPPageDetails? {

            val stream = URL(url).openStream()
            val reader = BufferedReader(InputStreamReader(stream))
            // 800 is a rough estimate of the wikirequest body on the source page
            val wiki_request_buffer = StringBuilder(800)

            val START_STRING = "WIKIREQUEST"
            var start_string_index = 0

            val END_STRING = "</script>"
            var end_string_index = 0

            var isReading = false

            var read:Int = reader.read()
            while (read != -1) {
                val char = read.toChar()

                if (isReading) {
                    wiki_request_buffer.append(char)
                    if (END_STRING[end_string_index].toLowerCase() == char.toLowerCase()) {
                        if (end_string_index == END_STRING.length-1)
                            break
                        else
                            end_string_index++
                    }
                }
                else if (START_STRING[start_string_index].toLowerCase() == char.toLowerCase()) {
                    if (start_string_index == START_STRING.length-1)
                        isReading = true
                    else
                        start_string_index++
                }
                else
                    start_string_index = 0


                read = reader.read()
            }
            reader.close()
            return SCPPageDetails.scrapePage(wiki_request_buffer.toString())
        }
    }
    constructor(context:Context):this(context,null) {
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
    }

    var OkHTTP = ((context as HomeActivity).application as SCPApplication).OkHTTP

    val TextEngine: TextWikiEngine = TextWikiEngine()

    fun History(): SCPHistory? = (context as HomeActivity).viewmodel?.SCPHistory

    fun CurrentPage(): SCPHistoryEntry? = History()?.CurrentPage()

    override fun goBack() {
        super.goBack()
        History()?.popPage()

    }

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
    private fun downloadAndPrepareSCP(url:String): SCPPage? {


        val details: SCPPageDetails = getPageDetails(url) ?: return null

        if (details.Domain != "www.scp-wiki.net") return null

        val page_source_request = requestSource(details.Page_ID)

        val page_source_response = OkHTTP?.newCall(page_source_request)?.execute()!!


        if (!page_source_response.isSuccessful) return null


        val page = SCPPage()
        val json = JSONObject(page_source_response.body()?.string()!!)
        var decoded = Html.fromHtml(json.getString("body")).toString()

        // Drop "Page Source" from the top of the string
        val drop_str = "Page source"
        if (decoded.startsWith(drop_str)) {
            decoded = decoded.drop(drop_str.length)
            decoded = decoded.trimStart()
            decoded = decoded.trimStart('\n')
        }
        // These characters are actually not the same
        // One is U+00A0 : NO-BREAK SPACE [NBSP], the other is a regular space
        // I don't know why the scp source does this, so we have to fix it
        decoded = decoded.replace(' ',' ')

        page.Page_Source = decoded
        page.Page_Details = details
        page.Page_ID = details.Page_ID

        page_source_response.close()
        return page

    }

    // Displays the SCP page from the database if its available, otherwise downloads and displays it
    // Todo: Implement database usage
    fun displaySCPPage(url:String): Boolean {
        doAsync{
            val page = downloadAndPrepareSCP(url)
            if (page?.Page_ID == CurrentPage()?.Page_ID) return@doAsync
            if (page != null) {
                val parsed = TextEngine.transform(page)
                uiThread {

                    this@SCPDisplay.loadDataWithBaseURL("file:///android_asset/", parsed, "text/html", "UTF-8",null)

                    this@SCPDisplay.History()?.addPage(SCPHistoryEntry(url,page.Page_ID,page.Page_Details!!))

                    (this@SCPDisplay.context as HomeActivity).updateTitle(CurrentPage()?.Details?.Page_Name)
                }
            }
        }
        return true
    }
}