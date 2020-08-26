package ru.l4gunner4l.vk.task2.main.posts_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.task2.model.Post

class PostsAdapter(
    val ctx: Context,
    private var posts: MutableList<Post>,
    var clickListener: PostClickListener
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_post, parent, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun updateItem(position: Int, post: Post){
        posts[position] = post
        notifyItemChanged(position)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val avatarIV: ImageView = itemView.findViewById(R.id.post_avatar)
        private val nameTV: TextView = itemView.findViewById(R.id.post_name)
        private val dateTV: TextView = itemView.findViewById(R.id.post_date)
        private val textTV: TextView = itemView.findViewById(R.id.post_text)
        private val imageIV: ImageView = itemView.findViewById(R.id.post_image)
        private val likeBtn: ImageView = itemView.findViewById(R.id.post_btn_like)
        private val likesCountTV: TextView = itemView.findViewById(R.id.post_tv_likes_count)
        private val commentsCountTV: TextView = itemView.findViewById(R.id.post_tv_comments_count)
        private val repostsCountTV: TextView = itemView.findViewById(R.id.post_tv_reposts_count)

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
            if (post.postText == null) textTV.visibility = GONE
            else textTV.text = post.postText

            if (post.postImage == null) imageIV.visibility = GONE
            else Picasso.get()
                .load(post.postImage)
                .into(imageIV)

            if (post.isUserLike) {
                likeBtn.setColorFilter(
                    ContextCompat.getColor(ctx, R.color.color_tint_like_active),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            else {
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