package fr.beeguide.beeguide

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager

import android.location.Location
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.location.places.PlaceBuffer
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLng
import java.util.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    val TAG = "SampleActivityBase"

    val PLACE_PICKER_REQUEST = 1

    val cityView: AutoCompleteTextView by lazy { findViewById(R.id.autocomplete_places) as AutoCompleteTextView }
    val dateView: TextView by lazy { findViewById(R.id.dateView) as TextView }

    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    private var mAutocompleteView: AutoCompleteTextView? = null
    private var mAdapter: PlaceAutocompleteAdapter? = null
    var requestLocation: Location = Location("")

    var year = 0
    var month = 0
    var day = 0

    private var datePickerDialog: DatePickerDialog? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val goButton = findViewById(R.id.button) as FloatingActionButton
        val profilButton = findViewById(R.id.fab) as Button
        val placeInput = findViewById(R.id.textView2) as TextView

        val intent = intent
        // TODO: User is not here, idiot !
        val user = intent.getStringExtra(getString(R.string.token))
        title = String.format(getString(R.string.logged_message), user)

        cityView.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.go || id == EditorInfo.IME_NULL) {
                go(requestLocation, cityView.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        goButton.setOnClickListener({ go(requestLocation, cityView.text.toString()) })

        //var autocompleteFragment = fragmentManager.findFragmentById(R.id.autocomplete_places) as PlaceAutocompleteFragment
        //autocompleteFragment.setOnPlaceSelectedListener(this);

        mAutocompleteView = findViewById(R.id.autocomplete_places) as AutoCompleteTextView
        mAutocompleteView!!.onItemClickListener = mAutocompleteClickListener

        //placeInput.setOnEditorActionListener { textView, i, keyEvent -> onEditorAction(textView, i, keyEvent) }

        profilButton.setOnClickListener { fab() }
        /*/val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Bitch!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        instantiateGoogleAPIClient()

        mAdapter = PlaceAutocompleteAdapter(this, mGoogleApiClient, null, null)
        if(mAutocompleteView != null){
            mAutocompleteView!!.setAdapter(mAdapter)
        }

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) +1
        day = calendar.get(Calendar.DAY_OF_MONTH)
        showDate(year,month,day)
        dateView.setOnClickListener { showDatePicker() }
    }

    fun showDate(year: Int, month:Int, day:Int){
        dateView.text = StringBuilder().append(day).append("/").append(month).append("/").append(year)
    }

    private val myDateListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener {
                datePicker, year, month, day -> showDate(year, month+1, day)
            }

    fun showDatePicker() {
        datePickerDialog = DatePickerDialog(this, myDateListener, year, month, day)
        datePickerDialog!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                val toastMsg = String.format("Place: %s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun instantiateGoogleAPIClient(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Wut ?
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

    fun go(location: Location?, name: String) {

        if (location == null) {
            cityView.error = getString(R.string.error_field_required)
        }  else {
            val intent = Intent(this, ScrollingActivity::class.java)
            intent.putExtra(getString(R.string.intent_location_latitude), location.latitude)
            intent.putExtra(getString(R.string.intent_location_longitude), location.longitude)
            intent.putExtra(getString(R.string.intent_location_name), name)
            startActivity(intent)
        }
    }

    fun fab(){
        val intent = Intent(this, ProfilActivity::class.java)
        startActivity(intent)
    }

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.

     * @see com.google.android.gms.location.places.GeoDataApi.getPlaceById
     */
    private val mAutocompleteClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        /*
         Retrieve the place ID of the selected item from the Adapter.
         The adapter stores each Place suggestion in a AutocompletePrediction from which we
         read the place ID and title.
          */
        val item: AutocompletePrediction = mAdapter!!.getItem(position)
        val placeId = item.placeId
        val primaryText = item.getPrimaryText(null)

        Log.i(TAG, "Autocomplete item selected: " + primaryText)

        /*
         Issue a request to the Places Geo Data API to retrieve a Place object with additional
         details about the place.
          */
        val placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId)
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback)

        Toast.makeText(applicationContext, "Clicked: " + primaryText,
                Toast.LENGTH_SHORT).show()

        var places: PendingResult<PlaceBuffer> = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
            .setResultCallback(mUpdatePlaceDetailsCallback)

        Log.i(TAG, "Called getPlaceById to get Place details for " + placeId!!)
    }

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private val mUpdatePlaceDetailsCallback = ResultCallback<PlaceBuffer> { places ->
        if (!places.status.isSuccess) {
            // Request did not complete successfully
            Log.e(TAG, "Place query did not complete. Error: " + places.status.toString())
            places.release()
            return@ResultCallback
        }
        // Get the Place object from the buffer.
        val place = places.get(0)

        // Format details of the place for display and show it in a TextView.

        // Display the third party attributions if set.
//        val thirdPartyAttribution = places.attributions
//        if (thirdPartyAttribution == null) {
//            mPlaceDetailsAttribution.setVisibility(View.GONE)
//        } else {
//            mPlaceDetailsAttribution.setVisibility(View.VISIBLE)
//            mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()))
//        }

        var latLng: LatLng = place.latLng
        requestLocation.latitude = latLng.latitude
        requestLocation.longitude = latLng.longitude

        Log.i(TAG, "Place details received: " + place.name)

        places.release()
    }

    /*private fun formatPlaceDetails(res: Resources, name: CharSequence, id: String,
                                   address: CharSequence, phoneNumber: CharSequence, websiteUri: Uri): Spanned {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri))
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri))

    }*/

}
