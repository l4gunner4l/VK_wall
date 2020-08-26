package ru.l4gunner4l.vk.task2.main.posts_list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_post_list.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.main.single_post.DetailsFragment
import ru.l4gunner4l.vk.task2.model.Post
import java.io.IOException


class PostListFragment : Fragment(R.layout.fragment_post_list) {

    companion object {
        const val TAG = "PostListFragment"
        private const val fileName = "vk_posts.json"

        @JvmStatic
        fun newInstance() =
            PostListFragment()
    }

    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun loadData() {
        val json = getJsonFromFile() ?: ""
        posts = Gson().fromJson(json, object : TypeToken<List<Post>>() {}.type)
    }

    private fun initView() {
        if (posts.isEmpty()) {
            rv_posts.visibility = View.GONE
            tv_list_empty.visibility = View.VISIBLE
        } else {
            rv_posts.visibility = View.VISIBLE
            tv_list_empty.visibility = View.GONE
        }
        adapter = PostsAdapter(
            activity as Context, posts,
            object :
                PostsAdapter.PostClickListener {
                override fun onPostClick(position: Int, v: View?) {
                    fragmentManager?.let {
                        it.beginTransaction()
                            .add(
                                R.id.main_fragment_container,
                                DetailsFragment.newInstance(position, posts[position])
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                }

                override fun onLikeClick(position: Int) {
                    posts[position].let { post ->
                        if (post.isUserLike) {
                            post.isUserLike = false
                            post.likesCount--
                        } else {
                            post.isUserLike = true
                            post.likesCount++
                        }
                        adapter.updateItem(position, post)
                    }

                }

            })
        rv_posts.layoutManager = LinearLayoutManager(activity as Context)
        rv_posts.adapter = adapter
    }

    private fun getJsonFromFile(): String? {
        val jsonString: String
        try {
            jsonString =
                (activity as Context).assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}

/*class PostListActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_POST = "PostListActivity_EXTRA_POST"
        const val EXTRA_POSITION = "PostListActivity_EXTRA_POSITION"
        private const val fileName = "vk_posts.json"
        private const val REQUEST_SINGLE_POST = 123
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
}
*/
