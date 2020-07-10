package ru.l4gunner4l.vk.single_post

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_post.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.model.Post
import ru.l4gunner4l.vk.posts_list.PostListActivity

class SinglePostActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_POST = "SinglePostActivity_EXTRA_POST"
        private const val EXTRA_POSITION = "SinglePostActivity_EXTRA_POSITION"

        fun start(ctx: Context, position:Int, post: Post): Intent {
            val intent = Intent(ctx, SinglePostActivity::class.java)
            intent.putExtra(EXTRA_POST, post)
            intent.putExtra(EXTRA_POSITION, position)
            return intent
        }
    }

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_post)
        post = intent.getParcelableExtra(EXTRA_POST)
            ?: throw NullPointerException("intent.getParcelableExtra(EXTRA_POST) is null")
        initView()
    }

    private fun initView() {
        initToolbar()

        Picasso.get()
            .load(post.avatarUrl)
            .into(post_avatar)
        post_name.text = post.userName
        post_date.text = post.getFormattedDate()

        if (post.postText != null) post_text.text = post.postText
        else post_text.visibility = GONE
        if (post.postImage != null) {
            Picasso.get()
                .load(post.postImage)
                .into(post_image)
        } else post_image.visibility = GONE

        post_tv_likes_count.text = post.likesCount.toString()
        post_tv_comments_count.text = post.commentsCount.toString()
        post_tv_reposts_count.text = post.sharesCount.toString()

        if (post.isUserLike) post_btn_like.setColorFilter(
            ContextCompat.getColor(this, R.color.color_tint_like_active),
            android.graphics.PorterDuff.Mode.SRC_IN
        ) else post_btn_like.setColorFilter(
            ContextCompat.getColor(this, R.color.color_tint_passive),
            android.graphics.PorterDuff.Mode.SRC_IN
        )

        post_btn_like.setOnClickListener {
            if (post.isUserLike) {
                post.isUserLike = false
                post.likesCount--
                post_btn_like.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_tint_passive),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            } else {
                post.isUserLike = true
                post.likesCount++
                post_btn_like.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_tint_like_active),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            post_tv_likes_count.text = post.likesCount.toString()
        }
    }

    override fun finish() {
        setResult(
            Activity.RESULT_OK,
            Intent()
                .putExtra(PostListActivity.EXTRA_POST, post)
                .putExtra(PostListActivity.EXTRA_POSITION, intent.getIntExtra(EXTRA_POSITION, 0))
        )
        super.finish()
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

}
