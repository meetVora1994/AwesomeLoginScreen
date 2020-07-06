package md.awesomelogin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)

        back_button.setOnClickListener { finish() }
        register_button.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {

        f_name.error = null
        l_name.error = null
        email.error = null
        password.error = null

        val fNameStr = f_name.text.toString()
        val lNameStr = l_name.text.toString()
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        val drawable: Drawable? = this.getDrawable(R.drawable.ic_error)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

        if (TextUtils.isEmpty(fNameStr)) {
            f_name.setError(getString(R.string.error_field_required), drawable)
            f_name.requestFocus()
            return
        } else if (TextUtils.isEmpty(lNameStr)) {
            l_name.setError(getString(R.string.error_field_required), drawable)
            l_name.requestFocus()
            return
        } else if (TextUtils.isEmpty(emailStr)) {
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

        // Proceed further the registration as your need
        showProgress(true)
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
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

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
