import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.post
import ie.wit.savvytutor.helpers.readImageFromPath
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.UserModel

class DisplayPostAdapter(private val postList: ArrayList<PostModel>) : RecyclerView.Adapter<DisplayPostAdapter.PostViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.displaypost_view,parent,false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       val currentItem = postList[position]

        holder.title.text = currentItem.title
        holder.subject.text = currentItem.subject
        holder.location.text = currentItem.location
        holder.level.text = currentItem.level
        holder.description.text = currentItem.description


    }

    override fun getItemCount(): Int {
       return postList.size
    }

    class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.displayTitle)
        val subject : TextView = itemView.findViewById(R.id.displaySubject)
        val location :TextView = itemView.findViewById(R.id.displayLocation)
        val level : TextView = itemView.findViewById(R.id.displayLevel)
        val description : TextView = itemView.findViewById(R.id.displayDescription)
        var profilepic: ImageView = itemView.findViewById(R.id.displayProfilePic)




    }



}