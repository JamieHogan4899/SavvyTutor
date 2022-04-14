package ie.wit.savvytutor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.TutorChatFragment
import ie.wit.savvytutor.models.UserModel

class UserAdapter(val userList: ArrayList<UserModel>) : RecyclerView.Adapter<UserAdapter.userViewHolder>() {

    class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.userNameView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tutor_chat_fragment, parent, false)
        return userViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userName.text = currentUser.email
    }

    override fun getItemCount(): Int {
       return userList.size
    }
}