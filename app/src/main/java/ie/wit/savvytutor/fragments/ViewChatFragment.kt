package ie.wit.savvytutor.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ie.wit.savvytutor.R
import ie.wit.savvytutor.adapters.MessageAdapter
import ie.wit.savvytutor.models.MessageModel
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.chat_screen_fragment.view.*


private lateinit var messageRecyclerView: RecyclerView
private lateinit var messageBox: EditText
private lateinit var sendBtn: ImageView
private lateinit var messageArrayList: ArrayList<MessageModel>
private lateinit var dbRef: DatabaseReference
private lateinit var mAuth: FirebaseAuth
var reciverRoom: String? = null
var senderRoom: String? = null
var reciveruid: String? = null
var senderuid: String? = FirebaseAuth.getInstance().currentUser?.uid


var email:String = ""
var phone:String = ""
var uid:String = ""

class ViewChatFragment : Fragment() {
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(R.layout.chat_screen_fragment, container, false)
        dbRef =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference

        mAuth = FirebaseAuth.getInstance()

        val bundle = this.arguments

        println(bundle)

        if (bundle != null) {

            println(bundle.getString("email"))
            email = bundle.getString("email").toString()
            phone = bundle.getString("phone").toString()
            reciveruid = bundle.getString("uid").toString()
            //println(email +" "+ phone+ " " + reciveruid +" From View Chat Fragment")
        }

        senderuid = mAuth.currentUser?.uid

        println("Sender uid " + senderuid + " Recvier uid" + reciveruid)
        senderRoom = reciveruid + senderuid
        reciverRoom = senderuid + reciveruid



        messageRecyclerView = root.findViewById(R.id.messageRecyclerView)
        messageBox = root.findViewById(R.id.messageBox)

        messageRecyclerView.layoutManager = LinearLayoutManager(context)
        messageRecyclerView.setHasFixedSize(true)
        messageArrayList = arrayListOf<MessageModel>()
        (messageRecyclerView.layoutManager as LinearLayoutManager).setStackFromEnd(true)



        (activity as AppCompatActivity?)!!.supportActionBar?.title = email


        dbRef.child("Chat").child(senderRoom!!).child("messages").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageArrayList.clear()
                for (postSnapshot in snapshot.children) {
                    val message = postSnapshot.getValue(MessageModel::class.java)
                    messageArrayList.add(message!!)
                }
                messageRecyclerView.adapter?.notifyDataSetChanged()
                messageRecyclerView.adapter = context?.let { MessageAdapter(messageArrayList) }

            }

            override fun onCancelled(error: DatabaseError) {
            }


        })

        sendBtnListener(root)
        callBtnListener(root)
        return root
    }


    fun sendBtnListener(layout: View) {
        sendBtn = layout.findViewById(R.id.sendBtn)

        sendBtn.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = MessageModel(message, senderuid)

            dbRef.child("Chat").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("Chat").child(reciverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

            messageBox.setText("")


        }

    }



    fun callBtnListener(layout: View) {

        val callBtn = layout.findViewById<ImageView>(R.id.callBtn)

        callBtn.setOnClickListener() {

            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);


        }
    }


}



