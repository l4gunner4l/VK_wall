package ru.l4gunner4l.vk.posts_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_post_list.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.model.Post
import ru.l4gunner4l.vk.single_post.SinglePostActivity
import java.io.IOException

class PostListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_POST = "PostListActivity_EXTRA_POST"
        const val EXTRA_POSITION = "PostListActivity_EXTRA_POSITION"
        private const val fileName = "vk_posts.json"
        private const val REQUEST_SINGLE_POST = 123
    }

    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        loadData()
        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SINGLE_POST && resultCode == Activity.RESULT_OK && data != null) {
            val newPost = data.getParcelableExtra<Post>(EXTRA_POST) ?: throw NullPointerException("data.getParcelableExtra<Post>(EXTRA_POST) is null")
            val position = data.getIntExtra(EXTRA_POSITION, -1)
            if (newPost != posts[position]){
                posts[position] = newPost
                adapter.updateItem(position, newPost)
            }
        }
    }

    private fun initView() {
        if (posts.isEmpty()) {
            rv_posts.visibility = View.GONE
            tv_list_empty.visibility = View.VISIBLE
        } else {
            rv_posts.visibility = View.VISIBLE
            tv_list_empty.visibility = View.GONE

        }
        adapter = PostsAdapter(this, posts, object : PostsAdapter.PostClickListener {
            override fun onPostClick(position: Int, v: View?) {
                startActivityForResult(
                    SinglePostActivity.start(
                        this@PostListActivity,
                        position,
                        posts[position]
                    ),
                    REQUEST_SINGLE_POST
                )
            }
            override fun onLikeClick(position: Int) {
                if (posts[position].isUserLike) {
                    posts[position].isUserLike = false
                    posts[position].likesCount--
                } else {
                    posts[position].isUserLike = true
                    posts[position].likesCount++
                }
                adapter.updateItem(position, posts[position])
            }

        })
        rv_posts.layoutManager = LinearLayoutManager(this)
        rv_posts.adapter = adapter
    }

    private fun loadData() {
        val gson = Gson()

        val json = getJsonFromFile() ?: ""
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

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
