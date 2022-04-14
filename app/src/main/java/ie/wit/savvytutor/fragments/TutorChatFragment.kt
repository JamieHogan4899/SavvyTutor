package ie.wit.savvytutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import ie.wit.savvytutor.adapters.UserAdapter
import ie.wit.savvytutor.models.UserModel

class TutorChatFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        return inflater.inflate(ie.wit.savvytutor.R.layout.tutor_chat_fragment, container, false)


    }
}