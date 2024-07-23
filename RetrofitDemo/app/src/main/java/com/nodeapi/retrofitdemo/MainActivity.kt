package com.nodeapi.retrofitdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var buttonget: Button
    private lateinit var buttonpost: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text_view_result)
        buttonget = findViewById(R.id.get)
        buttonpost = findViewById(R.id.post)

        buttonpost.setOnClickListener {
            val apiLogin = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val jsonObject = JSONObject()
            jsonObject.put("username", "nodeapi")
            jsonObject.put("password", "123456")
            val jsonObjectString = jsonObject.toString()
            val requestBody =
                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val loginData = apiLogin.userLogin(requestBody)
            loginData.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    if (response != null) {
                        Log.d("response", response.raw().toString())
                    }
                    if (response != null) {
                        if (response.isSuccessful) {
                            textView.text = """
                            Response: ${response.body()}
                        """.trimIndent()
                        }
                    }
                }

                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    // Handle the failure here
                    if (t != null) {
                        textView.text = "Error: ${t.message}"
                    } else {
                        textView.text = "Unknown error occurred"
                    }
                }
            })
        }

        buttonget.setOnClickListener {
            val apiLogin = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val call: Call<User> = apiLogin.getUser();
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    if (!response?.isSuccessful!!) {
                        textView.text = "Code: ${response.code()}"
                        return
                    }
                    textView.text = """
                            Response: ${response.body()}
                        """.trimIndent()
                }

                override fun onFailure(call: Call<User>?, t: Throwable?) {

                }
            })
        }

    }
}