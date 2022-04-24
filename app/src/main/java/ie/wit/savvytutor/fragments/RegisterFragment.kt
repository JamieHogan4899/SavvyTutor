package ie.wit.savvytutor.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import ie.wit.savvytutor.R
import ie.wit.savvytutor.helpers.readImage
import ie.wit.savvytutor.helpers.showImagePicker
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.UserModel
import kotlinx.android.synthetic.main.register_user_fragment.*
import kotlinx.android.synthetic.main.register_user_fragment.view.*
import java.net.URI
import java.util.*


private lateinit var mAuth: FirebaseAuth
var user = UserModel()
val rtdb =
    FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").reference
val IMAGE_REQUEST = 1

var profilePicUrl: String = ""



class RegisterFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = Firebase.auth

    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        //inflate the fragment layout
        val root = inflater.inflate(R.layout.register_user_fragment, container, false)
        setRegisterButtonListener(root)
        selectProfilePicture(root)
        goToLogin(root)


        return root


    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }



    fun goToLogin(layout: View){
        val backToLogin = layout.findViewById<TextView>(ie.wit.savvytutor.R.id.goToLogin)

        backToLogin.setOnClickListener {

            val fragment = LoginFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(ie.wit.savvytutor.R.id.fragment_container, fragment)?.commit()

        }

    }

    fun selectProfilePicture(layout: View){
        var selectProfilePicBtn = layout.findViewById<Button>(R.id.addprofilepic)
        selectProfilePicBtn.setOnClickListener{

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 ||  requestCode == RESULT_OK  ||  data !=null){

            println("photo selected")

            selectedPhotoUri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedPhotoUri )
            val bitmapDrawable = BitmapDrawable(bitmap)



        }
    }

    private fun uploadImageToDb(){
        if(selectedPhotoUri ==null) return

        val filename = UUID.randomUUID().toString()
        val ssRef = FirebaseStorage.getInstance().getReference("/images/$filename")

        ssRef.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                println("uploading image to db")

                ssRef.downloadUrl.addOnSuccessListener {
                    val selectedPicUrl = it.toString()
                    profilePicUrl = selectedPicUrl


                }

            }

    }



    fun setRegisterButtonListener(layout: View) {
        val registerbtn = layout.findViewById<Button>(R.id.registerbtn)
        val email = layout.findViewById<EditText>(R.id.registerEmail)
        val password = layout.findViewById<EditText>(R.id.registerPassword)
        val role = layout.findViewById<Spinner>(R.id.chooseRole)
        val username = layout.findViewById<EditText>(R.id.registerUsername)
           val displayprofilepic = layout.findViewById<ImageView>(R.id.registershowprofilepic)

        registerbtn.setOnClickListener {

            user.email = email.text.toString()
            user.password = password.text.toString()
            user.role = role.selectedItem.toString()
            user.phone = username.text.toString()

            if (user.email.isEmpty() || user.password.isEmpty() || user.role.isEmpty() || user.role == "Choose Role" || user.phone.isEmpty() || user.profilepic.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all the details and add a profile picture", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mAuth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val Fuser: FirebaseUser? = mAuth.currentUser
                            Toast.makeText(getActivity(), "Account Created ", Toast.LENGTH_LONG)
                                .show()
                            println(Fuser)


                            layout.findViewById<EditText>(R.id.registerEmail).text.clear()
                            layout.findViewById<EditText>(R.id.registerPassword).text.clear()
                            layout.findViewById<Spinner>(R.id.chooseRole).setSelection(0)

                            uploadImageToDb()
                            writeNewUser(
                                UserModel(
                                    email = user.email,
                                    password = user.password,
                                    profilepic = profilePicUrl,
                                    role = user.role,
                                    phone = user.phone,

                                    )
                            )

                            val user = FirebaseAuth.getInstance().currentUser
                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "Email sent.")
                                    }
                                }


                            val fragment = LoginFragment()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, fragment)?.commit()


                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }

    }

    private fun writeNewUser(userModel: UserModel) {

        val key = mAuth.currentUser?.uid
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for users")
            return
        }

        user.uid = mAuth.currentUser?.uid
        user.profilepic = profilePicUrl
        val userValues = user.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/Users/$key" to userValues,

            )

        database.updateChildren(childUpdates)
    }


}










