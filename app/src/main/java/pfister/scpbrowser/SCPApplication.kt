package pfister.scpbrowser

import android.app.Application
import android.arch.persistence.room.Room
import okhttp3.OkHttpClient
import pfister.scpbrowser.scpdata.database.SCPDatabase
import pfister.scpbrowser.scpdata.database.SCPPageDao

// Holds the SCP database and webscraper singletons
class SCPApplication: Application() {

    var Database: SCPDatabase? = null

    var OkHTTP: OkHttpClient? = null


    override fun onCreate() {
        super.onCreate()
        // Setup the database if its not already setup
        Database = Database ?: Room.databaseBuilder(this.applicationContext, SCPDatabase::class.java,"scpdatabase").build()

        OkHTTP = OkHttpClient()


    }

    // Convenience method to get the SCP Database interface
    fun SCPDatabaseDAO(): SCPPageDao? = Database?.SCPPageDao()
}