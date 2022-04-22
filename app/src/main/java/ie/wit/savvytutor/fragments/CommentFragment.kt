package ie.wit.savvytutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

var RuserId: String = ""
var RpostTitle: String = ""
var RpostSubject: String = ""
var RpostLocation: String = ""
var RpostLevel: String = ""
var RpostDescription: String = ""
var RpostId: String = ""
var RposterEmail: String = ""

class CommentFragment : Fragment() {
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(ie.wit.savvytutor.R.layout.comment_fragment, container, false)



        val bundle = this.arguments
        //println(bundle)

        RuserId = bundle?.getString("userId").toString()
        RpostTitle =  bundle?.getString("Title").toString()
        RpostSubject =  bundle?.getString("subject").toString()
        RpostLocation =  bundle?.getString("location").toString()
        RpostLevel =  bundle?.getString("level").toString()
        RpostDescription =  bundle?.getString("description").toString()
        RpostId =  bundle?.getString("postId").toString()
        RposterEmail =  bundle?.getString("posterEmail").toString()

        return root
    }


}