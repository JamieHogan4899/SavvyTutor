package ie.wit.savvytutor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.CommentModel

class DisplayCommentAdapter(private val commentList: ArrayList<CommentModel>) : RecyclerView.Adapter<DisplayCommentAdapter.CommentViewHolder>(){


    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val comment: TextView = itemView.findViewById(R.id.comment)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayCommentAdapter.CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.comment_layout,
            parent,
            false
        )
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DisplayCommentAdapter.CommentViewHolder, position: Int) {

        val currentItem = commentList[position]

        holder.comment.text = currentItem.comment

    }





    override fun getItemCount(): Int {
        return commentList.size
    }


}