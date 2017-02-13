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

    val guideView: TextView by lazy { findViewById(R.id.guide) as TextView }
    val thematicView: TextView by lazy { findViewById(R.id.thematic) as TextView }
    val ratingView: RatingBar by lazy { findViewById(R.id.rating) as RatingBar }
    val occupationView: TextView by lazy { findViewById(R.id.occupation) as TextView }
    val priceView: TextView by lazy { findViewById(R.id.price) as TextView }
    val tourImg: ImageView by lazy { findViewById(R.id.tour_img) as ImageView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_tour_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val intent = intent

        var tour:CityTour = intent.getSerializableExtra("tour") as CityTour



        guideView.text = tour.guide.username
        thematicView.text = tour.thematic
        ratingView.numStars = tour.rating.toInt()
        occupationView.text = tour.occupation.toString()
        priceView.text = tour.price.toString()
        tourImg.setImageResource(resources.getIdentifier(tour.guide.picture, "drawable", packageName))

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
