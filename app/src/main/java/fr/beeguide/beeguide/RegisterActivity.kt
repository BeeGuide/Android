package fr.beeguide.beeguide

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class RegisterActivity : AppCompatActivity() {

    private val TAG = "SignupActivity"

    val nameText: EditText by lazy { findViewById(R.id.input_name) as EditText }
    val emailText: EditText by lazy { findViewById(R.id.input_email) as EditText }
    val passwordText: EditText by lazy { findViewById(R.id.input_password) as EditText }
    val signupButton: Button by lazy { findViewById(R.id.btn_signup) as Button }
    val loginLink: TextView by lazy { findViewById(R.id.link_login) as TextView }
    val birthday: EditText by lazy { findViewById(R.id.input_birthday) as EditText }

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

        val progressDialog = ProgressDialog(this@RegisterActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        val name = nameText.text.toString()
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        // TODO: Implement your own signup logic here.

        android.os.Handler().postDelayed(
                {
                    // On complete call either onSignupSuccess or onSignupFailed
                    // depending on success
                    onSignupSuccess()
                    // onSignupFailed();
                    progressDialog.dismiss()
                }, 3000)
    }


    fun onSignupSuccess() {
        signupButton.isEnabled = true
        setResult(Activity.RESULT_OK, null)
        finish()
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
}
