package pfister.scpbrowser.scpdisplay

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
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

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var viewmodel: HomeViewModel? = null

    // Called on creation of activity
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Not implemented.", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

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
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean = request.hasGesture() && onResourceRequest(request)

        }
    }

    fun onResourceRequest(request:WebResourceRequest):Boolean  {
        displayAndUpdateStack(request.url.toString())
        return true
    }

    private fun displayAndUpdateStack(page:String):Boolean {
        return scp_display.displaySCPPage(page)
            
    }
    // Handles edge cases and updates the title of the activity
    fun updateTitle(newTitle:String?) {
        // If the title passed is null, invalid, or it's the home page (redirected), just display "SCP Browser"
        title = if (newTitle == null || newTitle == SCPDisplay.INVALID_PAGE)
            "SCP Browser"
        else {
            if (newTitle.startsWith("scp-") && newTitle.drop("scp-".length).toIntOrNull() != null)
                newTitle.toUpperCase()
            else
                newTitle.capitalize()
        }

    }

    override fun onStart() {
        super.onStart()
        val page = viewmodel?.SCPHistory?.CurrentPage()
        if (page == null)
            displayAndUpdateStack(SCPDisplay.HOME_PAGE)
        else
            displayAndUpdateStack(page.URL)
    }

    // Called when back button on phone is pressed
    override fun onBackPressed() {
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(GravityCompat.START)
            scp_display.canGoBack() -> {
                scp_display.goBack()
                updateTitle(scp_display.CurrentPage()?.Details?.Page_Name)
            }
            else -> {
                scp_display.History()?.clear()
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
                displayAndUpdateStack(SCPDisplay.HOME_PAGE)
            }
            R.id.nav_series_I -> {
                displayAndUpdateStack(SCPDisplay.SERIES_PAGES[0])
            }
            R.id.nav_series_II -> {
                displayAndUpdateStack(SCPDisplay.SERIES_PAGES[1])
            }
            R.id.nav_series_III -> {
                displayAndUpdateStack(SCPDisplay.SERIES_PAGES[2])
            }
            R.id.nav_series_IV -> {
                displayAndUpdateStack(SCPDisplay.SERIES_PAGES[3])
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
