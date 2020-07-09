package ru.l4gunner4l.vk.posts_list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.*
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.model.Post

class PostsAdapter(
    val ctx: Context,
    var posts: List<Post>,
    var clickListener: PostClickListener
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_post, parent, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun updatePosts(posts: List<Post>){
        this.posts = posts
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val avatarIV: ImageView = itemView.findViewById(R.id.item_post_avatar)
        private val nameTV: TextView = itemView.findViewById(R.id.item_post_name)
        private val dateTV: TextView = itemView.findViewById(R.id.item_post_date)
        private val textTV: TextView = itemView.findViewById(R.id.item_post_text)
        private val imageIV: ImageView = itemView.findViewById(R.id.item_post_image)
        private val likeBtn: ImageView = itemView.findViewById(R.id.item_post_btn_like)
        private val likesCountTV: TextView = itemView.findViewById(R.id.item_post_tv_likes_count)
        private val commentsCountTV: TextView = itemView.findViewById(R.id.item_post_tv_comments_count)
        private val repostsCountTV: TextView = itemView.findViewById(R.id.item_post_tv_reposts_count)

        init {
            itemView.setOnClickListener(this)
            likeBtn.setOnClickListener {
                clickListener.onLikeClick(adapterPosition)
            }
        }

        override fun onClick(v: View) {
            clickListener.onPostClick(adapterPosition, v)
        }


        fun bind(post: Post) {
            Picasso.get()
                .load(post.avatarUrl)
                .into(avatarIV)
            nameTV.text = post.userName
            dateTV.text = post.getFormattedDate()
            textTV.text = post.postText
            Picasso.get()
                .load(post.postImage)
                .into(imageIV)

            if (post.isUserLike) {
                Log.i("M_MAIN", "adapter - likeBtn.setColorFilter(color_tint_like_active)")
                likeBtn.setColorFilter(
                    ContextCompat.getColor(ctx, R.color.color_tint_like_active),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            else {
                Log.i("M_MAIN", "adapter - likeBtn.setColorFilter(color_tint_passive)")
                likeBtn.setColorFilter(
                    ContextCompat.getColor(ctx, R.color.color_tint_passive),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }

            likesCountTV.text = post.likesCount.toString()
            commentsCountTV.text = post.commentsCount.toString()
            repostsCountTV.text = post.sharesCount.toString()

        }
    }

    interface PostClickListener {
        fun onPostClick(position: Int, v: View?)
        fun onLikeClick(position: Int)
    }


}