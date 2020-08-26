package ru.l4gunner4l.vk.task1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_login.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.model.SignInInput

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val KEY_INPUT = "KEY_INPUT"
    }

    private lateinit var input: SignInInput

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_INPUT, input)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        input = savedInstanceState.getParcelable(KEY_INPUT) ?: SignInInput("", "")
        updateView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        input = savedInstanceState?.getParcelable(KEY_INPUT)
            ?: SignInInput("", "")
        updateView()

        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                input.email = p0.toString()
                updateBtn(btn_sign_in)
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                input.password = p0.toString()
                updateBtn(btn_sign_in)
            }
        })
        btn_sign_in.setOnClickListener { signInBtnClick() }
    }

    private fun signInBtnClick() {
        startActivity(ProfileActivity.newIntent(this, input))
        finish()
    }

    private fun updateView() {
        updateBtn(btn_sign_in)
        et_email.setText(input.email)
        et_password.setText(input.password)
    }

    private fun updateBtn(btn: Button) {
        if (input.isValid()) {
            btn.setBackgroundResource(R.drawable.bg_button_enabled)
            btn.isEnabled = true
        } else {
            btn.setBackgroundResource(R.drawable.bg_button_not_enabled)
            btn.isEnabled = false
        }
    }
}
