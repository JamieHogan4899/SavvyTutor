import android.graphics.Bitmap
import android.graphics.BitmapFactory.decodeFile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.user
import ie.wit.savvytutor.models.PostModel
import java.io.File


class DisplayPostAdapter(private val postList: ArrayList<PostModel>) : RecyclerView.Adapter<DisplayPostAdapter.PostViewHolder>(){

    public var postId:String = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.displaypost_view,
            parent,
            false
        )
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       val currentItem = postList[position]

        holder.title.text = currentItem.title
        holder.subject.text = currentItem.subject
        holder.location.text = currentItem.location
        holder.level.text = currentItem.level
        holder.description.text = currentItem.description
        holder.username.text = currentItem.email
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/savvytutor-ab3d2.appspot.com/o/images%2F31eeb0d0-82a3-4454-8194-1b741e426f3e?alt=media&token=519a1a0d-9b5b-437d-8892-ae404fae0fc7").into(holder.profilepic);

    }

    override fun getItemCount(): Int {
       return postList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.displayTitle)
        val subject : TextView = itemView.findViewById(R.id.displaySubject)
        val location :TextView = itemView.findViewById(R.id.displayLocation)
        val level : TextView = itemView.findViewById(R.id.displayLevel)
        val description : TextView = itemView.findViewById(R.id.displayDescription)
        var profilepic: ImageView = itemView.findViewById(R.id.displayProfilePic)
        val username: TextView = itemView.findViewById(R.id.displayParentName)

    }

    fun test(position: Int){

        val currentItem = postList[position]
        postId = currentItem.postId
       // println("post id from adapter = " + postId)
    }



    fun deleteItem(pos: Int){
        //send this postid to the handler
        test(pos)
        postList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun getProfilePicture(){


    }




}