package ie.wit.savvytutor.fragments

import DisplayPostAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.savvytutor.helpers.SwipeToDelete
import ie.wit.savvytutor.models.PostModel

private lateinit var dbRef: DatabaseReference
private lateinit var  postRecyclerView: RecyclerView
private lateinit var  postArrayList : ArrayList<PostModel>
private lateinit var mAuth: FirebaseAuth




class ParentViewOwnPosts : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.parent_view_own_post, container, false)
        postRecyclerView = root.findViewById(ie.wit.savvytutor.R.id.parentOwnPosts)
        postRecyclerView.layoutManager = LinearLayoutManager(context)
        postRecyclerView.setHasFixedSize(true)
        postArrayList = arrayListOf<PostModel>()
        getUserPosts()
        println("here")

        return root

    }


    private fun getUserPosts(){

        dbRef = FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
            "ParentPosts"
        )

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {



                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(PostModel::class.java)

                        if (post != null) {
                            if (post != null) {
                                if (mAuth.currentUser?.uid.equals(post.uid))
                                    if (post != null) {

                                        postArrayList.add(post!!)
                                    }
                            }
                        }
                    }

                    postRecyclerView.adapter = DisplayPostAdapter(postArrayList)

                    var itemTouchHelper =ItemTouchHelper(SwipeToDelete(postRecyclerView.adapter as DisplayPostAdapter))
                    itemTouchHelper.attachToRecyclerView(postRecyclerView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}