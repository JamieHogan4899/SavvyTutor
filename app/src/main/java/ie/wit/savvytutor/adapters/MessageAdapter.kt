package ie.wit.savvytutor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import ie.wit.savvytutor.R
import ie.wit.savvytutor.models.MessageModel
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.send_layout.view.*

class MessageAdapter(private val messgageList: ArrayList<MessageModel>, ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val Item_Recivce =1
    val Item_Sent = 2


    class sentViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val sentMessage = itemview.findViewById<TextView>(R.id.txtSendMessage)
    }


    class reciveViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val reciveMessage = itemview.findViewById<TextView>(R.id.txtReciveMessage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.recive_messsage, parent, false)
            return reciveViewHolder(itemView)
        } else {

            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.send_layout, parent, false)
            return sentViewHolder(itemView)

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messgageList[position]
       // println(currentMessage.senderId)


        if (holder.javaClass == sentViewHolder::class.java) {

            val viewHolder = holder as sentViewHolder
            holder.sentMessage.text = currentMessage.message

        } else {

            //recive view holder
            val viewHolder = holder as reciveViewHolder
            holder.reciveMessage.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messgageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messgageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return Item_Sent
        } else {
            return Item_Recivce
        }
    }




}