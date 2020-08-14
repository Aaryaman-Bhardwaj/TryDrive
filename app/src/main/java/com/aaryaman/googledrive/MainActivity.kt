package com.aaryaman.googledrive

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.services.drive.DriveScopes
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


var authoo: String = ""
var selectedFile: Uri? = null
var acnt : GoogleSignInAccount? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val intent = Intent(Intent.ACTION_VIEW)
//        http%3A//127.0.0.1%3A9004&
//        intent.data = Uri.parse("https://accounts.google.com/o/oauth2/v2/auth" + "?client_id=" + CLIENT_ID + "&response_type=" + "code" + "&redirect_uri=" + "http://localhost:8080" + "&scope=" + "https://www.googleapis.com/auth/drive")
//        startActivity(intent)

        choose.setOnClickListener {
            val intent = Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }

        sign_in_button.setOnClickListener {
            signIn()
            textView.text= acnt?.email
        }


        textView.setOnClickListener {
            textView.text= acnt?.grantedScopes.toString()+ "\nhyhyhy\n\n" + acnt?.isExpired.toString()
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            callRetrofit()
        }

    }

    private fun callRetrofit() {

        val file = File(selectedFile.toString())

//        val fbody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)

//        val descpart = RequestBody.create(MultipartBody.FORM, "Description fromthe requestbody create method")

        val filePrt = RequestBody.create(MediaType.parse("image/*"), file)

        val MP2 = MultipartBody.Part.createFormData("Photo", "Tasveer", filePrt)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceholderApi = retrofit.create(Jinterface::class.java)
        val mycall = jsonPlaceholderApi.uploadFile(authoo, MP2)

        try {
            mycall.enqueue(object : Callback<RequestBody?> {

                override fun onResponse(call: Call<RequestBody?>, response: Response<RequestBody?>) {
                    Toast.makeText(this@MainActivity, "Donne", Toast.LENGTH_SHORT).show()
                    textView.text = response.body().toString()
                    Log.e("ressponse", response.body().toString())
                }

                override fun onFailure(call: Call<RequestBody?>, t: Throwable) {
                    Log.e("dikkat", t.message.toString())
                }



            })
        }
        catch (e: Exception){
            Log.e("logE", e.message)
        }



    }

    private fun signIn() {
        val googleSignInClient = buildGoogleSignInClient()
        startActivityForResult(googleSignInClient.signInIntent, REQUEST_CODE_SIGN_IN)
    }

    private fun buildGoogleSignInClient(): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(DriveScopes.DRIVE_FILE), Scope(DriveScopes.DRIVE))
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, signInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        else if (requestCode == 111 ){
            selectedFile = data?.data //The uri with the location of the file
            textView.text=selectedFile.toString()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            acnt=account
            authoo=account?.idToken.toString()
            Toast.makeText(this, authoo, Toast.LENGTH_SHORT).show()
            val accountt = GoogleSignIn.getLastSignedInAccount(this)
            Toast.makeText(this, accountt?.email +" uuhuh", Toast.LENGTH_SHORT).show()
//
        }
        catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("logW", "${e.message}=" )
//            ("updateUI(null)")
        }
    }


    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Toast.makeText(this, account?.email +" uuhuh", Toast.LENGTH_SHORT).show()
//        updateUI(account)
    }

    companion object {
        const val REQUEST_CODE_SIGN_IN = 0
        const val CLIENT_ID="1028780865622-97p79dh3lppp6c4jnd11e3njrq95dh8g.apps.googleusercontent.com"
//        const val LOCAL_FILE_NAME = "file.txt"
//        const val REMOTE_FILE_NAME = "test-text"
    }





}