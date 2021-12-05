package pl.gg.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import pl.gg.weather.di.MyApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var toolbarTitle : String?
        get(){
            return supportActionBar?.title?.toString()
        }
        set(value){
            supportActionBar?.title = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}