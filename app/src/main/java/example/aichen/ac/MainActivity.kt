package example.aichen.ac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import x.aichen.extend.setCustomDensity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomDensity(this.application, 360)
        setContentView(R.layout.activity_main)
    }
}
