package ie.wit.savvytutor.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.R.layout.createapost_fragment


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
        val addBtn = layout.findViewById<Button>(R.id.createapostbtn)
        val title = layout.findViewById<EditText>(R.id.postTitle)
        addBtn.setOnClickListener {

            post.title = title.text.toString()




        println(post.title)
            println("button pressed")
        }

    }



    }






