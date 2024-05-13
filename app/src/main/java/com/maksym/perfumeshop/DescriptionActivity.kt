package com.maksym.perfumeshop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class DescriptionActivity : AppCompatActivity() {
    private var perfumeImages: IntArray = intArrayOf(
        R.drawable.parfume1,
        R.drawable.parfume2,
        R.drawable.perfume3,
        R.drawable.perfume4,
        R.drawable.perfume5,
        R.drawable.perfume6,
        R.drawable.perfume7,
        R.drawable.perfume8,
        R.drawable.perfume9,
        R.drawable.perfume10
    )
    private var brandTexts = arrayOf("Versace", "Dior", "Chanel", "Armani", "Rabbane", "Hugo", "Prada", "Armani", "Huggo", "Lacoste")
    private var modelTexts = arrayOf("Eros", "Sauvage", "Bleu", "Stronger", "1 million", "Boss", "Luna", "Code", "Jeans", "L’Homme")
    private var priceTexts = arrayOf("120$", "100$", "150$", "100$", "110$", "90$", "50$", "50$", "60$", "40$")

    private var descriptionTexts = arrayOf("When creating Versace Eros Eau de Toilette, " +
            "the ancient Greek god Eros, capable of igniting a flame in the" +
            " heart with one shot from a bow, served as inspiration." +
            " A wave of freshness covers the man, revealing his inner" +
            " sensuality and courage. The fragrant haze creates an " +
            "attractive flair that attracts women like a love potion." +
            " Temptation begins with fresh mint, combining green apples and lemon." +
            " These are followed by tonka beans surrounded by geranium and ambroxan." +
            " And the exquisite trail impresses with the depth of accords of Madagascar" +
            " vanilla, vetiver, oakmoss, Virginia cedar and Atlas cedar.",
        "Dior Sauvage is a fragrance in which your chosen one will want to drown. " +
                "The graceful dance of the enchanting composition captivates with " +
                "its freshness and freedom. Courageous, elegant, sexy and attractive " +
                "perfume, which perfectly fits the image of a strong, handsome man. " +
                "A truly magical creation of French perfumers from the fashion house Dior. " +
                "The debut chord is sweet and slightly tart bergamot, enveloping with " +
                "its unique aroma. The heart of the composition is revealed in the tenderness " +
                "and sophistication of anise, drowning in a fragrant cloud of Chinese pepper" +
                " intertwined with intoxicating lavender, leaving an aftertaste of the velvety" +
                " warmth of nutmeg. This entire symphony of aromas ends with a noble, sexy ambroxan, " +
                "intertwined with the sweetness of raspberry, which gives this brutal perfume " +
                "composition some playfulness and lightness. Dior Sauvage personifies the temperament, " +
                "emphasizes the masculinity and elegance of its owner, conquering women's hearts. " +
                "The fragrance will be an excellent addition to the image of a strong, decisive " +
                "and self-confident man who appreciates sincerity and simplicity.",
        "An ode to masculine freedom in a woody aromatic composition with a captivating trail. " +
                "A timeless scent in a mysterious blue bottle." +
                "Here, BLEU DE CHANEL appears as an eau de parfum, " +
                "whose exquisitely expressive fragrance reveals a decisive character.",
        "Emporio Armani Stronger With You Absolutely Parfum is an oriental amber amber fragrance " +
                "for men, released in 2017 and joining the Emporio Armani collection. Author: " +
                "Cecile Matton. This is an exciting, assertive and sensual scent, an expression" +
                " of absolute love that knows no barriers. It talks about the feeling of two strong " +
                "personalities who together become one; mutual passion gives them truly fantastic" +
                " strength and invulnerability." +
                "A powerful start forms a bright chord of expensive rum in the company of bergamot, " +
                "enhanced by the slightly spicy freshness of elemi. The heart is a dazzling duet of " +
                "liqueur-fruity shades of French lavender and tart greenery of davana. The elegant" +
                " base features a gourmand accord of glazed chestnut and Madagascar vanilla, balanced " +
                "by smoky notes of patchouli and Virginia cedar.",
        "Elegant, sophisticated and luxurious at the same time, Paco Rabanne 1 Million will exceed all" +
                " your expectations. With it, you are guaranteed success in any life situations, both " +
                "at important business meetings and at a romantic dinner with your beloved woman. " +
                "It will perfectly complement the image of its owner, emphasizing his inner strength.",
        "Slow, gentle seduction is found in Boss Hugo Boss The Scent Eau de Toilette for men. This is" +
                " true amber art. A man who wears this fragrance can have no doubts about his success, " +
                "because the perfumers put a real aphrodisiac there - the fruits of the African maninka.",
        "Only brave, confident and ambitious men can take part in a sailing regatta, because this " +
                "extreme sport requires special training." +
                "Prada Luna Rossa eau de toilette was inspired by the yacht of the same name, whose " +
                "crew became one of the most beloved among the participants of the America's Cup regatta. " +
                "This fragrance combines sensuality and dynamics, modernity and classic sound.",
        "Giorgio Armani Armani Code Eau de Toilette is a spicy woody fragrance for men, an updated " +
                "version of the iconic Armani Code from 2004. Magnetic and sensual masculinity is " +
                "always attractive, no matter how fickle fashion is. Armani Code is about a man " +
                "whose courage lies in revealing himself, his potential and his sensuality. He is " +
                "heading towards his goal, but manages to notice the beauty of the world and admire it.",
        "Dare to freshen up with Hugo Jeans for men, a bold blend of vibrant grapefruit," +
                " invigorating mint and warm sandalwood, housed in a matte blue bottle with " +
                "the iconic Hugo logo. How about a perfect fit?",
        "The woody-spicy fragrance for men opens with a bold combination of juicy mandarin, sweet " +
                "orange essence and rhubarb. A bright spicy heart is revealed by black pepper and " +
                "ginger, while a masculine and sensual accord of cedar wood, dry ambergris and " +
                "vanilla gives the fragrance a sense of timeless elegance." +
                "The bottle and packaging are decorated with Lacoste's iconic crocodile logo.")
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        val backBtn: ImageButton = findViewById(R.id.back_btn)
        val cartBtn: ImageButton = findViewById(R.id.cart_btn)
        val addToCart: ImageButton = findViewById(R.id.add_to_cart_btn)
        val perfumeImg: ImageView = findViewById(R.id.perfume_img)
        val brandTxt: TextView = findViewById(R.id.brand_text)
        val modelTxt: TextView = findViewById(R.id.model_text)
        val priceTxt: TextView = findViewById(R.id.price_text)
        val descriptionTxt: TextView = findViewById(R.id.description_text)

        sharedPreferences = getSharedPreferences("myCart", MODE_PRIVATE)

        val perfumeIndex = intent.getIntExtra("perfume_key", 0)
        perfumeImg.setImageResource(perfumeImages[perfumeIndex])
        brandTxt.text = brandTexts[perfumeIndex]
        modelTxt.text = modelTexts[perfumeIndex]
        priceTxt.text = priceTexts[perfumeIndex]
        descriptionTxt.text = descriptionTexts[perfumeIndex]
        addToCart.setOnClickListener {
            val cartList = sharedPreferences.getStringSet("listCart", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            var check = true
            cartList.forEach{
                val name = it.split(":")[0]
                if(name.toInt() == perfumeIndex) check = false
            }
            if(check){
            cartList.add("$perfumeIndex:1")
            val editor = sharedPreferences.edit()
            editor.putStringSet("listCart", cartList.toSet())
            editor.apply()
            }
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        cartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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