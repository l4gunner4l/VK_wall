package ru.l4gunner4l.vk.posts_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_post_list.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.model.Post
import java.io.IOException

class PostListActivity : AppCompatActivity() {

    companion object {
        private const val fileName = "vk_posts.json"
    }

    private lateinit var posts: List<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        loadData()
        initView()
    }

    private fun initView() {
        if (posts.isEmpty()) {
            rv_posts.visibility = View.GONE
            tv_list_empty.visibility = View.VISIBLE
        } else {
            rv_posts.visibility = View.VISIBLE
            tv_list_empty.visibility = View.GONE
        }
    }

    private fun loadData() {
        val gson = Gson()

        val json = getJsonFromFile() ?: ""
        Log.i("M_MAIN", "11111111")
        Log.i("M_MAIN", json.toString())
        val listPostType = object : TypeToken<List<Post>>() {}.type
        posts = gson.fromJson(json, listPostType)
        Log.i("M_MAIN", posts.toString())

    }

    private fun getJsonFromFile(): String? {
        val jsonString: String
        try {
            jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
