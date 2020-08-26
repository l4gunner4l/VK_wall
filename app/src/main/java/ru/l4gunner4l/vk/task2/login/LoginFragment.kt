package ru.l4gunner4l.vk.task2.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.HostActivity
import ru.l4gunner4l.vk.task2.model.SignInInput

class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        const val TAG = "LoginFragment"
        private const val KEY_INPUT = "KEY_INPUT"
        fun newInstance() = LoginFragment()
    }


    private lateinit var input: SignInInput


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_INPUT, input)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        input = savedInstanceState?.getParcelable(KEY_INPUT) ?: SignInInput("", "")
        updateView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("M_MAIN", "onViewCreated ${hashCode()}")
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
        (activity as HostActivity).openMainFragment(input.email)
    }

    private fun updateView() {
        updateBtn(btn_sign_in)
        et_email.setText(input.email)
        et_password.setText(input.password)
    }

    private fun updateBtn(btn: Button) {
        if (input.isValid()) {
            btn.setBackgroundResource(R.color.colorPrimary)
            btn.isEnabled = true
        } else {
            btn.setBackgroundResource(R.color.color_primary_light)
            btn.isEnabled = false
        }
    }
}
