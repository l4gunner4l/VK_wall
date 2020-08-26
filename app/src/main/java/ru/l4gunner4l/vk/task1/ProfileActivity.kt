package ru.l4gunner4l.vk.task1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.model.SignInInput


class ProfileActivity : AppCompatActivity() {

    companion object {
        private const val KEY_INPUT = "KEY_INPUT"
        fun newIntent(context: Context, input: SignInInput): Intent {
            return Intent(context, ProfileActivity::class.java).apply {
                putExtra(KEY_INPUT, input)
            }
        }
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        loadData()
        updateView()
    }

    private fun loadData() {
        val email = intent.getParcelableExtra<SignInInput>(KEY_INPUT)!!.email
        user = User(
            email,
            "Nikola",
            "Mikhaylov",
            "https://sun1-90.userapi.com/c855532/v855532752/84d74/VDCzh0c1zI0.jpg",
            "Не изменю статус, пока Арсенал не выйдет в финал ЛЧ (01.06.2015)",
            true
        )
    }

    private fun updateView() {
        with(user) {
            Picasso
                .get()
                .load(avatarLink)
                .into(profile_avatar)
            profile_toolbar.title = email
            profile_full_name.text = getFullName()
            profile_status.text = status
            profile_online.text =
                if (isOnline) getString(R.string.online) else getString(R.string.offline)
        }
    }
}
