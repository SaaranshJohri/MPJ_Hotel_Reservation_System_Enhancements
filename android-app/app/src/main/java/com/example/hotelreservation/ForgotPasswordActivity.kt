package com.example.hotelreservation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelreservation.api.RetrofitClient
import com.example.hotelreservation.model.ApiResponse
import retrofit2.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnSendOtp: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etEmail = findViewById(R.id.etEmail)
        btnSendOtp = findViewById(R.id.btnSendOtp)
        progressBar = findViewById(R.id.progressBar)

        btnSendOtp.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnSendOtp.isEnabled = false

            val body = mapOf("email" to email)

            RetrofitClient.instance.forgotPassword(body)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        progressBar.visibility = View.GONE
                        btnSendOtp.isEnabled = true
                        if (response.isSuccessful) {
                            Toast.makeText(this@ForgotPasswordActivity,
                                "OTP sent to $email", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ForgotPasswordActivity, VerifyOtpActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@ForgotPasswordActivity,
                                "Email not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        btnSendOtp.isEnabled = true
                        Toast.makeText(this@ForgotPasswordActivity,
                            "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}