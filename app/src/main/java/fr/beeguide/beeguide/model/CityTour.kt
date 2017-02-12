package fr.beeguide.beeguide.model

class CityTour(
        var guide: User,
        var rating: Byte,
        var duration: Byte,
        var price: Byte,
        var thematic: String,
        var occupation: Byte,
        var capacity: Byte/*,
        var guests: List<User>*/
)