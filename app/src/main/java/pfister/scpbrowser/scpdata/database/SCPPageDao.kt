package pfister.scpbrowser.scpdata.database

import android.arch.persistence.room.*
import pfister.scpbrowser.scpdata.SCPPage

@Dao
interface SCPPageDao {
    @Query("SELECT * FROM scppage WHERE id=:id LIMIT 1")
    fun getSCPPage(id:String):SCPPage

    @Insert
    fun addSCPPage(page:SCPPage)

    @Update
    fun updateSCPPage(page:SCPPage)

    @Delete
    fun deleteSCPPage(page:SCPPage)
}