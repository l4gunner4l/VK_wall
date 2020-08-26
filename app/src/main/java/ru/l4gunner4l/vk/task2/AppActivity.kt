package ru.l4gunner4l.vk.task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.login.LoginFragment
import ru.l4gunner4l.vk.task2.main.MainFragment

class AppActivity : AppCompatActivity(), HostActivity {

    companion object {
        private const val KEY_FIRST_START = "KEY_FIRST_START"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isFirstStart = savedInstanceState?.getBoolean(KEY_FIRST_START) ?: true
        if (isFirstStart) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, LoginFragment.newInstance(), LoginFragment.TAG)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_FIRST_START, false)
    }

    override fun openMainFragment(login: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance(login), MainFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}
