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
import ie.wit.savvytutor.adapters.UserData
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.UserModel
import java.io.File
import kotlin.reflect.KFunction0


class DisplayPostAdapter(private val postList: ArrayList<PostModel>, val handler: (PostModel) -> Unit) :
    RecyclerView.Adapter<DisplayPostAdapter.PostViewHolder>() {

    public var postId: String = ""
    private lateinit var mAuth: FirebaseAuth


    private fun getPost(uid:String, title:String, subject:String, location:String, level:String, description:String, postId:String, email:String) {
        // call the handler function with your data (you can write handler.invoke() if you prefer)
        handler(PostModel(uid, title, subject, location, level, description, postId, email))
    }

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


        val dbRef = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
        mAuth = FirebaseAuth.getInstance()

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
                                        .into(holder.displayProfilePic);
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
            val description = currentItem.description
            val postId = currentItem.postId
            val email = currentItem.email

            if (uid != null) {
                getPost(uid, title, subject, location, level, description, postId, email)
            }

        }

    }


    override fun getItemCount(): Int {
        return postList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.displayTitle)
        val subject: TextView = itemView.findViewById(R.id.displaySubject)
        val location: TextView = itemView.findViewById(R.id.displayLocation)
        val level: TextView = itemView.findViewById(R.id.displayLevel)
        val description: TextView = itemView.findViewById(R.id.displayDescription)
        var displayProfilePic: ImageView = itemView.findViewById(R.id.displayProfilePic)
        val username: TextView = itemView.findViewById(R.id.displayParentName)


    }

    fun test(position: Int) {

        val currentItem = postList[position]
        postId = currentItem.postId
        // println("post id from adapter = " + postId)
    }


    fun deleteItem(pos: Int) {
        //send this postid to the handler
        test(pos)
        postList.removeAt(pos)
        notifyItemRemoved(pos)
    }

//    fun getProfilePicture(){
//        val dbRef =
//            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
//
//        mAuth = FirebaseAuth.getInstance()
//
//        dbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    for (userSnapshot in snapshot.children) {
//                        val users = userSnapshot.getValue(UserModel::class.java)
//                        //val userProfilePic = users?.profilepic
//                        //println(userProfilePic)
//                        val uid = users?.uid
//
//
//                        if (uid.equals(mAuth.currentUser?.uid)){
//                            if (users != null) {
//                                profilepic = users.profilepic
//                            }
//                           profilepic = user.profilepic
//                        }
//
//                    }
//                }
//
//        println("Profile Picture: " + profilepic)
//
//    }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//    }
}