package ru.l4gunner4l.vk.posts_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.l4gunner4l.vk.R
import ru.l4gunner4l.vk.model.Post
import java.text.SimpleDateFormat

class PostsAdapter(
    val ctx: Context,
    val posts: List<Post>,
    var clickListener: PostClickListener
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_post, parent, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val avatarIV: ImageView = itemView.findViewById(R.id.item_post_avatar)
        private val nameTV: TextView = itemView.findViewById(R.id.item_post_name)
        private val dateTV: TextView = itemView.findViewById(R.id.item_post_date)
        private val textTV: TextView = itemView.findViewById(R.id.item_post_text)
        private val imageIV: ImageView = itemView.findViewById(R.id.item_post_image)
        private val likeBtn: ImageButton = itemView.findViewById(R.id.item_post_btn_like)
        private val likesCountTV: TextView = itemView.findViewById(R.id.item_post_tv_likes_count)
        private val commentsCountTV: TextView = itemView.findViewById(R.id.item_post_tv_comments_count)
        private val repostsCountTV: TextView = itemView.findViewById(R.id.item_post_tv_reposts_count)

        fun bind(post: Post) {
            Glide.with(ctx)
                .load(post.avatarUrl)
                .into(avatarIV)
            nameTV.text = post.userName
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
            dateTV.text = dateFormatter.format(post.postDate)
            textTV.text = post.postText
            Glide.with(ctx)
                .load(post.postImage)
                .into(imageIV)
            likesCountTV.text = post.likesCount.toString()
            commentsCountTV.text = post.commentsCount.toString()
            repostsCountTV.text = post.sharesCount.toString()

        }

        override fun onClick(v: View) {
            clickListener.onPostClick(adapterPosition, v)
        }


        init {
            itemView.setOnClickListener(this)
            likeBtn.setOnClickListener {
                clickListener.onLikeClick(adapterPosition)
            }
        }
    }

    interface PostClickListener {
        fun onPostClick(position: Int, v: View?)
        fun onLikeClick(position: Int)
    }


}