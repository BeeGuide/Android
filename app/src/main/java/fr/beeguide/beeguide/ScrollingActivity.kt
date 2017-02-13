package fr.beeguide.beeguide

import android.location.Location
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.*
import fr.beeguide.beeguide.model.CityTour
import fr.beeguide.beeguide.model.Sex
import fr.beeguide.beeguide.model.User
import java.util.*
import android.widget.AdapterView.OnItemClickListener






class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val intent = intent
        val city = Location("")
        city.latitude = intent.getDoubleExtra(getString(R.string.intent_location_latitude), 0.0)
        city.longitude = intent.getDoubleExtra(getString(R.string.intent_location_longitude), 0.0)
        val name = intent.getStringExtra(getString(R.string.intent_location_name))
        title = name
        changeHeader(name)

        val mListView = findViewById(R.id.listView) as ListView
        val emptyText = findViewById(android.R.id.empty) as TextView
        mListView.emptyView = emptyText
        mListView.adapter = CityTourAdapter(this, getCityTours(city))
        mListView.setOnTouchListener { v, event ->
            // Setting on Touch Listener for handling the touch inside ScrollView
            // Disallow the touch request for parent scroll on touch of child view
            (findViewById(R.id.app_bar) as AppBarLayout).setExpanded(false)
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        mListView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val item = (view as RelativeLayout)
            Toast.makeText(baseContext, "item", Toast.LENGTH_LONG).show()
        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            finish()
        }
    }

    fun changeHeader(city: String) {
        // Webservice here, maybe ?
        val cityView = findViewById(R.id.imageHeader) as ImageView
        val spliced = city.split(",")
        when(spliced[0]) {
            "Paris" -> cityView.setImageResource(R.drawable.paris)
            "Toulouse" -> cityView.setImageResource(R.drawable.toulouse)
            "Givors" -> cityView.setImageResource(R.drawable.givors)
            "Lille" -> cityView.setImageResource(R.drawable.lille)
            "Marseille" -> cityView.setImageResource(R.drawable.marseille)
            "Chateau-neuf-les-martigues" -> cityView.setImageResource(R.drawable.chateau9)
        }
    }

    fun getCityTours(requestedLocation: Location): List<CityTour> {

        // Cities of our guys
        val lyon: Location = Location("")
        lyon.latitude = 45.764043
        lyon.longitude = 4.835658999999964
        val paris: Location = Location("")
        paris.latitude = 48.85661400000001
        paris.longitude = 2.3522219000000177
        val marseille: Location = Location("")
        marseille.latitude = 43.296482
        marseille.longitude = 5.369779999999992

        // Bitchies to focus the user
        val alexandra: User = User("Alexandra", "Alexandra", "Lastname", Date(), Sex.FEMALE, "picture")
        val diane: User = User("Diane", "Diane", "Lastname", Date(), Sex.FEMALE, "diane")
        val solange: User = User("Solange", "Solange", "Lastname", Date(), Sex.FEMALE, "solange")
        val marion: User = User("Marion", "Marion", "Lastname", Date(), Sex.FEMALE, "marion")
        val manon: User = User("Manon", "Manon", "Lastname", Date(), Sex.FEMALE, "manon")
        val marie: User = User("Marie", "Marie", "Lastname", Date(), Sex.FEMALE, "marie")
        val claudia: User = User("Claudia", "Claudia", "Lastname", Date(), Sex.FEMALE, "claudia")
        val margot: User = User("Margot", "Margot", "Lastname", Date(), Sex.FEMALE, "margot")
        val pauline: User = User("Pauline", "Pauline", "Lastname", Date(), Sex.FEMALE, "pauline")
        val flora: User = User("Flora", "Flora", "Lastname", Date(), Sex.FEMALE, "flora")

        // Gentleman
        val cedric: User = User("Cedric", "Cyril", "Couturier", Date(), Sex.MALE, "c")
        val bastien: User = User("Bastien", "Bastien", "Guyl", Date(), Sex.MALE, "b")
        val florian: User = User("Florian", "Florian", "Martin", Date(), Sex.MALE, "f")
        val john: User = User("John", "Jonathan", "Platteau", Date(), Sex.MALE, "j")
        val crabe: User = User("Mr.Crabe", "Gaëtan", "Roche", Date(), Sex.MALE, "g")
        val vial: User = User("Chrichri", "Christian", "Vial", Date(), Sex.MALE, "vial")


        val toursAvailables = ArrayList<CityTour>()
        toursAvailables.add(CityTour(alexandra, 4, 2, 12, "Historique", 2, 5, lyon))
        toursAvailables.add(CityTour(cedric, 2, 3, 5, "Patrimoine", 0, 1, lyon))
        toursAvailables.add(CityTour(diane, 4, 1, 25, "Insolite", 0, 1, paris))
        toursAvailables.add(CityTour(solange, 5, 8, 42, "Gastronomie", 1, 2, lyon))
        toursAvailables.add(CityTour(marion, 4, 3, 10, "Culture", 2, 10, paris))
        toursAvailables.add(CityTour(john, 5, 1, 1, "Patisserie", 50, 50, lyon))
        toursAvailables.add(CityTour(manon, 2, 1, 4, "Randonné", 4, 6, lyon))
        toursAvailables.add(CityTour(crabe, 3, 7, 7, "Randonné", 2, 3, lyon))
        toursAvailables.add(CityTour(marie, 0, 4, 1, "Balade", 2, 50, marseille))
        toursAvailables.add(CityTour(claudia, 1, 6, 9, "Shopping", 50, 50, paris))
        toursAvailables.add(CityTour(margot, 3, 7, 7, "Culture", 2, 3, paris))
        toursAvailables.add(CityTour(pauline, 4, 2, 0, "Sport", 8, 10, marseille))
        toursAvailables.add(CityTour(flora, 3, 3, 18, "Religieux", 1, 5, paris))
        toursAvailables.add(CityTour(solange, 5, 8, 42, "Gastronomie", 1, 2, marseille))
        toursAvailables.add(CityTour(bastien, 4, 3, 10, "Culture", 2, 10, paris))
        toursAvailables.add(CityTour(marie, 2, 1, 4, "Restauration", 4, 6, paris))
        toursAvailables.add(CityTour(florian, 0, 4, 1, "Shopping", 2, 50, marseille))
        toursAvailables.add(CityTour(pauline, 4, 2, 0, "Sport", 8, 10, lyon))
        toursAvailables.add(CityTour(vial, 5, 48, 0, "Programmation", 42, 99, lyon))

        // We filter by location
        val requestedTours = toursAvailables.filter { requestedLocation.distanceTo(it.location) < 50 }

        return requestedTours
    }
}
