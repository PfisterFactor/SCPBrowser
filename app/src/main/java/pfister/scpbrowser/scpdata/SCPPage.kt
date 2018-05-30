package pfister.scpbrowser.scpdata

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import pl.droidsonroids.jspoon.annotation.Selector

@Entity
class SCPPage {

    @PrimaryKey
    @Selector("#page-title", regex = "([0-9]+)")
    var ID:String = ""

    @Selector(".scp-image-block > img", attr = "src")
    var Images: ArrayList<String>? = null
}
class SCPPageConverter {
    @TypeConverter
    fun listFromString(images:String):java.util.ArrayList<String> {
        val list = ArrayList<String>()
        images.split('\n').toCollection(list)
        return list
    }

    @TypeConverter
    fun listToString(list:java.util.ArrayList<String>):String {
        return list.foldRight("",{acc,s -> s + acc + "\n"})
    }
}