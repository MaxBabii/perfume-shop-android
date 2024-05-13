package com.maksym.perfumeshop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderActivity : AppCompatActivity() {
    private lateinit var userDataPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val enterName: EditText = findViewById(R.id.nameEnterText)
        val enterSurname: EditText = findViewById(R.id.surnameEnterText)
        val mailName: EditText = findViewById(R.id.mailEnterText)
        val phoneName: EditText = findViewById(R.id.phoneEnterText)
        val cityEnter: EditText = findViewById(R.id.cityEnterText)
        val addressEnter: EditText = findViewById(R.id.adressEnterText)
        val checkInStore: ImageButton = findViewById(R.id.check_in_store)
        val checkInReceipt: ImageButton = findViewById(R.id.check_in_receipt)
        val buyBtn: ImageButton = findViewById(R.id.buyBtn)
        val blackBg: ImageView = findViewById(R.id.black_bg)
        val completedImg: ImageView = findViewById(R.id.completed_img)
        val completedText: TextView = findViewById(R.id.completed_text)

        userDataPreferences = getSharedPreferences("userData", MODE_PRIVATE)

        val intent = intent


        val nameArray = intent.getStringArrayListExtra("nameArray")
        val countArray = intent.getStringArrayListExtra("countArray")
        val priceArray = intent.getStringArrayListExtra("priceArray")
        for(i in nameArray!!.indices){
            Log.d("Tag", "${nameArray.elementAt(i)}")
        }
        for(i in countArray!!.indices){
            Log.d("Tag", "${countArray.elementAt(i)}")
        }
        for(i in priceArray!!.indices){
            Log.d("Tag", "${priceArray.elementAt(i)}")
        }
        checkInStore.setOnClickListener {
            checkInStore.setImageResource(R.drawable.checkbox_selected)
            checkInReceipt.setImageResource(R.drawable.checkbox_empty)

            cityEnter.isVisible = false
            addressEnter.isVisible = false
        }
        checkInReceipt.setOnClickListener {
            checkInStore.setImageResource(R.drawable.checkbox_empty)
            checkInReceipt.setImageResource(R.drawable.checkbox_selected)

            cityEnter.isVisible = true
            addressEnter.isVisible = true
        }
        buyBtn.setOnClickListener {
            val name = enterName.text.toString().trim()
            val surname = enterSurname.text.toString().trim()
            val email = mailName.text.toString().trim()
            val phone = phoneName.text.toString().trim()
            val city = cityEnter.text.toString().trim()
            val address = addressEnter.text.toString().trim()
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields (*)", Toast.LENGTH_SHORT).show()
            } else if (cityEnter.isVisible && city.isEmpty()) {
                Toast.makeText(this, "Please fill in the city field", Toast.LENGTH_SHORT).show()
            } else if (addressEnter.isVisible && address.isEmpty()) {
                Toast.makeText(this, "Please fill in the address field", Toast.LENGTH_SHORT).show()
            } else {
                blackBg.isVisible = true
                completedImg.isVisible = true
                completedText.isVisible = true
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1500)
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


                    val userDataSet = userDataPreferences.getStringSet("userData", setOf())!!.toMutableSet()

                    for(i in nameArray!!.indices){
                        val value = nameArray[i] + ":" + countArray[i] + ":" + priceArray[i] + ":" +
                                name + ":" + surname + ":" + email + ":" + phone + ":" + currentDate
                        userDataSet!!.add(value)
                    }
                    val editor = userDataPreferences.edit()
                    editor.putStringSet("userData", userDataSet.toSet())
                    editor.apply()
                    val intent = Intent(this@OrderActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }

            }
        }

        hideUi()
    }
    private fun hideUi() {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LOW_PROFILE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsController = ViewCompat.getWindowInsetsController(window.decorView)
            insetsController?.let {
                it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                it.hide(WindowInsetsCompat.Type.systemBars())
            }
        } else {
            val decorView = window.decorView
            decorView.systemUiVisibility = uiOptions
        }
    }
}