package fr.beeguide.beeguide.model

import java.util.*

class User (
        var username: String,
        var firstname: String,
        var lastname: String,
        var birthday: Date,
        var sex: Sex,
        var picture: String
)

enum class Sex {
        FEMALE, MALE
}