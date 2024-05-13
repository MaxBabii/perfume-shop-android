package com.maksym.perfumeshop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    private lateinit var perfumeLayouts: List<FrameLayout>
    private var fragranceTexts = arrayOf("Ice", "Sweet", "Floral", "Bergamot", "Grapefruit", "Ginger", "Tonka beans", "Bergamot", "Vetiver", "Vetiver")
    private lateinit var addToCartBtn: List<ImageButton>
    private lateinit var enterFragrance: EditText
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enterFragrance = findViewById(R.id.enterFieldNumber)
        val firstPerfume: ImageButton = findViewById(R.id.perfume1)
        val secondPerfume: ImageButton = findViewById(R.id.perfume2)
        val thirdPerfume: ImageButton = findViewById(R.id.perfume3)
        val fourthPerfume: ImageButton = findViewById(R.id.perfume4)
        val fivePerfume: ImageButton = findViewById(R.id.perfume5)
        val sixPerfume: ImageButton = findViewById(R.id.perfume6)
        val sevenPerfume: ImageButton = findViewById(R.id.perfume7)
        val eightPerfume: ImageButton = findViewById(R.id.perfume8)
        val ninePerfume: ImageButton = findViewById(R.id.perfume9)
        val tenPerfume: ImageButton = findViewById(R.id.perfume10)
        val cart: ImageButton = findViewById(R.id.cart_btn)
        val historyBtn: ImageButton = findViewById(R.id.history_btn)
        perfumeLayouts = listOf(
            findViewById(R.id.first_layout),
            findViewById(R.id.second_layout),
            findViewById(R.id.third_layout),
            findViewById(R.id.four_layout),
            findViewById(R.id.five_layout),
            findViewById(R.id.six_layout),
            findViewById(R.id.seven_layout),
            findViewById(R.id.eight_layout),
            findViewById(R.id.nine_layout),
            findViewById(R.id.ten_layout)
        )
        sharedPreferences = getSharedPreferences("myCart", MODE_PRIVATE)
        addToCartBtn = listOf(
            findViewById(R.id.add_to_cart_1),
            findViewById(R.id.add_to_cart_2),
            findViewById(R.id.add_to_cart_3),
            findViewById(R.id.add_to_cart_4),
            findViewById(R.id.add_to_cart_5),
            findViewById(R.id.add_to_cart_6),
            findViewById(R.id.add_to_cart_7),
            findViewById(R.id.add_to_cart_8),
            findViewById(R.id.add_to_cart_9),
            findViewById(R.id.add_to_cart_10)
        )
        firstPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 0)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        secondPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 1)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        thirdPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 2)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        fourthPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 3)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        fivePerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 4)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        sixPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 5)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        sevenPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 6)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        eightPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 7)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        ninePerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 8)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        tenPerfume.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java)
            intent.putExtra("perfume_key", 9)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        historyBtn.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        enterFragrance.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                searchFragrance(s.toString())
            }
        })
        for(i in addToCartBtn.indices){
            addToCartBtn.elementAt(i).setOnClickListener {
                val cartList = sharedPreferences.getStringSet("listCart", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
                var check = true
                cartList.forEach{
                    val name = it.split(":")[0]
                    if(name.toInt() == i) check = false
                }
                if(check){
                cartList.add("$i:1")
                val editor = sharedPreferences.edit()
                editor.putStringSet("listCart", cartList.toSet())
                editor.apply()
                }
            }
        }
        hideUi()
    }
    private fun searchFragrance(query: String) {
        for (i in fragranceTexts.indices) {
            val fragrance = fragranceTexts[i]
            val layout = perfumeLayouts[i]
            if (fragrance.contains(query, true)) layout.visibility = View.VISIBLE
            else layout.visibility = View.GONE
        }
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