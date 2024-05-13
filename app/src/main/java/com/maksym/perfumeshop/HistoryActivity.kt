package com.maksym.perfumeshop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text

class HistoryActivity : AppCompatActivity() {
    private lateinit var frames: List<FrameLayout>
    private lateinit var name: List<TextView>
    private lateinit var surname: List<TextView>
    private lateinit var mail: List<TextView>
    private lateinit var phone: List<TextView>
    private lateinit var date: List<TextView>
    private lateinit var perfumeName: List<TextView>
    private lateinit var perfumeCount: List<TextView>
    private lateinit var perfumePrice: List<TextView>
    private lateinit var userDataPreferences: SharedPreferences
    private lateinit var enterFragrance: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val backBtn: ImageButton = findViewById(R.id.back_history_btn)
        enterFragrance = findViewById(R.id.enterFieldNumber)
        userDataPreferences = getSharedPreferences("userData", MODE_PRIVATE)
        init()
        val userData = userDataPreferences.getStringSet("userData", setOf())
        for (i in userData!!.indices){
            val value = userData.elementAt(i).split(":")
            frames[i].isVisible = true
            perfumeName[i].text = value[0]
            perfumeCount[i].text = value[1]
            perfumePrice[i].text = value[2]
            name[i].text = value[3]
            surname[i].text = value[4]
            mail[i].text = value[5]
            phone[i].text = value[6]
            date[i].text = value[7]
        }
        enterFragrance.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                for (i in userData.indices) {
                    val phone = phone[i].text
                    val layout = frames[i]
                    if (phone.contains(s.toString(), true)) layout.visibility = View.VISIBLE
                    else layout.visibility = View.GONE
                }
            }
        })
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        hideUi()
    }
    private fun init(){
        frames = listOf(
            findViewById(R.id.first_history_layout),
            findViewById(R.id.second_history_layout),
            findViewById(R.id.third_history_layout),
            findViewById(R.id.fourth_history_layout),
            findViewById(R.id.five_history_layout),
            findViewById(R.id.six_history_layout),
            findViewById(R.id.seven_history_layout),
            findViewById(R.id.eight_history_layout),
            findViewById(R.id.nine_history_layout),
            findViewById(R.id.ten_history_layout)
        )
        name = listOf(
            findViewById(R.id.name1),
            findViewById(R.id.name2),
            findViewById(R.id.name3),
            findViewById(R.id.name4),
            findViewById(R.id.name5),
            findViewById(R.id.name6),
            findViewById(R.id.name7),
            findViewById(R.id.name8),
            findViewById(R.id.name9),
            findViewById(R.id.name10)
        )
        surname = listOf(
            findViewById(R.id.surname1),
            findViewById(R.id.surname2),
            findViewById(R.id.surname3),
            findViewById(R.id.surname4),
            findViewById(R.id.surname5),
            findViewById(R.id.surname6),
            findViewById(R.id.surname7),
            findViewById(R.id.surname8),
            findViewById(R.id.surname9),
            findViewById(R.id.surname10)
        )
        mail = listOf(
            findViewById(R.id.mail1),
            findViewById(R.id.mail2),
            findViewById(R.id.mail3),
            findViewById(R.id.mail4),
            findViewById(R.id.mail5),
            findViewById(R.id.mail6),
            findViewById(R.id.mail7),
            findViewById(R.id.mail8),
            findViewById(R.id.mail9),
            findViewById(R.id.mail10)
        )
        phone = listOf(
            findViewById(R.id.phone1),
            findViewById(R.id.phone2),
            findViewById(R.id.phone3),
            findViewById(R.id.phone4),
            findViewById(R.id.phone5),
            findViewById(R.id.phone6),
            findViewById(R.id.phone7),
            findViewById(R.id.phone8),
            findViewById(R.id.phone9),
            findViewById(R.id.phone10)
        )
        date = listOf(
            findViewById(R.id.date1),
            findViewById(R.id.date2),
            findViewById(R.id.date3),
            findViewById(R.id.date4),
            findViewById(R.id.date5),
            findViewById(R.id.date6),
            findViewById(R.id.date7),
            findViewById(R.id.date8),
            findViewById(R.id.date9),
            findViewById(R.id.date10)
        )
        perfumeName = listOf(
            findViewById(R.id.perfume1),
            findViewById(R.id.perfume2),
            findViewById(R.id.perfume3),
            findViewById(R.id.perfume4),
            findViewById(R.id.perfume5),
            findViewById(R.id.perfume6),
            findViewById(R.id.perfume7),
            findViewById(R.id.perfume8),
            findViewById(R.id.perfume9),
            findViewById(R.id.perfume10)
        )
        perfumeCount = listOf(
            findViewById(R.id.quantity1),
            findViewById(R.id.quantity2),
            findViewById(R.id.quantity3),
            findViewById(R.id.quantity4),
            findViewById(R.id.quantity5),
            findViewById(R.id.quantity6),
            findViewById(R.id.quantity7),
            findViewById(R.id.quantity8),
            findViewById(R.id.quantity9),
            findViewById(R.id.quantity10)
        )
        perfumePrice = listOf(
            findViewById(R.id.price1),
            findViewById(R.id.price2),
            findViewById(R.id.price3),
            findViewById(R.id.price4),
            findViewById(R.id.price5),
            findViewById(R.id.price6),
            findViewById(R.id.price7),
            findViewById(R.id.price8),
            findViewById(R.id.price9),
            findViewById(R.id.price10)
        )
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