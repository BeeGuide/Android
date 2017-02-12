package fr.beeguide.beeguide

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.ListView
import fr.beeguide.beeguide.model.CityTour
import fr.beeguide.beeguide.model.Sex
import fr.beeguide.beeguide.model.User
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

        var alexandra: User = User("Alexandra", "Alexandra", "Lastname", Date(), Sex.FEMALE, "picture")
        var diane: User = User("Diane", "Diane", "Lastname", Date(), Sex.FEMALE, "diane")
        var solange: User = User("Solange", "Solange", "Lastname", Date(), Sex.FEMALE, "solange")
        var marion: User = User("Marion", "Marion", "Lastname", Date(), Sex.FEMALE, "marion")
        var manon: User = User("Manon", "Manon", "Lastname", Date(), Sex.FEMALE, "manon")
        var marie: User = User("Marie", "Marie", "Lastname", Date(), Sex.FEMALE, "marie")
        var claudia: User = User("Claudia", "Claudia", "Lastname", Date(), Sex.FEMALE, "claudia")
        var margot: User = User("Margot", "Margot", "Lastname", Date(), Sex.FEMALE, "margot")
        var pauline: User = User("Pauline", "Pauline", "Lastname", Date(), Sex.FEMALE, "pauline")
        var flora: User = User("Flora", "Flora", "Lastname", Date(), Sex.FEMALE, "flora")


        var cedric: User = User("Cedric", "Cyril", "Couturier", Date(), Sex.MALE, "c")
        var bastien: User = User("Bastien", "Bastien", "Guyl", Date(), Sex.MALE, "b")
        var florian: User = User("Florian", "Florian", "Martin", Date(), Sex.MALE, "f")
        var john: User = User("John", "Jonathan", "Platteau", Date(), Sex.MALE, "j")
        var crabe: User = User("Mr.Crabe", "Gaëtan", "Roche", Date(), Sex.MALE, "g")
        var vial: User = User("Chrichri", "Christian", "Vial", Date(), Sex.MALE, "vial")

        // Webservice here, maybe ?
        val tours = ArrayList<CityTour>()
        tours.add(CityTour(alexandra, 4, 2, 12, "Historique", 2, 5))
        if(city == "Givors")
        {
            tours.add(CityTour(diane, 4, 1, 25, "Insolite", 0, 1))
            tours.add(CityTour(solange, 5, 8, 42, "Gastronomie", 1, 2))
            tours.add(CityTour(marion, 4, 3, 10, "Culture", 2, 10))
            tours.add(CityTour(manon, 2, 1, 4, "Randonné", 4, 6))
            tours.add(CityTour(marie, 0, 4, 1, "Balade", 2, 50))
            tours.add(CityTour(claudia, 1, 6, 9, "Shopping", 50, 50))
            tours.add(CityTour(margot, 3, 7, 7, "Culture", 2, 3))
            tours.add(CityTour(pauline, 4, 2, 0, "Sport", 8, 10))
            tours.add(CityTour(flora, 3, 3, 18, "Religieux", 1, 5))
        }
        else {
            tours.add(CityTour(cedric, 2, 3, 5, "Patrimoine", 0, 1))
            tours.add(CityTour(solange, 5, 8, 42, "Gastronomie", 1, 2))
            tours.add(CityTour(bastien, 4, 3, 10, "Culture", 2, 10))
            tours.add(CityTour(marie, 2, 1, 4, "Restauration", 4, 6))
            tours.add(CityTour(florian, 0, 4, 1, "Shopping", 2, 50))
            tours.add(CityTour(john, 5, 1, 1, "Patisserie", 50, 50))
            tours.add(CityTour(crabe, 3, 7, 7, "Randonné", 2, 3))
            tours.add(CityTour(pauline, 4, 2, 0, "Sport", 8, 10))
        }
        tours.add(CityTour(vial, 5, 48, 0, "Programmation", 42, 99))
        return tours
    }
}
