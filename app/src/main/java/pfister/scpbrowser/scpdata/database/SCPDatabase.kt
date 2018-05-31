package pfister.scpbrowser.scpdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import pfister.scpbrowser.scpdata.SCPPage


@Database(entities = [(SCPPage::class)],version = 2)
abstract class SCPDatabase: RoomDatabase() {
    abstract fun SCPPageDao():SCPPageDao
}