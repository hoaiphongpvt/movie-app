package com.example.project_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.project_android.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var btnSignInGoogle : Button
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginViewModel: LoginViewModel
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        loginViewModel = LoginViewModel(this)

        btnLogin.setOnClickListener {
            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                loginViewModel.login(username.text.toString(), password.text.toString()) { result, sessionID ->
                    if (result) {
                         AestheticDialog.Builder(this, DialogStyle.EMOTION, DialogType.SUCCESS)
                            .setTitle("Success")
                            .setMessage("Log in successfully.")
                            .setDarkMode(true)
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.SHRINK)
                            .show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("sessionId", sessionID)
                        startActivity(intent)
                    } else {
                        AestheticDialog.Builder(this, DialogStyle.EMOTION, DialogType.ERROR)
                            .setTitle("Error")
                            .setMessage("User name or password is not correct!")
                            .setDarkMode(true)
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.SHRINK)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            })
                            .show()
                    }
                }
            } else {
                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.WARNING)
                    .setTitle("Warning")
                    .setMessage("Please enter username/password!")
                    .setDarkMode(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .setOnClickListener(object : OnDialogClickListener {
                        override fun onClick(dialog: AestheticDialog.Builder) {
                            dialog.dismiss()
                        }
                    })
                    .show()
            }
        }
        btnSignInGoogle.setOnClickListener {
            signInGoogle()
        }

        btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    //Sign in with Google
    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account?.idToken)
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        if (idToken == null) {
            Toast.makeText(this, "ID token is null", Toast.LENGTH_SHORT).show()
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await()
                val user = auth.currentUser
                if(user != null) {
                    loginViewModel.createGuestSession {result ->
                        if (result != null) {
                            if (result.success) {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("guestSessionId", result.guestSessionId)
                                startActivity(intent)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}