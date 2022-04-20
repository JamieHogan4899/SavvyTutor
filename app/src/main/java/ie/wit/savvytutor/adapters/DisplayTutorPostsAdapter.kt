package ie.wit.savvytutor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.TutorPostModel
import ie.wit.savvytutor.models.UserModel

class DisplayTutorPostAdapter(private val tutorPostList: ArrayList<TutorPostModel>) : RecyclerView.Adapter<DisplayTutorPostAdapter.TutorPostViewHolder>() {

    public var postId:String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.display_tutor_posts, parent, false)
        return TutorPostViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: TutorPostViewHolder, position: Int) {
        val currentItem = tutorPostList[position]

        holder.title.text = currentItem.title
        holder.subject.text = currentItem.subject
        holder.location.text = currentItem.location
        holder.level.text = currentItem.level
        holder.availability.text = currentItem.availability
        holder.description.text = currentItem.description


        val dbRef = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Users")


        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val users = userSnapshot.getValue(UserModel::class.java)
                        val individualDb = currentItem.uid?.let { dbRef.child(it) }
                        //println("indavidual users database " + individualDb)
                        var user: String = ""
                        if (individualDb != null) {
                            individualDb.child("profilepic").get().addOnSuccessListener {
                                if (it.exists()) {
                                    var link = it.value
                                    //println("THIS IS LINK    " + link)
                                    Picasso.get().load(link.toString())
                                        .into(holder.profilepic);
                                }
                            }

                        }


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })





    }




    override fun getItemCount(): Int {
        return tutorPostList.size
    }


    class TutorPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.displayTutorTitle)
        val subject: TextView = itemView.findViewById(R.id.displayTutorSubject)
        val location: TextView = itemView.findViewById(R.id.displayTutorLocation)
        val level: TextView = itemView.findViewById(R.id.displayTutorLevel)
        val availability: TextView = itemView.findViewById(R.id.displayTutorAva)
        val description: TextView = itemView.findViewById(R.id.displayTutorDescription)
        var profilepic: ImageView = itemView.findViewById(R.id.displayTutorProfilePic)
    }



    fun getPostId(position: Int){

        val currentItem = tutorPostList[position]
        postId = currentItem.postId

        // println("post id from adapter = " + postId)
    }


    fun deleteItem(pos:Int){
        //send this postid to the handler and remove from array list
        getPostId(pos)
        tutorPostList.removeAt(pos)
        notifyItemRemoved(pos)
    }


}
