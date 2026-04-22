package com.example.hotelreservation

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelreservation.api.RetrofitClient
import com.example.hotelreservation.model.ApiResponse
import retrofit2.*

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var etOtp: EditText
    private lateinit var btnVerifyOtp: Button
    private lateinit var tvEmailHint: TextView
    private lateinit var tvResendOtp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        etOtp = findViewById(R.id.etOtp)
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp)
        tvEmailHint = findViewById(R.id.tvEmailHint)
        tvResendOtp = findViewById(R.id.tvResendOtp)

        // ✅ FIX — plain val inside onCreate, not lateinit class variable
        val email = intent.getStringExtra("email") ?: ""
        tvEmailHint.text = "OTP sent to $email"

        btnVerifyOtp.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            if (otp.length != 6) {
                Toast.makeText(this, "Enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val body = mapOf("email" to email, "otp" to otp)

            RetrofitClient.instance.verifyOtp(body)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            val intent = Intent(this@VerifyOtpActivity, ResetPasswordActivity::class.java)
                            intent.putExtra("email", email)
                            intent.putExtra("otp", otp)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@VerifyOtpActivity,
                                "Invalid or expired OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(this@VerifyOtpActivity,
                            "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        tvResendOtp.setOnClickListener {
            val body = mapOf("email" to email)
            RetrofitClient.instance.forgotPassword(body)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        Toast.makeText(this@VerifyOtpActivity, "OTP resent!", Toast.LENGTH_SHORT).show()
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {}
                })
        }
    }
}