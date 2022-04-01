package ie.wit.savvytutor.fragments

import DisplayPostAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*

private lateinit var dbRef: DatabaseReference
private lateinit var  postRecyclerView: RecyclerView
private lateinit var  postArrayList : ArrayList<PostModel>



class HomeFragment : Fragment() {

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
        getUserData()


        return root

    }

    private fun getUserData(){

        dbRef = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ParentPosts")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){


                    for(postSnapshot in snapshot.children){

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

