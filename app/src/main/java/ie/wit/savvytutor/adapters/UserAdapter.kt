package ie.wit.savvytutor.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.activity.MainActivity
import ie.wit.savvytutor.fragments.ViewChatFragment
import ie.wit.savvytutor.models.UserModel

class UserAdapter(private val userList: ArrayList<UserModel>, val context: Context, ) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(itemView)
    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username: TextView = itemView.findViewById(R.id.userNameView)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.username.text = currentItem.email
        holder.itemView.setOnClickListener {
            val optionsFrag = ViewChatFragment()
            (context as MainActivity).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, optionsFrag, "OptionsFragment").addToBackStack(null)
                .commit()
        }


    }

    override fun getItemCount(): Int {
        return userList.size

    }

}
