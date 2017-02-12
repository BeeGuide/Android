package fr.beeguide.beeguide.model

import android.location.Geocoder
import java.util.*

class CityTour(
        var guide: User,
        var rating: Byte,
        var duration: Byte,
        var price: Byte,
        var thematic: String,
        var occupation: Byte,
        var capacity: Byte,
        //var localisation: Geocoder,
        var guests: List<User>
) {
    constructor(guide: User, rating: Byte, duration: Byte, price: Byte, thematic: String, occupation: Byte, capacity: Byte) :
            this(guide, rating, duration, price, thematic, occupation, capacity, ArrayList<User>()
    )
}