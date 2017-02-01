package fr.beeguide.beeguide

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        val convertView = _convertView ?: LayoutInflater.from(context).inflate(R.layout.row_city_tour, parent, false)

        var ct = convertView.tag as? CityTourViewHolder
        if (ct == null) {
            ct = CityTourViewHolder()
            ct.name = convertView.findViewById(R.id.name) as TextView
            ct.thematique = convertView.findViewById(R.id.thematique) as TextView
            ct.profilePicture = convertView.findViewById(R.id.profilePicture) as ImageView
            ct.rating = convertView.findViewById(R.id.rating) as RatingBar
            ct.price = convertView.findViewById(R.id.price) as TextView
            ct.duration = convertView.findViewById(R.id.duration) as TextView
            ct.occupation = convertView.findViewById(R.id.occupation) as TextView
            convertView.tag = ct
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> cityTours
        val c: CityTour = getItem(position)

        //il ne reste plus qu'à remplir notre vue
        ct.name?.text = c.name
        ct.thematique?.text = c.thematique
        ct.price?.text = String.format(context.getString(R.string.price), c.price)
        ct.duration?.text = String.format(context.getString(R.string.duration), c.duration)
        ct.occupation?.text = String.format(context.getString(R.string.occupation), c.occupation, c.capacity)
        ct.rating?.rating = c.rating.toFloat()
        ct.rating?.numStars = 5
        val id = context.resources.getIdentifier(c.picture, "drawable", context.packageName)
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