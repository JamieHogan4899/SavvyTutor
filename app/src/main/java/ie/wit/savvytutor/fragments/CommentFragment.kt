package ie.wit.savvytutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.comment_fragment.*

var RuserId: String = ""
var RpostTitle: String = ""
var RpostSubject: String = ""
var RpostLocation: String = ""
var RpostLevel: String = ""
var RpostDescription: String = ""
var RpostId: String = ""
var RposterEmail: String = ""
var posterProfilePicture = ""
private lateinit var dbRef: DatabaseReference
private lateinit var mAuth: FirebaseAuth

class CommentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbRef = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference
        mAuth = FirebaseAuth.getInstance()
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.comment_fragment, container, false)
        getProfilePicture(root)



        val bundle = this.arguments
        //println(bundle)

        RuserId = bundle?.getString("userId").toString()
        RpostTitle =  bundle?.getString("Title").toString()
        RpostSubject =  bundle?.getString("subject").toString()
        RpostLocation =  bundle?.getString("location").toString()
        RpostLevel =  bundle?.getString("level").toString()
        RpostDescription =  bundle?.getString("description").toString()
        RpostId =  bundle?.getString("postId").toString()
        RposterEmail =  bundle?.getString("posterEmail").toString()

        root.findViewById<TextView>(R.id.commentTitle).setText(RpostTitle)
        root.findViewById<TextView>(R.id.commentSubject).setText(RpostSubject)
        root.findViewById<TextView>(R.id.commentLocation).setText(RpostLocation)
        root.findViewById<TextView>(R.id.commentLevel).setText(RpostLevel)
        root.findViewById<TextView>(R.id.commentDescription).setText(RpostDescription)
        root.findViewById<TextView>(R.id.commentPosterId).setText(RposterEmail)



        return root
    }

    fun getProfilePicture(layout:View) {
        //go to database and get the posters profile picture
        //go the database and get all the users
        dbRef.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val users = userSnapshot.getValue(UserModel::class.java)
                       // println(users)

                        //check does any uid match the posters id
                        if(RuserId.equals(users?.uid)){
                            if (users != null) {
                                //if they do get there profile picture url
                                posterProfilePicture = users.profilepic
                                val imageview = layout.findViewById<ImageView>(R.id.CommentPosterProfilePicutre)
                                //println("Users Picture: " + posterProfilePicture)
                                Picasso.get().load(posterProfilePicture.toString()).into(imageview);

                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }



}

