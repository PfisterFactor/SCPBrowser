package pfister.scpbrowser.scpdata

import android.arch.lifecycle.ViewModel
import java.util.*

class HomeViewModel : ViewModel() {
    var SCPPagesVisited: Stack<String> = Stack()

    fun CurrentSCPPage(): String {
        if (SCPPagesVisited.empty())
            SCPPagesVisited.push("")
        return SCPPagesVisited.peek()
    }
}