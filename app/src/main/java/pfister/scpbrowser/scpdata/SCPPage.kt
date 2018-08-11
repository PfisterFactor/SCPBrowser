package pfister.scpbrowser.scpdata

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter


@Entity
class SCPPage {

    @PrimaryKey
    // The page_id of the page
    var Page_ID:Int = 0

    // The wikidot source for the page
    var Page_Source:String = ""

    // Various metadata about the page
    var Page_Details:SCPPageDetails? = null

    // List of tags for the page
    var Tags:Array<String> = arrayOf()

    // URL of page, not stored
    @Ignore
    var URL:String = ""



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

class PageDetailsConverter {
    @TypeConverter
    fun toPageDetails(value: String?):SCPPageDetails? {
        val array = value?.split(",")
        if (array == null || array.size < 4) return null
        return SCPPageDetails(array[0].toInt(),array[1].toInt(),array[2],array[3])
    }
    @TypeConverter
    fun toString(value: SCPPageDetails?):String? {
        if (value == null) return null
        val string = "${value.Page_ID},${value.Category_ID},${value.Page_Name},${value.Lang}"
        return string
    }
}

