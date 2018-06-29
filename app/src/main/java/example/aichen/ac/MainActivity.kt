package example.aichen.ac

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import x.aichen.extend.setCustomDensity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomDensity(260)
        setContentView(R.layout.activity_main)
    }
}
