package com.example.ido_list

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {


    // DECLARE FIREBASE AUTH VARIABLE
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth


        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        // CLICK LISTENER
        LoginButton.setOnClickListener {

            doLogin()

        }

    }

    private fun doLogin() {


        val dataEmail_login : String = editTextEmail_login.text.toString()
        val dataPass_login : String = editTextPassword_login.text.toString()


        // EMAIL VALIDATION
        if (dataEmail_login.isNotEmpty()){
            if (validateEmail(dataEmail_login))
            {

            }
            else
            {
                editTextEmail_login.error="Email is invalid"
                return
            }
        }
        else
        {
            editTextEmail_login.error = "Field can't be empty!"
            return
        }



        // PASSWORD VALIDATION
        if (dataPass_login.isNotEmpty())
        {

        }
        else
        {
            editTextPassword_login.error="Password field can't be empty!"
            return
        }


        // LOGIN OUR USER
        auth.signInWithEmailAndPassword(dataEmail_login, dataPass_login)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }

    }


    // VALIDATE EMAIL FUNCTION
    private fun validateEmail(dataEmail_login: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(dataEmail_login).matches()
    }



    // FOR USER STAY LOGGED IN ONCE HE/SHE LOGIN
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }



    // IF WE FIND USER THEN MOVE TO NEXT ACTIVITY
    // ELSE SHOW A TOAST
    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null)
        {

            // TO CHECK USER EMAIL VERIFICATION
            if (currentUser.isEmailVerified)
            {
                val i = Intent(this,Dashboard::class.java)
                startActivity(i)
                finish()
            }else
            {
                Toast.makeText(baseContext, "Please verify your email first",
                    Toast.LENGTH_SHORT).show()
            }

        }else
        {
            Toast.makeText(baseContext, "User not found!",
                Toast.LENGTH_SHORT).show()
        }
    }


    // LOGIN SCREEN TO REGISTER SCREEN
    fun onLoginClick(View: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
    }

}