package ie.wit.savvytutor.helpers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.savvytutor.adapters.DisplayTutorPostAdapter
import ie.wit.savvytutor.models.PostModel
import ie.wit.savvytutor.models.TutorPostModel


class TutorSwipeToDelete(var adapter: DisplayTutorPostAdapter) :
    ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition
        adapter.deleteItem(pos)



        val dbRef =
            FirebaseDatabase.getInstance("https://savvytutor-ab3d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("TutorPosts")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(TutorPostModel::class.java)
                        val selectedId = post?.postId
                        if(selectedId.equals(adapter.postId)) {
                            postSnapshot.getRef().removeValue();
                            break
                            }
                        }
                    }

                }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}