package pfister.scpbrowser.scpdisplay

import pfister.scpbrowser.scpdata.SCPPageDetails
import java.util.*

class SCPHistoryEntry(val URL:String, val Page_ID:Int, val Details:SCPPageDetails) {
    override fun equals(other: Any?): Boolean =
        when (other) {
            is SCPHistoryEntry -> other.Page_ID == this.Page_ID
            else -> false
        }

    override fun hashCode(): Int {
        return Page_ID
    }
}

class SCPHistory {
    private val SCPPagesVisited: Stack<SCPHistoryEntry> = Stack()

    fun CurrentPage(): SCPHistoryEntry? {
        return if (SCPPagesVisited.isEmpty()) null else SCPPagesVisited.peek()
    }

    fun addPage(entry:SCPHistoryEntry): Boolean {
        if (CurrentPage() == entry) return false

        SCPPagesVisited.push(entry)
        return true
    }
    fun popPage():Boolean {
        if (SCPPagesVisited.isEmpty()) return false

        SCPPagesVisited.pop()
        return true
    }
    fun clear() {
        SCPPagesVisited.clear()
    }


}