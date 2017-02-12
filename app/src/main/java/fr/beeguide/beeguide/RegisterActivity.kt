package fr.beeguide.beeguide

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class RegisterActivity : AppCompatActivity() {

    private val TAG = "SignupActivity"

    val nameText: EditText by lazy { findViewById(R.id.input_name) as EditText }
    val emailText: EditText by lazy { findViewById(R.id.input_email) as EditText }
    val passwordText: EditText by lazy { findViewById(R.id.input_password) as EditText }
    val signupButton: Button by lazy { findViewById(R.id.btn_signup) as Button }
    val loginLink: TextView by lazy { findViewById(R.id.link_login) as TextView }
    val birthdayText: EditText by lazy { findViewById(R.id.input_birthday) as EditText }
    val phoneText: EditText by lazy { findViewById(R.id.input_phone) as EditText }

    private var mRegistrationTask: UserRegistrationTask? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent = intent
        val username = intent.getStringExtra(getString(R.string.intent_email))

        emailText.text = SpannableStringBuilder(username)

        signupButton.setOnClickListener { signup() }

        loginLink.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            finish()
        }
    }

    fun signup() {
        Log.d(TAG, "Signup")

        if (!validate()) {
            onSignupFailed()
            return
        }

        signupButton.isEnabled = false

        progressDialog = ProgressDialog(this@RegisterActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setMessage("Creating Account...")
        progressDialog!!.show()

        val name = nameText.text.toString()
        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        val phone = phoneText.text.toString()
        val birthday = birthdayText.text.toString()

        mRegistrationTask = UserRegistrationTask(email, password, birthday, name, phone, this)
        mRegistrationTask!!.execute()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Signup failed", Toast.LENGTH_LONG).show()
        signupButton.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        val name = nameText.text.toString()
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        if (name.isEmpty() || name.length < 3) {
            nameText.error = "At least 3 characters"
            valid = false
        } else {
            nameText.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.error = "Enter a valid email address"
            valid = false
        } else {
            emailText.error = null
        }

        if (password.isEmpty() || password.length < 4) {
            passwordText.error = "At least 4 alphanumeric characters"
            valid = false
        } else {
            passwordText.error = null
        }

        return valid
    }

    /**
     * Represents an asynchronous registration task used to register
     * the user.
     */
    inner class UserRegistrationTask internal constructor(
            private val mEmail: String, private val mPassword: String,
            private val mBirthday: String, private val name: String,
            private val mPhone: String,
            private val mContext: Context) : AsyncTask<Void, Void, Boolean>() {

        private val mHashed: String = LoginActivity.passwordEncryption(mPassword)

        override fun doInBackground(vararg params: Void): Boolean? {

            val mDbHelper = UserDbHelper(mContext)
            val db = mDbHelper.writableDatabase

            val form = Base64.encodeToString(
                    (mEmail + ':' + mPassword + ':' +
                            name + ':' + mPhone + ':' + mBirthday).toByteArray(),
                    Base64.DEFAULT)
            try {
                val url = URL("http://api.beeguide.fr/?r=" + form)
                val urlConnection = url.openConnection() as HttpURLConnection
                val `in` = BufferedInputStream(urlConnection.inputStream)
                Log.i("IS", "" + `in`.read())
                urlConnection.disconnect()
            } catch (e: Exception) {
                Log.i("API_ERROR", e.message)
            }

            // Create a new map of values, where column names are the keys
            val values = ContentValues()
            values.put("username", mEmail)
            values.put("password", mHashed)
            values.put("name", name)
            values.put("birthday", mBirthday)
            values.put("phone", mPhone)

            // Insert the new row, returning the primary key value of the new row
            return db.insert("User", null, values) != -1L
        }

        override fun onPostExecute(success: Boolean?) {
            mRegistrationTask = null
            progressDialog!!.dismiss()
            if (success!!) {
                signupButton.isEnabled = true
                setResult(Activity.RESULT_OK, null)
                finish()
            } else {
                onSignupFailed()
            }
        }

        override fun onCancelled() {
            mRegistrationTask = null
        }
    }
}


