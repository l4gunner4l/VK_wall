package ru.l4gunner4l.vk.posts_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_post_list.*
import kotlinx.android.synthetic.main.item_post.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.model.Post
import java.io.IOException

class PostListActivity : AppCompatActivity() {

    companion object {
        private const val fileName = "vk_posts.json"
    }

    private lateinit var posts: List<Post>
    private lateinit var adapter: PostsAdapter

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
        adapter = PostsAdapter(this, posts, object : PostsAdapter.PostClickListener{
            override fun onPostClick(position: Int, v: View?) {
                toast("Click on post #$position")
            }

            override fun onLikeClick(position: Int) {
                toast("Click on LIKE post #$position")

                if (posts[position].isUserLike){
                    posts[position].isUserLike = false
                    posts[position].likesCount--
                } else {
                    posts[position].isUserLike = true
                    posts[position].likesCount++
                }
                adapter.updatePosts(posts)
                adapter.notifyItemChanged(position)
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

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
