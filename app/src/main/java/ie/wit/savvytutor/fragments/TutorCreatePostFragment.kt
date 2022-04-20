package ie.wit.savvytutor.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.TutorPostModel


var tutorPosts = TutorPostModel()
private lateinit var mAuth: FirebaseAuth


class TutorCreatePostFragment : Fragment() {
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

        val root = inflater.inflate(R.layout.tutor_create_post, container, false)
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
        val addBtn = layout.findViewById<Button>(R.id.tutorcreateapostbtn)
        val title = layout.findViewById<EditText>(R.id.tutorPostTitle)
        val subject = layout.findViewById<Spinner>(R.id.tutorchooseSubject)
        val location = layout.findViewById<EditText>(R.id.tutorChooseLocation)
        val level = layout.findViewById<Spinner>(R.id.tutorChooseLevel)
        val availability = layout.findViewById<EditText>(R.id.tutorChooseAvailability)
        val description = layout.findViewById<EditText>(R.id.tutorDescription)


        addBtn.setOnClickListener {

            tutorPosts.title = title.text.toString()
            tutorPosts.subject = subject.selectedItem.toString()
            tutorPosts.location = location.text.toString()
            tutorPosts.level = level.selectedItem.toString()
            tutorPosts.availability = availability.text.toString()
            tutorPosts.description = description.text.toString()

            if (tutorPosts.title.isEmpty() || tutorPosts.subject.isEmpty() || tutorPosts.location.isEmpty() ||
                tutorPosts.level.isEmpty() || tutorPosts.availability.isEmpty() || tutorPosts.description.isEmpty()
            ) {
                Toast.makeText(getActivity(), "Please enter all the details", Toast.LENGTH_SHORT)
                    .show()
            } else {

                writeNewTutorPost(
                    TutorPostModel(
                        title = tutorPosts.title,
                        subject = tutorPosts.subject,
                        location = tutorPosts.location,
                        level = tutorPosts.level,
                        availability = tutorPosts.availability,
                        description = tutorPosts.description

                    )
                )

                println(tutorPosts)
                Toast.makeText(getActivity(), "Post Created", Toast.LENGTH_LONG).show();
            }
        }
    }

    private fun writeNewTutorPost(tutorPostModel: TutorPostModel) {

        val key = database.child("tutorPosts").push().key
        if (key == null) {
            Log.w(ContentValues.TAG, "Couldn't get push key for posts")
            return
        }

        tutorPosts.uid = mAuth.currentUser?.uid
        tutorPosts.postId = key

        var userEmail = mAuth.currentUser?.email.toString()
        val substring: String = userEmail.substring(0, userEmail.indexOf("@"))
        tutorPosts.email = substring
        val postValues = tutorPosts.toMap()




        val childUpdates = hashMapOf<String, Any>(
            "/TutorPosts/$key" to postValues,
            )
        database.updateChildren(childUpdates)
    }


}