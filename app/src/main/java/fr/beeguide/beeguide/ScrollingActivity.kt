package fr.beeguide.beeguide

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.ListView
import fr.beeguide.beeguide.model.CityTour
import java.util.*


class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val intent = intent
        val city = intent.getStringExtra(getString(R.string.intent_city))
        title = city
        changeHeader(city)

        val mListView = findViewById(R.id.listView) as ListView
        mListView.adapter = CityTourAdapter(this, getCityTours(city))
        mListView.setOnTouchListener { v, event ->
            // Setting on Touch Listener for handling the touch inside ScrollView
            // Disallow the touch request for parent scroll on touch of child view
            (findViewById(R.id.app_bar) as AppBarLayout).setExpanded(false)
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            finish()
        }
    }

    fun changeHeader(city: String) {
        // Webservice here, maybe ?
        val cityView = findViewById(R.id.imageHeader) as ImageView

        when(city) {
            "Paris" -> cityView.setImageResource(R.drawable.paris)
            "Toulouse" -> cityView.setImageResource(R.drawable.toulouse)
            "Givors" -> cityView.setImageResource(R.drawable.givors)
            "Lille" -> cityView.setImageResource(R.drawable.lille)
            "Marseille" -> cityView.setImageResource(R.drawable.marseille)
            "Chateau-neuf-les-martigues" -> cityView.setImageResource(R.drawable.chateau9)
        }
    }

    fun getCityTours(city: String): List<CityTour> {
        // Webservice here, maybe ?
        val tours = ArrayList<CityTour>()
        tours.add(CityTour("picture", "Alexandra", 4, 2, 12, "Historique", 2, 5))
        if(city == "Givors")
        {
            tours.add(CityTour("diane", "Diane", 4, 1, 25, "Insolite", 0, 1))
            tours.add(CityTour("solange", "Solange", 5, 8, 42, "Gastronomie", 1, 2))
            tours.add(CityTour("marion", "Marion", 4, 3, 10, "Culture", 2, 10))
            tours.add(CityTour("manon", "Manon", 2, 1, 4, "Randonné", 4, 6))
            tours.add(CityTour("marie", "Marie", 0, 4, 1, "Balade", 2, 50))
            tours.add(CityTour("claudia", "Claudia", 1, 6, 9, "Shopping", 50, 50))
            tours.add(CityTour("margot", "Margot", 3, 7, 7, "Culture", 2, 3))
            tours.add(CityTour("pauline", "Pauline", 4, 2, 0, "Sport", 8, 10))
            tours.add(CityTour("flora", "Flora", 3, 3, 18, "Religieux", 1, 5))
        }
        else {
            tours.add(CityTour("c", "Cédric", 2, 3, 5, "Patrimoine", 0, 1))
            tours.add(CityTour("solange", "Solange", 5, 8, 42, "Gastronomie", 1, 2))
            tours.add(CityTour("b", "Bastien", 4, 3, 10, "Culture", 2, 10))
            tours.add(CityTour("marie", "Marie", 2, 1, 4, "Restauration", 4, 6))
            tours.add(CityTour("f", "Florian", 0, 4, 1, "Shopping", 2, 50))
            tours.add(CityTour("j", "John", 5, 1, 1, "Patisserie", 50, 50))
            tours.add(CityTour("g", "Mr Crabe", 3, 7, 7, "Randonné", 2, 3))
            tours.add(CityTour("pauline", "Pauline", 4, 2, 0, "Sport", 8, 10))
        }
        tours.add(CityTour("vial", "Christian", 5, 48, 0, "Programmation", 42, 99))
        return tours
    }
}
