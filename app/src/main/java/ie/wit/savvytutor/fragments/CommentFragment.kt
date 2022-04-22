package ie.wit.savvytutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import ie.wit.savvytutor.R
import ie.wit.savvytutor.adapters.DisplayCommentAdapter
import ie.wit.savvytutor.adapters.DisplayTutorPostAdapter
import ie.wit.savvytutor.adapters.MessageAdapter
import ie.wit.savvytutor.models.CommentModel
import ie.wit.savvytutor.models.MessageModel
import ie.wit.savvytutor.models.TutorPostModel
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.comment_fragment.*
import kotlinx.android.synthetic.main.tutor_create_post.*

var RuserId: String = ""
var RpostTitle: String = ""
var RpostSubject: String = ""
var RpostLocation: String = ""
var RpostLevel: String = ""
var RpostDescription: String = ""
var RpostId: String = ""
var RposterEmail: String = ""
var posterProfilePicture = ""
var commentRoom: String? = null
private lateinit var dbRef: DatabaseReference
private lateinit var mAuth: FirebaseAuth
private lateinit var sendComment: ImageView
private lateinit var CommentBox: EditText
private lateinit var commentRecyclerView: RecyclerView
private lateinit var commentArrayList: ArrayList<CommentModel>




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

        sendComment = root.findViewById(R.id.commentBtn)
        CommentBox = root.findViewById(R.id.commentBox)


        getProfilePicture(root)
        commentBtnListener(root)

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

        commentRoom = RpostId + RuserId

            root.findViewById<TextView>(R.id.commentTitle).setText(RpostTitle)
        root.findViewById<TextView>(R.id.commentSubject).setText(RpostSubject)
        root.findViewById<TextView>(R.id.commentLocation).setText(RpostLocation)
        root.findViewById<TextView>(R.id.commentLevel).setText(RpostLevel)
        root.findViewById<TextView>(R.id.commentDescription).setText(RpostDescription)
        root.findViewById<TextView>(R.id.commentPosterId).setText(RposterEmail)

        commentRecyclerView = root.findViewById(ie.wit.savvytutor.R.id.commentSectionRecylerView)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentRecyclerView.setHasFixedSize(true)

        commentArrayList = arrayListOf<CommentModel>()
        getComments()

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

    fun getComments(){
        dbRef.child("Comments").child(commentRoom!!).child("comments").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                commentArrayList.clear()
                for (postSnapshot in snapshot.children) {
                    val comment = postSnapshot.getValue(CommentModel::class.java)
                    println("Comments: " + comment)
                    commentArrayList.add(comment!!)
                }
                commentRecyclerView.adapter?.notifyDataSetChanged()
                commentRecyclerView.adapter = context?.let { DisplayCommentAdapter(commentArrayList) }
                println("This: " + commentArrayList )
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }



    fun commentBtnListener(layout: View){
        sendComment.setOnClickListener {

            val comment = CommentBox.text.toString()
            val commentObject = CommentModel(mAuth.currentUser?.uid, RpostId, mAuth.currentUser?.email.toString(), comment)
            //write comment to database
            dbRef.child("Comments").child(commentRoom!!).child("comments").push().setValue(commentObject)

        }
        CommentBox.setText("")
        }

}

