import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.adapters.DisplayTutorPostAdapter
import ie.wit.savvytutor.fragments.*
import ie.wit.savvytutor.fragments.userEmail
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.TutorPostModel


private lateinit var dbRef: DatabaseReference
private lateinit var tutorPostRecyclerView: RecyclerView
private lateinit var tutorPostArrayList: ArrayList<TutorPostModel>
private lateinit var mAuth: FirebaseAuth
var userEmail : String = ""
var userRole : String = ""

var userId: String = ""
var postTitle: String = ""
var postSubject: String = ""
var postLocation: String = ""
var postLevel: String = ""
var postAvailability: String = ""
var postDescription: String = ""
var postId: String = ""
var posterEmail: String = ""


class ParentHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }


    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.parent_home_screen, container, false)
        tutorPostRecyclerView = root.findViewById(ie.wit.savvytutor.R.id.displayTutorPosts)
        tutorPostRecyclerView.layoutManager = LinearLayoutManager(context)
        tutorPostRecyclerView.setHasFixedSize(true)

        tutorPostArrayList = arrayListOf<TutorPostModel>()
        getTutorPosts()


        userEmail = mAuth.currentUser?.email.toString()
        userRole = user.role

        context?.let { updateNavView(it) }
        return root

    }


    fun updateNavView(context: Context) {
        super.onAttach(context)
        val activity: Activity = context as MainActivity
        val navigationView = activity.findViewById(ie.wit.savvytutor.R.id.nav_view) as NavigationView
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.tutorHome).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.tutorCreate).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.tutorChat).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.tutorViewOwnPosts).isVisible = false
        navigationView.menu.findItem(ie.wit.savvytutor.R.id.tutorAbout).isVisible = false

        val navigationHeader = activity.findViewById(ie.wit.savvytutor.R.id.nav_view) as NavigationView
        val txtProfileName = navigationHeader.getHeaderView(0).findViewById<View>(ie.wit.savvytutor.R.id.DisplayName) as TextView
        txtProfileName.setText(userEmail)

    }



    private fun getTutorPosts() {
        dbRef =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "TutorPosts"
                )

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {

                        val tutorPost = postSnapshot.getValue(TutorPostModel::class.java)
                        tutorPostArrayList.add(tutorPost!!)
                    }

                    tutorPostRecyclerView.adapter = DisplayTutorPostAdapter(tutorPostArrayList, ::handlePostData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun getProfile(){


    }


    private fun handlePostData(data:TutorPostModel){

        userId = data.uid.toString()
        postTitle = data.title
        postSubject = data.subject
        postLocation = data.location
        postLevel = data.level
        postAvailability = data.availability
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
        bundle.putString("availability", postAvailability)
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

