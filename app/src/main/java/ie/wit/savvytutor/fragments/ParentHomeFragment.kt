import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import ie.wit.savvytutor.adapters.DisplayTutorPostAdapter
import ie.wit.savvytutor.models.TutorPostModel


private lateinit var dbRef: DatabaseReference
private lateinit var tutorPostRecyclerView: RecyclerView
private lateinit var tutorPostArrayList: ArrayList<TutorPostModel>


class ParentHomeFragment : Fragment() {


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
        return root

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

                    tutorPostRecyclerView.adapter = DisplayTutorPostAdapter(tutorPostArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}

