package fr.beeguide.beeguide.model

import java.io.Serializable
import java.util.*

class User (
        var username: String,
        var firstname: String,
        var lastname: String,
        var birthday: Date,
        var sex: Sex,
        var picture: String
) : Serializable

enum class Sex : Serializable {
        FEMALE, MALE
}