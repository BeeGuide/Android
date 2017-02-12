package fr.beeguide.beeguide

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import fr.beeguide.beeguide.model.CityTour


class CityTourAdapter(context: Context, cityTours: List<CityTour>) : ArrayAdapter<CityTour>(context, 0, cityTours) {

    private inner class CityTourViewHolder {
        var name: TextView? = null
        var thematique: TextView? = null
        var profilePicture: ImageView? = null
        var rating: RatingBar? = null
        var price: TextView? = null
        var occupation: TextView? = null
        var duration: TextView? = null
    }

    override fun getView(position: Int, _convertView: View?, parent: ViewGroup): View {

        val convertView: View
        val ct: CityTourViewHolder

        if(_convertView == null) {
            convertView = (context as Activity).layoutInflater.inflate(R.layout.row_city_tour, parent, false)
            ct = CityTourViewHolder()
            ct.name = convertView.findViewById(R.id.name) as TextView
            ct.thematique = convertView.findViewById(R.id.thematique) as TextView
            ct.profilePicture = convertView.findViewById(R.id.profilePicture) as ImageView
            ct.rating = convertView.findViewById(R.id.rating) as RatingBar
            ct.price = convertView.findViewById(R.id.price) as TextView
            ct.duration = convertView.findViewById(R.id.duration) as TextView
            ct.occupation = convertView.findViewById(R.id.occupation) as TextView
            convertView.tag = ct
        } else {
            convertView = _convertView
            ct = convertView.tag as CityTourViewHolder
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> cityTours
        val c: CityTour = getItem(position)

        //il ne reste plus qu'à remplir notre vue
        ct.name?.text = c.guide.username
        ct.thematique?.text = c.thematic
        ct.price?.text = String.format(context.getString(R.string.price), c.price)
        ct.duration?.text = String.format(context.getString(R.string.duration), c.duration)
        ct.occupation?.text = String.format(context.getString(R.string.occupation), c.occupation, c.capacity)
        ct.rating?.rating = c.rating.toFloat()
        ct.rating?.numStars = 5
        val id = context.resources.getIdentifier(c.guide.picture, "drawable", context.packageName)
        ct.profilePicture?.setImageDrawable(ContextCompat.getDrawable(context, id))

        val occupation_percent: Float = c.occupation.toFloat() / c.capacity.toFloat()
        if(occupation_percent <= 0.25)
            ct.occupation?.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
        else if(occupation_percent <= 0.75)
            ct.occupation?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        else
            ct.occupation?.setTextColor(ContextCompat.getColor(context, R.color.colorCommunism))

        return convertView
    }
}