package fr.beeguide.beeguide

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import fr.beeguide.beeguide.model.CityTour
import org.w3c.dom.Text

class CityTourDetailActivity : AppCompatActivity() {

    val guideView: TextView by lazy { findViewById(R.id.guide_detail) as TextView }
    val thematicView: TextView by lazy { findViewById(R.id.thematic_detail) as TextView }
    val ratingView: RatingBar by lazy { findViewById(R.id.rating_detail) as RatingBar }
    val occupationView: TextView by lazy { findViewById(R.id.occupation_detail) as TextView }
    val priceView: TextView by lazy { findViewById(R.id.price_detail) as TextView }
    val tourImg: ImageView by lazy { findViewById(R.id.tour_img) as ImageView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_tour_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val intent = intent


        val username: String = intent.getStringExtra("ex_username")
        val thematic: String = intent.getStringExtra("ex_thematic")
        val rating: Int = intent.getIntExtra("ex_rating", 0)
        val occupation: String = intent.getStringExtra("ex_occupation")
        val price: String = intent.getStringExtra("ex_price")
        val picture: String = intent.getStringExtra("ex_picture")

        guideView.text = username
        thematicView.text = thematic
        ratingView.numStars = rating
        occupationView.text = occupation
        priceView.text = price+"€"
        tourImg.setImageResource(resources.getIdentifier(picture, "drawable", packageName))

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Booked ! $username will wait for you !", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
