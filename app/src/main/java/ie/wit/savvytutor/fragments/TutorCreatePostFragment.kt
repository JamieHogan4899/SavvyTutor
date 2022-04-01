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
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.TutorPostModel


var tutorPosts = TutorPostModel()


class TutorCreatePostFragment : Fragment() {
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
            if(tutorPosts.title.isEmpty() || tutorPosts.subject.isEmpty() || tutorPosts.location.isEmpty()||
                tutorPosts.level.isEmpty() || tutorPosts.availability.isEmpty()|| tutorPosts.description.isEmpty())

            tutorPosts.title = title.text.toString()
            tutorPosts.subject = subject.selectedItem.toString()
            tutorPosts.location = location.text.toString()
            tutorPosts.level = level.selectedItem.toString()
            tutorPosts.availability = availability.text.toString()
            tutorPosts.description = description.text.toString()

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

    private fun writeNewTutorPost(tutorPostModel: TutorPostModel) {

        val uid = tutorPosts.uid
        val key = database.child("tutorPosts").push().key
        if (key == null) {
            Log.w(ContentValues.TAG, "Couldn't get push key for posts")
            return
        }

        tutorPosts.uid = key
        val postValues = tutorPosts.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/TutorPosts/$key" to postValues,
            )
        database.updateChildren(childUpdates)
    }


}