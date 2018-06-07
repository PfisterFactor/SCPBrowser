package pfister.scpbrowser.scpdisplay

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import pfister.scpbrowser.R
import pfister.scpbrowser.SCPApplication

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    fun SCPService() = (application as SCPApplication).SCPService

    private var viewmodel: HomeViewModel? = null

    // Called on creation of activity
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Not implemented.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        scp_display.settings.javaScriptEnabled = true
        // Set the background color to *near* transparent
        // Apparently there's a bug with older versions of android
        scp_display.setBackgroundColor(Color.argb(1, 255, 255, 255))

        scp_display.settings.loadWithOverviewMode = true

        scp_display.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean = onURLNavigate(request)
        }
    }

    fun onURLNavigate(request:WebResourceRequest):Boolean  {
        val scpURL = request.url.path.drop(1)
        displayAndUpdateStack(scpURL)
        return true
    }

    private fun displayAndUpdateStack(scp:String):Boolean {
        if (scp == viewmodel?.CurrentSCPPage()) return false
        if (scp_display.displaySCPPage(scp)) {
            viewmodel?.SCPPagesVisited?.push(scp)
            updateTitle(scp)
            return true
        }
        return false
            
    }
    // Handles edge cases and updates the title of the activity
    private fun updateTitle(newTitle:String?) {
        // If the title passed is null, invalid, or it's the home page (redirected), just display "SCP Browser"
        title = if (newTitle == null || newTitle == SCPDisplay.INVALID_PAGE || newTitle.startsWith("main/html/"))
            "SCP Browser"
        else {
            if (newTitle.startsWith("scp-"))
                newTitle.toUpperCase()
            else
                newTitle.capitalize()
        }

    }

    override fun onStart() {
        super.onStart()
        val page = if (viewmodel?.CurrentSCPPage() == SCPDisplay.INVALID_PAGE) "" else viewmodel?.CurrentSCPPage().orEmpty()
        displayAndUpdateStack(page)
    }

    // Called when back button on phone is pressed
    override fun onBackPressed() {
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(GravityCompat.START)
            scp_display.canGoBack() -> {
                scp_display.goBack()
                viewmodel?.SCPPagesVisited?.pop()
                updateTitle(viewmodel?.CurrentSCPPage())
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
                displayAndUpdateStack("")
            }
            R.id.nav_series_I -> {
                displayAndUpdateStack(SCPDisplay.SeriesPage(1))
            }
            R.id.nav_series_II -> {
                displayAndUpdateStack(SCPDisplay.SeriesPage(2))
            }
            R.id.nav_series_III -> {
                displayAndUpdateStack(SCPDisplay.SeriesPage(3))
            }
            R.id.nav_series_IV -> {
                displayAndUpdateStack(SCPDisplay.SeriesPage(4))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
