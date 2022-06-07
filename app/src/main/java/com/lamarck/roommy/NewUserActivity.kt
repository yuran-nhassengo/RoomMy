package com.lamarck.roommy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lamarck.roommy.database.AppDatabase
import com.lamarck.roommy.database.dao.UserDao
import com.lamarck.roommy.database.model.User
import com.lamarck.roommy.databinding.ActivityNewUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewUserBinding
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.database = AppDatabase.getInstance(this)

        this.userDao = this.database.userDao()
    }

    override fun onStart() {
        super.onStart()

        this.binding.btnSave.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val result =saveUser(
                    binding.edFirst.text.toString(),
                    binding.edLast.text.toString(),
                )

                withContext(Dispatchers.Main){
                    Toast.makeText(this@NewUserActivity,
                    if (result)"User Saved" else "Error trying to save user",Toast.LENGTH_LONG)
                        .show()

                    if (result)
                        finish()
                }
            }

        }
    }

    private  suspend  fun saveUser(firstName:String,lastName:String): Boolean{

        if (firstName.isBlank() || firstName.isEmpty())
            return false

        if (lastName.isBlank() || lastName.isEmpty())
            return false

        this.userDao.insert(User(firstName,lastName))

        return true

    }

}