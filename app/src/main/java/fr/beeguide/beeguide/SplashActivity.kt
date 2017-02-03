package fr.beeguide.beeguide

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch if the token is stored
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_token), MODE_PRIVATE)
        val defaultToken = getString(R.string.token_default)
        val token = sharedPref.getString(getString(R.string.token), defaultToken)

        // If there is no token (maybe check for a TTL in the future)
        if (token == defaultToken) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
