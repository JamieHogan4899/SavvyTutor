package ie.wit.savvytutor.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.HomeFragment
import ie.wit.savvytutor.models.PostModel

class DisplayPostAdapter : RecyclerView.Adapter<DisplayPostAdapter.ViewHolder>() {

    private val title = arrayOf("this is a test", "Test 2")

    private val subject = arrayOf("Maths", "English")

    private val location = arrayOf("Wexford", "Waterford")

    private val level = arrayOf("Junior Cert", "leaving Cert")

    private val description = arrayOf("Looking for a Maths Tutor in Kilmore Quay, must be available on weekends",
        "Looking for a Enlgish Tutor in Waterford, must be available on Thursday")





    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var subject: TextView
        var location: TextView
        var level: TextView
        var description: TextView


        init {
            title = itemView.findViewById(R.id.displayTitle)
            subject = itemView.findViewById(R.id.displaySubject)
            location = itemView.findViewById(R.id.displayLocation)
            level = itemView.findViewById(R.id.displayLevel)
            description = itemView.findViewById(R.id.displayDescription)


            itemView.setOnClickListener {

                val context = itemView.context
                val intent = Intent(context, HomeFragment::class.java).apply {

                    putExtra("TITLE", title.text)
                    putExtra("SUBJECT", subject.text)
                    putExtra("LOCATION", location.text)
                    putExtra("LEVEL", level.text)
                    putExtra("DESCRIPTION", description.text)



                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.displaypost_view, viewGroup, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.title.text = title[i]
        viewHolder.subject.text = subject[i]
        viewHolder.location.text = location[i]
        viewHolder.level.text = level[i]
        viewHolder.description.text = description[i]

    }

    override fun getItemCount(): Int {
        return title.size
    }


    }
