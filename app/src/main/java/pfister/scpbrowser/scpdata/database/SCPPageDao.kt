package pfister.scpbrowser.scpdata.database

import android.arch.persistence.room.*
import pfister.scpbrowser.scpdata.SCPPage

@Dao
interface SCPPageDao {
    @Query("SELECT * FROM scppage WHERE id=:id LIMIT 1")
    fun getSCPPage(id:String):SCPPage

    @Query("DELETE FROM scppage WHERE id=:id")
    fun deleteSCPPage(id:String)

    @Insert
    // Add a SCP page
    fun addSCPPage(page:SCPPage)

    @Update
    // Updates an SCP page
    fun updateSCPPage(page:SCPPage)

    @Delete
    // Deletes a single SCP page
    fun deleteSCPPage(page:SCPPage)

    @Query("DELETE FROM scppage")
    // Deletes the entire database
    fun nukeDatabase()
}