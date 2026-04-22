package com.example.hotelreservation

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelreservation.api.RetrofitClient
import com.example.hotelreservation.model.ApiResponse
import retrofit2.*

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnResetPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnResetPassword = findViewById(R.id.btnResetPassword)

        val email = intent.getStringExtra("email") ?: ""
        val otp = intent.getStringExtra("otp") ?: ""

        btnResetPassword.setOnClickListener {
            val newPass = etNewPassword.text.toString()
            val confirmPass = etConfirmPassword.text.toString()

            if (newPass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val body = mapOf(
                "email" to email,
                "otp" to otp,
                "newPassword" to newPass
            )

            RetrofitClient.instance.resetPassword(body)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ResetPasswordActivity,
                                "Password reset successfully!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@ResetPasswordActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@ResetPasswordActivity,
                                "Failed to reset. Try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(this@ResetPasswordActivity,
                            "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}