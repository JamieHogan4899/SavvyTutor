package ie.wit.savvytutor.fragments


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.savvytutor.R
import ie.wit.savvytutor.R.layout.createapost_fragment
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.UserModel


var post = PostModel()
private lateinit var mAuth: FirebaseAuth



// Write a message to the database
val database =
    FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference

class CreateAPostFragment : Fragment() {
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

        val root = inflater.inflate(createapost_fragment, container, false)
        setAddButtonListener(root)
        return root


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateAPostFragment().apply {
                arguments = Bundle().apply {}
            }
    }


    fun setAddButtonListener(layout: View) {


        //assign the user input fields
        val addBtn = layout.findViewById<Button>(R.id.createapostbtn)
        val title = layout.findViewById<EditText>(R.id.postTitle)
        val subject = layout.findViewById<Spinner>(R.id.chooseSubject)
        val location = layout.findViewById<EditText>(R.id.chooseLocation)
        val level = layout.findViewById<Spinner>(R.id.chooseLevel)
        val description = layout.findViewById<EditText>(R.id.description)

        //take in user input and store in the model
        addBtn.setOnClickListener {

            post.title = title.text.toString()
            post.subject = subject.selectedItem.toString()
            post.location = location.text.toString()
            post.level = level.selectedItem.toString()
            post.description = description.text.toString()

            if (post.title.isEmpty() || post.subject.isEmpty() || post.location.isEmpty() || post.level.isEmpty()) {

                Toast.makeText(getActivity(), "Please enter all the details", Toast.LENGTH_SHORT)
                    .show()
            }

            else {

                if(post.description.isEmpty()){

                    layout.findViewById<EditText>(R.id.description).setText("")
                }

                writeNewPost(
                    PostModel(
                        title = post.title,
                        subject = post.subject,
                        location = post.location,
                        level = post.level,
                        description = post.description
                    )
                )

                addBtn.onEditorAction(EditorInfo.IME_ACTION_DONE)
                println(post)
                Toast.makeText(getActivity(), "Post Created", Toast.LENGTH_LONG).show();

                val fragment = ParentViewOwnPosts()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.fragment_container,
                        fragment
                    )?.commit()

            }
        }

    }


    private fun writeNewPost(postModel: PostModel) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        val key = database.child("posts").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        post.uid = mAuth.currentUser?.uid
        post.postId = key
        var userEmail = mAuth.currentUser?.email.toString()
        val substring: String = userEmail.substring(0, userEmail.indexOf("@"))
        post.email = substring

        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/ParentPosts/$key" to postValues,

            )
        database.updateChildren(childUpdates)
    }



}