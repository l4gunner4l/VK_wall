package ru.l4gunner4l.vk.task2.main.single_post

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details_post.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.model.Post

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment(R.layout.fragment_details_post) {

    companion object {
        const val TAG = "DetailsFragment"
        private const val EXTRA_POST = "EXTRA_POST"
        private const val EXTRA_POSITION = "EXTRA_POSITION"

        fun newInstance(position: Int, post: Post): DetailsFragment {
            return DetailsFragment().apply {
                arguments = bundleOf(
                    EXTRA_POST to post,
                    EXTRA_POSITION to position
                )
            }
        }
    }

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = arguments?.getParcelable(EXTRA_POST)
            ?: throw NullPointerException("intent.getParcelableExtra(EXTRA_POST) is null")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        else post_text.visibility = View.GONE
        if (post.postImage != null) Picasso.get().load(post.postImage).into(post_image)
        else post_image.visibility = View.GONE

        post_tv_likes_count.text = post.likesCount.toString()
        post_tv_comments_count.text = post.commentsCount.toString()
        post_tv_reposts_count.text = post.sharesCount.toString()

        if (post.isUserLike) post_btn_like.setColorFilter(
            ContextCompat.getColor(activity as Context, R.color.color_tint_like_active),
            PorterDuff.Mode.SRC_IN
        ) else post_btn_like.setColorFilter(
            ContextCompat.getColor(activity as Context, R.color.color_tint_passive),
            PorterDuff.Mode.SRC_IN
        )

        post_btn_like.setOnClickListener {
            clickLikeBtn()
        }
    }

    private fun clickLikeBtn() {
        if (post.isUserLike) {
            post.isUserLike = false
            post.likesCount--
            post_btn_like.setColorFilter(
                ContextCompat.getColor(activity as Context, R.color.color_tint_passive),
                PorterDuff.Mode.SRC_IN
            )
        } else {
            post.isUserLike = true
            post.likesCount++
            post_btn_like.setColorFilter(
                ContextCompat.getColor(activity as Context, R.color.color_tint_like_active),
                PorterDuff.Mode.SRC_IN
            )
        }
        post_tv_likes_count.text = post.likesCount.toString()
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}
