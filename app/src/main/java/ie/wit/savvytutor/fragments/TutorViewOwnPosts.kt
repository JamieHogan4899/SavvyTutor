package ie.wit.savvytutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.adapters.DisplayTutorPostAdapter
import ie.wit.savvytutor.helpers.TutorSwipeToDelete
import ie.wit.savvytutor.models.TutorPostModel
import postAvailability

private lateinit var tutorPostRecyclerView: RecyclerView
private lateinit var tutorPostArrayList: ArrayList<TutorPostModel>
private lateinit var dbRef: DatabaseReference
private lateinit var mAuth: FirebaseAuth





class TutorViewOwnPosts : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        //println(mAuth.currentUser?.email)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.tutor_view_own_posts, container, false)
        tutorPostRecyclerView = root.findViewById(ie.wit.savvytutor.R.id.tutorOwnPosts)
        tutorPostRecyclerView.layoutManager = LinearLayoutManager(context)
        tutorPostRecyclerView.setHasFixedSize(true)

        tutorPostArrayList = arrayListOf<TutorPostModel>()
        getUsersPosts()

        (activity as AppCompatActivity?)!!.supportActionBar?.title = "SavvyTutor"
        return root
    }


    private fun getUsersPosts() {
        dbRef =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "TutorPosts"
                )

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    tutorPostArrayList.clear()

                    for (postSnapshot in snapshot.children) {
                        val tutorPost = postSnapshot.getValue(TutorPostModel::class.java)

                        if (tutorPost != null) {
                                if (tutorPost != null) {
                                    if (mAuth.currentUser?.uid.equals(tutorPost.uid))
                                    if (tutorPost != null) {
                                        tutorPostArrayList.add(tutorPost!!)
                                    }
                                }
                        }
                    }

                    tutorPostRecyclerView.adapter = DisplayTutorPostAdapter(tutorPostArrayList, ::handlePostData)

                    var itemTouchHelper = ItemTouchHelper(TutorSwipeToDelete(tutorPostRecyclerView.adapter as DisplayTutorPostAdapter))
                    itemTouchHelper.attachToRecyclerView(tutorPostRecyclerView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
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