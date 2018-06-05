package pfister.scpbrowser.scpdata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SCPPageService {

    @GET("{scp}")
    fun getSCPPage(@Path("scp") scp:String) : Call<SCPPage>

}
