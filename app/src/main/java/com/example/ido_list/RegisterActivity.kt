package com.example.ido_list

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.Window
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth

        RegisterButton.setOnClickListener {

            registerUser()
        }

        changeStatusBarColor()

    }

    private fun registerUser() {

        val dataName : String = editTextName.text.toString()
        val dataEmail : String = editTextEmail.text.toString()
        val dataPhone : String = editTextPhone.text.toString()
        val dataPassword : String = editTextPassword.text.toString()


        // NAME VALIDATION
        if (dataName.isNotEmpty())
        {
            if (dataName.length <= 9)
            {

            }
            else {
                editTextName.error = "Name length must be less than or equal 9"
                return
            }

        }
        else {
            editTextName.error = "First Name can't be empty"
            return }



        // EMAIL VALIDATION
        if (dataEmail.isNotEmpty()){
            if (validateEmail(dataEmail))
            {

            }
            else
            {
                editTextEmail.error="Email is invalid!"
                return }
        }
        else
        {
            editTextEmail.error = "Email field can't be empty!"
            return }



        // PHONE VALIDATION
        if (dataPhone.isNotEmpty())
        {

        }
        else
        {
            editTextPhone.error="Phone field can't be empty!"
            return
        }



        // PASSWORD VALIDATION
        if (dataPassword.isNotEmpty())
        {
            if(dataPassword.length <= 8)
            {

            }
            else
            {
                editTextPassword.error="Password must be less than or equal to 8"
                return
            }
        }
        else
        {
            editTextPassword.error="Password field can't be empty!"
            return
        }



        auth.createUserWithEmailAndPassword(dataEmail, dataPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val i = Intent(this,LoginActivity::class.java)
                    // To Remove very previous Activity
               //     i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(i)

                } else {

                    Toast.makeText(baseContext, "Authentication failed!",
                        Toast.LENGTH_SHORT).show()

                }
            }




    }

    private fun validateEmail(dataEmail: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(dataEmail).matches()
    }


    private fun changeStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //            window.setStatusBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(resources.getColor(R.color.register_bk_color))
    }

    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay)
    }

}