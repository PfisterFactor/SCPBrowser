package pfister.scpbrowser.scpdisplay

import android.arch.lifecycle.ViewModel
import java.util.*

class HomeViewModel : ViewModel() {
    var SCPPagesVisited: Stack<Int> = Stack()

    fun CurrentSCPPage(): Int {
        if (SCPPagesVisited.empty())
            return SCPDisplay.INVALID_PAGE

        return SCPPagesVisited.peek()
    }
}