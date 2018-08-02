package pfister.scpbrowser.scpdata.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pfister.scpbrowser.scpdata.ArrayStringConverter
import pfister.scpbrowser.scpdata.SCPPage


@Database(entities = [(SCPPage::class)],version = 2)
@TypeConverters(ArrayStringConverter::class)
abstract class SCPDatabase: RoomDatabase() {
    abstract fun SCPPageDao():SCPPageDao
}