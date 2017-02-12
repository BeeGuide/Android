package fr.beeguide.beeguide

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private val AVAILABLE_CITIES = arrayOf("Lyon", "Givors", "Paris", "Toulouse",
            "Marseille", "Lille", "Chateauneuf-les-martigues")

    val cityView: AutoCompleteTextView by lazy { findViewById(R.id.city) as AutoCompleteTextView }
    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        println("Hello")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val goButton = findViewById(R.id.button) as FloatingActionButton
        val fabButton = findViewById(R.id.fab) as Button

        val intent = intent
        // TODO: User is not here, idiot !
        val user = intent.getStringExtra(getString(R.string.token))
        title = String.format(getString(R.string.logged_message), user)

        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, AVAILABLE_CITIES)
        cityView.setAdapter<ArrayAdapter<String>>(adapter)
        cityView.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.go || id == EditorInfo.IME_NULL) {
                go(cityView.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        goButton.setOnClickListener({ go(cityView.text.toString()) })

        fabButton.setOnClickListener { fab() }
        /*/val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Bitch!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        instantiateGoogleAPIClient()
    }

    fun instantiateGoogleAPIClient(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        }
    }

    override fun onStart() {
        mGoogleApiClient?.connect()
        super.onStart()
    }

    override fun onStop() {
        mGoogleApiClient?.disconnect()
        super.onStop()
    }

    override fun onConnected(p0: Bundle?) {
        var mLatitudeText: String
        var mLongitudeText: String

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

        if(mLastLocation != null){
            println(mLastLocation!!.latitude)
            println(mLastLocation!!.longitude)
        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        println("Connection failed ...")
        println(p0.errorCode)
        println(p0.errorMessage)
        println(p0)
    }

    override fun onConnectionSuspended(p0: Int) {
        println("Connection suspended ...")
    }

    fun go(city: String) {

        if (TextUtils.isEmpty(city)) run {
            cityView.error = getString(R.string.error_field_required)
        } else if (!AVAILABLE_CITIES.contains(city)) {
            cityView.error = getString(R.string.error_not_available)
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
