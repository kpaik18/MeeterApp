import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREFS_NAME = "token_prefs"
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        refreshToken = ""
        accessToken = ""
    }

    var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN_KEY, "")
        set(value) = sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, value).apply()

    var refreshToken: String?
        get() = sharedPreferences.getString(REFRESH_TOKEN_KEY, "")
        set(value) = sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, value).apply()
}
