package ru.l4gunner4l.vk.task2.main

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.main.posts_list.PostListFragment

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "MainFragment"
        private const val KEY_LOGIN = "KEY_LOGIN"
        fun newInstance(login: String): MainFragment {
            return MainFragment().apply {
                arguments = bundleOf(KEY_LOGIN to login)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val login = arguments!!.getString(KEY_LOGIN) ?: "Email is null"
        Toast.makeText(context, "Hello! Your login: $login", Toast.LENGTH_LONG).show()

        if (savedInstanceState == null) {
            fragmentManager?.let {
                it.beginTransaction()
                    .add(
                        R.id.main_fragment_container,
                        PostListFragment.newInstance(),
                        PostListFragment.TAG
                    )
                    .commit()
            }
        }

    }

}
