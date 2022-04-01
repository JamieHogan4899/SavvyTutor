package ie.wit.savvytutor.fragments

import DisplayPostAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import ie.wit.savvytutor.R
import ie.wit.savvytutor.activity.drawer
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
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        postRecyclerView = root.findViewById(R.id.displayPosts)
        postRecyclerView.layoutManager = LinearLayoutManager(context)
        postRecyclerView.setHasFixedSize(true)

        postArrayList = arrayListOf<PostModel>()
        getParentPosts()
        //changeToolbar(root)


        return root

    }


//    private fun changeToolbar(layout: View){
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }


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

