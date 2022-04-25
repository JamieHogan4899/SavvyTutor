package ie.wit.savvytutor.fragments

import DisplayPostAdapter
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.models.PostModel
import kotlin.concurrent.timerTask


private lateinit var dbRef: DatabaseReference
private lateinit var  postRecyclerView: RecyclerView
private lateinit var  postArrayList : ArrayList<PostModel>
private lateinit var mAuth: FirebaseAuth
var userEmail : String = ""

var userId: String = ""
var postTitle: String = ""
var postSubject: String = ""
var postLocation: String = ""
var postLevel: String = ""
var postDescription: String = ""
var postId: String = ""
var posterEmail: String = ""


class TutorHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.home_fragment, container, false)
        postRecyclerView = root.findViewById(ie.wit.savvytutor.R.id.displayPosts)
        postRecyclerView.layoutManager = LinearLayoutManager(context)
        postRecyclerView.setHasFixedSize(true)

        postArrayList = arrayListOf<PostModel>()
        getParentPosts()
        userEmail= mAuth.currentUser?.email.toString()
        println(userEmail)
        context?.let { updateNavView(it) }


        return root

    }

    fun updateNavView(context: Context) {
        super.onAttach(context)
        val activity: Activity = context as MainActivity
        val navigationView = activity.findViewById(ie.wit.savvytutor.R.id.nav_view) as NavigationView
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.home).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.createAPost).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.chat).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.aboutSavvyTutor).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.parentsViewOwnPosts).isVisible = false

        val navigationHeader = activity.findViewById(ie.wit.savvytutor.R.id.nav_view) as NavigationView
        val txtProfileName =
            navigationHeader.getHeaderView(0).findViewById<View>(ie.wit.savvytutor.R.id.DisplayName) as TextView
        txtProfileName.setText(userEmail)
    }

    private fun getParentPosts(){

        dbRef = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
            "ParentPosts"
        )

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                    for (postSnapshot in snapshot.children) {

                        val post = postSnapshot.getValue(PostModel::class.java)
                        postArrayList.add(post!!)
                    }

                    postRecyclerView.adapter = DisplayPostAdapter(postArrayList, ::handlePostData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun handlePostData(data : PostModel){

        userId = data.uid.toString()
        postTitle = data.title
        postSubject = data.subject
        postLocation = data.location
        postLevel = data.level
        postDescription = data.description
        postId = data.postId
        posterEmail = data.email

        println("This is the view Fragment: " + postSubject + "" + postDescription + "" + postId)

        val bundle = Bundle()
        bundle.putString("userId", userId)
        bundle.putString("Title", postTitle)
        bundle.putString("subject", postSubject)
        bundle.putString("location", postLocation)
        bundle.putString("level", postLevel)
        bundle.putString("description", postDescription)
        bundle.putString("postId", postId)
        bundle.putString("posterEmail", posterEmail)


        val optionsFrag = CommentFragment()
        optionsFrag.setArguments(bundle)
        (context as MainActivity).getSupportFragmentManager().beginTransaction()
            .replace(ie.wit.savvytutor.R.id.fragment_container, optionsFrag, "OptionsFragment")
            .addToBackStack(null)
            .commit()



    }


}

