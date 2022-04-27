package ie.wit.savvytutor.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks


class AboutFragment : Fragment() {
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
       val root = inflater.inflate(ie.wit.savvytutor.R.layout.about_fragment, container, false)

        linkToGithub(root)

        (activity as AppCompatActivity?)!!.supportActionBar?.title = "SavvyTutor"
        return root
    }

    fun linkToGithub(layout: View){


        val githubLink = Link("SavvyTutor GitHub Repo")
            .setTextColor(Color.BLUE)
            .setTextColorOfHighlightedLink(Color.CYAN)
            .setHighlightAlpha(.4f)
            .setUnderlined(true)
            .setBold(false)
            .setOnClickListener {
                val url = "https://github.com/JamieHogan4899/SavvyTutor"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

        layout.findViewById<TextView>(ie.wit.savvytutor.R.id.githubLink).applyLinks(githubLink)




    }
}