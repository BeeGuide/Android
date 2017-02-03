package fr.beeguide.beeguide

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


class MainActivity : AppCompatActivity() {

    private val AVAILABLE_CITIES = arrayOf("Lyon", "Givors", "Paris", "Toulouse")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val cityView = findViewById(R.id.city) as AutoCompleteTextView
        val goButton = findViewById(R.id.button) as FloatingActionButton
        val fabButton = findViewById(R.id.fab) as FloatingActionButton

        val intent = intent
        // TODO: User is not here, idiot !
        val user = intent.getStringExtra(getString(R.string.token))
        title = String.format(getString(R.string.logged_message), user)

        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, AVAILABLE_CITIES)
        cityView.setAdapter<ArrayAdapter<String>>(adapter)

        goButton.setOnClickListener({ go(cityView.text.toString()) })

        fabButton.setOnClickListener { fab() }
        /*/val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Bitch!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
    }

    fun go(city: String) {

        if (TextUtils.isEmpty(city)) run {
            //mEmailView.setError(getString(R.string.error_field_required))
            //focusView = mEmailView
        } else if (!AVAILABLE_CITIES.contains(city)) {
            //mEmailView.setError(getString(R.string.error_not_available))
            //focusView = mEmailView
        } else {
            val intent = Intent(this, ScrollingActivity::class.java)
            intent.putExtra(getString(R.string.intent_city), city)
            startActivity(intent)
        }
    }

    fun fab(){
        val intent = Intent(this, ProfilActivity::class.java)
        startActivity(intent)
    }

}
