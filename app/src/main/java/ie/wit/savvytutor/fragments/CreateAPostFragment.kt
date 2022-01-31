package ie.wit.savvytutor.fragments


import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.R.layout.createapost_fragment
import android.widget.Toast


class CreateAPostFragment : Fragment() {

    var post = PostModel()

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
        //assign
        val addBtn = layout.findViewById<Button>(R.id.createapostbtn)
        val title = layout.findViewById<EditText>(R.id.postTitle)
        val subject =layout.findViewById<Spinner>(R.id.chooseSubject)
        val location = layout.findViewById<EditText>(R.id.chooseLocation)
        val level = layout.findViewById<Spinner>(R.id.chooseSubject)

        addBtn.setOnClickListener {

            post.title = title.text.toString()
            post.subject = subject.selectedItem.toString()
            post.location = location.text.toString()
            post.level = level.selectedItem.toString()

        println(post)
            Toast.makeText(getActivity(), "Post Created" , Toast.LENGTH_LONG).show();
        }

    }



    }






