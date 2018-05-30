package pfister.scpbrowser.scpdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pfister.scpbrowser.scpdata.SCPPage
import pfister.scpbrowser.scpdata.SCPPageConverter

@TypeConverters(SCPPageConverter::class)
@Database(entities = [(SCPPage::class)],version = 1)
abstract class SCPDatabase: RoomDatabase() {
    abstract fun SCPPageDao():SCPPageDao
}