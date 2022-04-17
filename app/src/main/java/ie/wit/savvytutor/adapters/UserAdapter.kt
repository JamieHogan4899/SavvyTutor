package ie.wit.savvytutor.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.savvytutor.R
import ie.wit.savvytutor.fragments.ViewChatFragment
import ie.wit.savvytutor.fragments.user
import ie.wit.savvytutor.models.UserModel
import kotlin.reflect.KFunction0

data class UserData(val email: String, val phone: String, val uid: String)



class UserAdapter(private val userList: ArrayList<UserModel>, val context: Context, val handler: (UserData) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private fun getValues(email: String, phone: String, uid: String) {
        // call the handler function with your data (you can write handler.invoke() if you prefer)
        handler(UserData(email, phone, uid))
    }



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

           // println(getValues("jamiehogan4848@gmail.com", currentItem.phone))
            val email = currentItem.email
            val phone = currentItem.phone
            val uid = currentItem.uid
            if (uid != null) {
                getValues(email, phone, uid)
            }



        }


    }

    override fun getItemCount(): Int {
        return userList.size

    }




}