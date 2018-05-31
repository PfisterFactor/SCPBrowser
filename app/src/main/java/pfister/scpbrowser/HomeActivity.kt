package pfister.scpbrowser

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pfister.scpbrowser.scpdata.HomeViewModel
import pfister.scpbrowser.scpdata.SCPPage
import pfister.scpbrowser.scpdata.SCPPageService

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    fun SCPService() = (application as SCPApplication).SCPService

    private var viewmodel: HomeViewModel? = null

    // Called on creation of activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        scp_display.settings.javaScriptEnabled = true
        scp_display.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean = onURLClicked(request)
        }
        scp_display.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                println(consoleMessage?.message())
                return super.onConsoleMessage(consoleMessage)
            }
        }
    }

    fun onURLClicked(request:WebResourceRequest):Boolean  {
        val scpURL = request.url.path.drop(1)
        if (scpURL == viewmodel?.CurrentSCPPage()) return false

        displaySCPPage(scpURL)
        return true
    }

    // Downloads the SCP page and injects the local CSS
    private fun downloadAndPrepareSCP(scp: String): SCPPage? {
        val response = SCPService()!!.getSCPPage(scp).execute()
        if (response == null || !response.isSuccessful) return null
        if (response.body() == null) return null

        val page = response.body()!!

        SCPPageService.stripUnwantedElements(page)
        SCPPageService.injectLocalResources(page)
        return page

    }

    // Displays the SCP page from the database if its available, otherwise downloads and displays it
    // Todo: Implement database usage
    private fun displaySCPPage(scp: String): Boolean {
        if (SCPService() == null) return false
        doAsync{
            val page = downloadAndPrepareSCP(scp)
            if (page != null) {
                uiThread {
                    scp_display.loadDataWithBaseURL("file:///android_asset/", page.PageContent!!, "text/html", "UTF-8",null)
                    viewmodel?.SCPPagesVisited?.push(scp)
                }
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        displaySCPPage(viewmodel?.CurrentSCPPage().orEmpty())
    }

    // Called when back button on phone is pressed
    override fun onBackPressed() {
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(GravityCompat.START)
            scp_display.canGoBack() -> {
                scp_display.goBack()
                viewmodel?.SCPPagesVisited?.pop()
            }
            else -> {
                viewmodel?.SCPPagesVisited?.clear()
                super.onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_scp_home -> {
                // Navigates back to the SCP homepage
                displaySCPPage("")
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
