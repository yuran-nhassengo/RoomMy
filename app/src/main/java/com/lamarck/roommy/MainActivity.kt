package com.lamarck.roommy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lamarck.roommy.database.AppDatabase
import com.lamarck.roommy.database.dao.UserDao
import com.lamarck.roommy.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.database = AppDatabase.getInstance(this)

        this.userDao = this.database.userDao()


    }

    override fun onStart() {
        super.onStart()
        loadTotalUsers()

        this.binding.btnAdd.setOnClickListener {
            openNewUserActivity()
        }
    }

    private fun openNewUserActivity() {
         startActivity(Intent(this,NewUserActivity::class.java))
    }

    private fun loadTotalUsers(){

        CoroutineScope(Dispatchers.IO).launch {

            val total = userDao.getTotalItems()

            withContext(Dispatchers.Main){

                binding.textView.text="Total de usuarios: $total"
            }


        }




    }
}