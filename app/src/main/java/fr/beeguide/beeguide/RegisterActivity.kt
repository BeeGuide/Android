package fr.beeguide.beeguide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent = intent
        val username = intent.getStringExtra(getString(R.string.intent_email))
        title = String.format(getString(R.string.logged_message), username)
    }
}
