package ie.wit.savvytutor.fragments

import DisplayPostAdapter
import android.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.models.PostModel
import kotlinx.android.synthetic.main.activity_main.*


private lateinit var dbRef: DatabaseReference
private lateinit var  postRecyclerView: RecyclerView
private lateinit var  postArrayList : ArrayList<PostModel>



class TutorHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        return root

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

                    postRecyclerView.adapter = DisplayPostAdapter(postArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}

