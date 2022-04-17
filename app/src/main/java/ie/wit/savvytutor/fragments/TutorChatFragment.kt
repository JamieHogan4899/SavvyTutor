package ie.wit.savvytutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import ie.wit.savvytutor.R
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.adapters.UserAdapter
import ie.wit.savvytutor.adapters.UserData
import ie.wit.savvytutor.models.UserModel


class TutorChatFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    var email: String = ""
    var phone: String = ""
    var uid: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbRef =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users").ref
        mAuth = FirebaseAuth.getInstance()
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(R.layout.tutor_chat_fragment, container, false)
        userRecyclerView = root.findViewById(R.id.userListView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<UserModel>()
        getUserList()


        (activity as AppCompatActivity?)!!.supportActionBar?.title = "SavvyTutor"


        return root
    }


    private fun getUserList() {

        userArrayList.clear()

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(UserModel::class.java)
                    val email = currentUser?.email

                    if (currentUser != null) {
                        if (!mAuth.currentUser?.uid.equals(currentUser.uid))
                            if (email != null) {
                                userArrayList.add(currentUser!!)


                            }
                    }
                            userRecyclerView.adapter?.notifyDataSetChanged()
                            userRecyclerView.adapter =
                                context?.let {
                                    UserAdapter(
                                        userArrayList,
                                        it,
                                        ::handleUserData,
                                    )
                                }


                        }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun handleUserData(data: UserData) {
        email = data.email
        phone = data.phone
        uid = data.uid


        val bundle = Bundle()
        bundle.putString("email", email)
        bundle.putString("phone", phone)
        bundle.putString("uid", uid)


        val optionsFrag = ViewChatFragment()
        optionsFrag.setArguments(bundle);

        (context as MainActivity).getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, optionsFrag, "OptionsFragment")
            .addToBackStack(null)
            .commit()

    }
}