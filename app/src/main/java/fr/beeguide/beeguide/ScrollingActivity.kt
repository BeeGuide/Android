package fr.beeguide.beeguide

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val intent = intent
        val city = intent.getStringExtra(Intents.CITY)
        title = city
        changeHeader(city)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, city, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun changeHeader(city: String) {
        // Webservice here, maybe ?
        val cityView = findViewById(R.id.imageHeader) as ImageView

        when(city) {
            "Paris" -> cityView.setImageResource(R.drawable.paris)
            "Toulouse" -> cityView.setImageResource(R.drawable.toulouse)
            "Givors" -> cityView.setImageResource(R.drawable.givors)
        }
    }
}
