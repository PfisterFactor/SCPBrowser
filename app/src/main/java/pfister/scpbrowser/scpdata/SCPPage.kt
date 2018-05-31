package pfister.scpbrowser.scpdata

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import pl.droidsonroids.jspoon.annotation.Selector

@Entity
class SCPPage {

    @PrimaryKey
    @Selector("#page-title")
    // The title of the scp
    var ID:String = ""

    @Selector("#page-content",attr = "html")
    // The full page content of the SCP
    var PageContent: String? = null

//    var Tags: Array<String>? = null

}

