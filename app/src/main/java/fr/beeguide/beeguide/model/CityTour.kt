package fr.beeguide.beeguide.model

import android.location.Location
import java.io.Serializable

class CityTour (
        var guide: User,
        var rating: Byte,
        var duration: Byte,
        var price: Byte,
        var thematic: String,
        var occupation: Byte,
        var capacity: Byte,
        var location: Location
) : Serializable