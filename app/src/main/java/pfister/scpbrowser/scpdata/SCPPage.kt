package pfister.scpbrowser.scpdata

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter


@Entity
class SCPPage {

    @PrimaryKey
    // The page_id of the page
    var Page_ID:Int = 0

    // The title of the page
    var SCP_Title:String = ""

    // The wikidot source for the page
    var Page_Source:String = ""

    // List of tags for the page
    var Tags:Array<String> = arrayOf()



}
class ArrayStringConverter {
    @TypeConverter
    fun toArray(value: String?): Array<String>? {
        return value?.split(",")?.toTypedArray()
    }

    @TypeConverter
    fun toString(value: Array<String>?): String? {
        return (value?.fold("") { acc, s -> "$acc,$s" })
    }
}

