package com.example.services

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_signin.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signin_link_btn.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        signup_btn.setOnClickListener {
            CreateAccount()
        }
    }

    private fun CreateAccount()
    {
        val fullName = fullname_signup.text.toString()
        val userName = username_signup.text.toString()
        val email = email_signup.text.toString()
        val password = password_signup.text.toString()
        val phone = phone_signup.text.toString()

        when{
            TextUtils.isEmpty(fullName)-> Toast.makeText(this, "FullName is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName)-> Toast.makeText(this, "UserName is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email)-> Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password)-> Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(phone)-> Toast.makeText(this, "Phone is required", Toast.LENGTH_LONG).show()

            else ->{

                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("Creating Account !")
                progressDialog.setMessage("Please Wait......")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful)
                        {
                            saveUserInfo(userName, fullName, email, phone, progressDialog)
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error! $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(userName: String, fullName: String, email: String, phone: String, progressDialog: ProgressDialog)
    {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullName.toLowerCase()
        userMap["username"] = userName.toLowerCase()
        userMap["phone"] = phone
        userMap["email"] = email
        userMap["bio"] = "Hire Me!"
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/service-map-e0318.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=f57f2488-21cc-410d-b6c8-e5aca786bd25"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if(task.isSuccessful)
                {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show()


                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(currentUserID)
                            .child("Following").child(currentUserID)
                            .setValue(true)


                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error! $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}
