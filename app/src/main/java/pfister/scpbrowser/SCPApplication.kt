package pfister.scpbrowser

import android.app.Application
import android.arch.persistence.room.Room
import pfister.scpbrowser.scpdata.database.SCPDatabase
import pfister.scpbrowser.scpdata.database.SCPPageDao
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

// Holds the SCP database and webscraper singletons
class SCPApplication: Application() {
    var SCPRetrofit: Retrofit? = null

    var Database: SCPDatabase? = null

    override fun onCreate() {
        super.onCreate()
        // Setup the webscraper pointing towards the scp wiki main page if its not already set up
        SCPRetrofit = SCPRetrofit ?: Retrofit.Builder()
                .baseUrl("http://www.scp-wiki.net")
                .addConverterFactory(JspoonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        // Setup the database if its not already setup
        Database = Database ?: Room.databaseBuilder(this.applicationContext, SCPDatabase::class.java,"scpdatabase").build()
    }

    // Convenience method to get the SCP Database interface
    fun SCPDatabaseDAO(): SCPPageDao? = Database?.SCPPageDao()
}