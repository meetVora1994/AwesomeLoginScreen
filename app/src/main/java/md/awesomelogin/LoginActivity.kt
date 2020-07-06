package md.awesomelogin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import md.awesomelogin.R


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })
        email_sign_in_button.setOnClickListener { attemptLogin() }
        tv_register.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

    }

    private fun attemptLogin() {

        email.error = null
        password.error = null

        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var drawable: Drawable? = this.getDrawable(R.drawable.ic_error)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

        if (TextUtils.isEmpty(emailStr)) {
            email.setError(getString(R.string.error_field_required), drawable)
            email.requestFocus()
            return
        } else if (TextUtils.isEmpty(passwordStr)) {
            password.setError(getString(R.string.error_field_required), drawable)
            password.requestFocus()
            return
        } else if (!isEmailValid(emailStr)) {
            email.setError(getString(R.string.error_invalid_email), drawable)
            email.requestFocus()
            return
        } else if (!isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password), drawable)
            password.requestFocus()
            return
        }

        callApi()
    }

    private fun callApi() {
        // Show loading
        showProgress(true)

        // Handle the redirection
        // startActivity(Intent(applicationContext, NextActivity::class.java))
        // finish()
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        val shortAnimTime =
            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 0 else 1).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_form.visibility = if (show) View.GONE else View.VISIBLE
                }
            })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
    }
}
