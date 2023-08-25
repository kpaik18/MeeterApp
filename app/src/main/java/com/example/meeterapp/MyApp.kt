import android.app.Application
import android.content.Context

class MyApp : Application() {

    companion object {
        private lateinit var instance: MyApp

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
