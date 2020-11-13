package com.bagasbest.woah.messages

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import androidx.recyclerview.widget.DividerItemDecoration
import com.bagasbest.woah.activity.MainActivity
import com.bagasbest.woah.R
import com.bagasbest.woah.messages.NewMessageActivity.Companion.USER_KEY
import com.bagasbest.woah.models.ChatMessage
import com.bagasbest.woah.models.LatestMessageRow
import com.bagasbest.woah.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.view.*

class LatestMessagesActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        recyclerView_latestMessage.adapter = adapter
        recyclerView_latestMessage.addItemDecoration(DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL))

        title = "Latest message"

//        val toolbars = Toolbar(findViewById(R.id.toolbar))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
//        val drw = DrawerLayout(findViewById(R.id.drawer_layout))

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.bringToFront()

        val headerView = nav_view.getHeaderView(0)
        headerView.changePictureIv.setOnClickListener {
            changeProfilePic();
        }

        nav_view.setNavigationItemSelectedListener(this)



        //set item click listener on your item
        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(this, ChatLogActivity::class.java)

            //pass user key from chatPartnerUser
            val row = item as LatestMessageRow
            intent.putExtra(USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        listenForLatestMessage()
        fetchCurrentUser()
        verifyUserLogin()

    }

    private fun changeProfilePic () {
        val profile:String = "image"
        val profilePick = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick a picture with")
        builder.setItems(profilePick) { _, which ->
            when (which) {
                0 -> Toast.makeText(this, "Camera Choose", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(this, "Gallery Choose", Toast.LENGTH_SHORT).show()
            }

        }
        builder.show()
    }

    val latestMessageMap = HashMap<String, ChatMessage>()

    //refresh recyclerView adapter
    private fun refreshRecyclerView() {
        adapter.clear()
        latestMessageMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessage() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest_message/$fromId")
        ref.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return
                latestMessageMap[snapshot.key!!] = chatMessage
                refreshRecyclerView()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return
                latestMessageMap[snapshot.key!!] = chatMessage
                refreshRecyclerView()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    val adapter = GroupAdapter<ViewHolder>()

    private fun fetchCurrentUser () {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)
                Log.d("LatestMessageActivity", "Current user :  ${ currentUser?.profileImageUrl} ")

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    private fun verifyUserLogin () {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_new_message -> {
                startActivity(Intent(this, NewMessageActivity::class.java))
            }

            R.id.menu_change_name -> Toast.makeText(this, "Change name", Toast.LENGTH_SHORT).show()
            R.id.menu_change_email -> Toast.makeText(this, "Change email", Toast.LENGTH_SHORT).show()
            R.id.change_number -> Toast.makeText(this, "Change number", Toast.LENGTH_SHORT).show()

            R.id.menu_sign_out -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirm logout")
                builder.setIcon(R.drawable.logout_24)
                builder.setMessage("Are you sure want to logout?")
                builder.setCancelable(false)

                builder.setPositiveButton("YES") { _, _ ->
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish();
                }
                builder.setNegativeButton("NO") { _,_ ->

                }

                val alertDialog = builder.create()
                alertDialog.show()
            }

        }

        return true
    }




}