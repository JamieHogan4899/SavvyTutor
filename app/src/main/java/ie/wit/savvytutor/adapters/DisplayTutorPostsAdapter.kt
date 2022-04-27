package ie.wit.savvytutor.adapters

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.TutorPostModel
import ie.wit.savvytutor.models.UserModel

class DisplayTutorPostAdapter(private val tutorPostList: ArrayList<TutorPostModel>, val handler: (TutorPostModel) -> Unit) : RecyclerView.Adapter<DisplayTutorPostAdapter.TutorPostViewHolder>() {

    public var postId:String = ""
    private var context: Context? = null



    private fun getPost(uid:String, title:String, subject:String, location:String, level:String, availability:String, description:String, postId:String, email:String) {
        // call the handler function with your data (you can write handler.invoke() if you prefer)
        handler(TutorPostModel(uid, title, subject, location, level, availability, description, postId, email))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.display_tutor_posts, parent, false)
        context = parent.getContext();
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
        holder.username.text = currentItem.email


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



        holder.itemView.setOnClickListener{
            println("Item clicked, Item is :" + currentItem.title + " By: " + currentItem.email)
            val uid = currentItem.uid
            val title = currentItem.title
            val subject = currentItem.subject
            val location = currentItem.subject
            val level = currentItem.level
            val availability = currentItem.availability
            val description = currentItem.description
            val postId = currentItem.postId
            val email = currentItem.email

            if (uid != null) {
                getPost(uid, title, subject, location, level, availability, description, postId, email)
            }

        }


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
        val username: TextView = itemView.findViewById(R.id.displayTutorName)
    }



    fun getPostId(position: Int){

        val currentItem = tutorPostList[position]
        postId = currentItem.postId

        // println("post id from adapter = " + postId)
    }



    fun alertDialog(position: Int){
        val currentItem = tutorPostList[position]

        context?.let { MaterialAlertDialogBuilder(it) }
            ?.setTitle("Alert")
            ?.setMessage("Are you sure you want to delete this post")
            ?.setNegativeButton("no"){ dialogInterface: DialogInterface, i: Int ->
                tutorPostList.add(currentItem)
                notifyDataSetChanged()
                tutorPostList.remove(currentItem)

            }
            ?.setPositiveButton("yes"){ dialogInterface: DialogInterface, i: Int ->
                getPostId(position)
                tutorPostList.removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()

                val dbRef =
                    FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("TutorPosts")
                dbRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (postSnapshot in snapshot.children) {
                                val post = postSnapshot.getValue(PostModel::class.java)
                                val selectedId = post?.postId
                                if(selectedId.equals(currentItem.postId)) {
                                    postSnapshot.getRef().removeValue();
                                    break
                                }
                            }

                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            ?.show()
    }


}
