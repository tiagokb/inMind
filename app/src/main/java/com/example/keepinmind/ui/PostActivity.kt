package com.example.keepinmind.ui

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.TypefaceCompat
import com.example.keepinmind.R

class PostActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener{

    private lateinit var fontTypeSelector: ImageView
    private lateinit var fontSizeSelector: ImageView
    private lateinit var colorSelector: ImageView
    private lateinit var textGravitySelector: ImageView

    private lateinit var bottomLayout: LinearLayout

    private lateinit var textSize: SeekBar
    private lateinit var textFont: Spinner
    private lateinit var fontList: Array<String>

    //color Buttons
    private lateinit var colorWhite: ImageView
    private lateinit var colorBlack: ImageView
    private lateinit var colorRed: ImageView
    private lateinit var colorOrange: ImageView
    private lateinit var colorGreen: ImageView
    private lateinit var colorDarkGreen: ImageView
    private lateinit var colorYellow: ImageView
    private lateinit var colorBlue: ImageView
    private lateinit var colorLightBlue: ImageView
    private lateinit var colorPink: ImageView

    //gravity Buttons
    private lateinit var gravityTopStart: ImageView
    private lateinit var gravityTopCenter: ImageView
    private lateinit var gravityTopEnd: ImageView
    private lateinit var gravityCenterStart: ImageView
    private lateinit var gravityCenter: ImageView
    private lateinit var gravityCenterEnd: ImageView
    private lateinit var gravityBottomStart: ImageView
    private lateinit var gravityBottomCenter: ImageView
    private lateinit var gravityBottomEnd: ImageView


    private lateinit var text: EditText


    private lateinit var includerFontTypeTopMenuLayout: LinearLayout
    private lateinit var includerFontSizeTopMenuLayout: LinearLayout
    private lateinit var includerFontColorTopMenuLayout: LinearLayout
    private lateinit var includerTextGravityLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        text = findViewById(R.id.text_post)

        textFont = findViewById(R.id.post_menu_top_font_type_spinner)
        fontList = resources.getStringArray(R.array.lista_font)
        ArrayAdapter.createFromResource(
            this,
            R.array.lista_font,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textFont.adapter = adapter
        }

        textFont.onItemSelectedListener = this

        textSize = findViewById(R.id.post_menu_top_font_size_seek_bar)

        //Initialize color buttons

        colorWhite = findViewById(R.id.color_white)
        colorBlack = findViewById(R.id.color_black)
        colorRed = findViewById(R.id.color_red)
        colorOrange = findViewById(R.id.color_orange)
        colorGreen = findViewById(R.id.color_green)
        colorDarkGreen = findViewById(R.id.color_dark_green)
        colorYellow = findViewById(R.id.color_yellow)
        colorBlue = findViewById(R.id.color_blue)
        colorLightBlue = findViewById(R.id.color_light_blue)
        colorPink = findViewById(R.id.color_pink)

        //////////////////////////////

        //Initialize gravity Buttons

        gravityTopStart = findViewById(R.id.topStart)
        gravityTopCenter = findViewById(R.id.topCenter)
        gravityTopEnd = findViewById(R.id.topEnd)
        gravityCenterStart = findViewById(R.id.centerStart)
        gravityCenter = findViewById(R.id.center)
        gravityCenterEnd = findViewById(R.id.centerEnd)
        gravityBottomStart = findViewById(R.id.bottomStart)
        gravityBottomCenter = findViewById(R.id.bottomCenter)
        gravityBottomEnd = findViewById(R.id.bottomEnd)

        //////////////////////////////

        fontTypeSelector = findViewById(R.id.post_menu_bottom_font_type)
        fontSizeSelector = findViewById(R.id.post_menu_bottom_font_size)
        colorSelector = findViewById(R.id.post_menu_bottom_font_color)
        textGravitySelector = findViewById(R.id.post_menu_bottom_text_position)

        bottomLayout = findViewById(R.id.post_menu_bottom)

        includerFontTypeTopMenuLayout = findViewById(R.id.post_menu_top_font_type)
        includerFontSizeTopMenuLayout = findViewById(R.id.post_menu_top_font_size)
        includerFontColorTopMenuLayout = findViewById(R.id.post_menu_top_font_color)
        includerTextGravityLayout = findViewById(R.id.post_text_gravity_layout)



        setOnClickListener(fontTypeSelector, fontSizeSelector, colorSelector, textGravitySelector)


        setOnColorClickListener(
            colorWhite,
            colorBlack,
            colorRed,
            colorOrange,
            colorGreen,
            colorDarkGreen,
            colorYellow,
            colorBlue,
            colorLightBlue,
            colorPink
        )

        setOnGravityClick(
            gravityTopStart,
            gravityTopCenter,
            gravityTopEnd,
            gravityCenterStart,
            gravityCenter,
            gravityCenterEnd,
            gravityBottomStart,
            gravityBottomCenter,
            gravityBottomEnd
        )

        textSize.setOnSeekBarChangeListener(this)

    }

    private fun setOnGravityClick(
        gravityTopStart: ImageView,
        gravityTopCenter: ImageView,
        gravityTopEnd: ImageView,
        gravityCenterStart: ImageView,
        gravityCenter: ImageView,
        gravityCenterEnd: ImageView,
        gravityBottomStart: ImageView,
        gravityBottomCenter: ImageView,
        gravityBottomEnd: ImageView
    ) {

        Log.e("teste", "try")
        gravityTopStart.setOnClickListener {
            text.gravity = Gravity.TOP or Gravity.START
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityTopCenter.setOnClickListener {
            text.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityTopEnd.setOnClickListener {
            text.gravity = Gravity.TOP or Gravity.END
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityCenterStart.setOnClickListener {
            text.gravity = Gravity.CENTER_VERTICAL or Gravity.START
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityCenter.setOnClickListener {
            text.gravity = Gravity.CENTER
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityCenterEnd.setOnClickListener {
            text.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityBottomStart.setOnClickListener {
            text.gravity = Gravity.BOTTOM or Gravity.START
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityBottomCenter.setOnClickListener {
            text.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }
        gravityBottomEnd.setOnClickListener {
            text.gravity = Gravity.BOTTOM or Gravity.END
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomLayout.visibility = View.VISIBLE
        }


    }

    private fun setOnColorClickListener(
        colorWhite: ImageView,
        colorBlack: ImageView,
        colorRed: ImageView,
        colorOrange: ImageView,
        colorGreen: ImageView,
        colorDarkGreen: ImageView,
        colorYellow: ImageView,
        colorBlue: ImageView,
        colorLightBlue: ImageView,
        colorPink: ImageView
    ) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Log.e("teste", "new textColor")
            colorWhite.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.white,
                        theme
                    )
                )
            }
            colorBlack.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.black,
                        theme
                    )
                )
            }
            colorRed.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.red,
                        theme
                    )
                )
            }
            colorOrange.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.orange,
                        theme
                    )
                )
            }
            colorGreen.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.green,
                        theme
                    )
                )
            }
            colorDarkGreen.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.dark_green,
                        theme
                    )
                )
            }
            colorYellow.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.yellow,
                        theme
                    )
                )
            }
            colorBlue.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.blue,
                        theme
                    )
                )
            }
            colorLightBlue.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.light_blue,
                        theme
                    )
                )
            }
            colorPink.setOnClickListener {
                text.setTextColor(
                    resources.getColor(
                        R.color.pink,
                        theme
                    )
                )
            }

        } else {
            Log.e("teste", "old textColor")
            colorWhite.setOnClickListener { text.setTextColor(resources.getColor(R.color.white)) }
            colorBlack.setOnClickListener { text.setTextColor(resources.getColor(R.color.black)) }
            colorRed.setOnClickListener { text.setTextColor(resources.getColor(R.color.red)) }
            colorOrange.setOnClickListener { text.setTextColor(resources.getColor(R.color.orange)) }
            colorGreen.setOnClickListener { text.setTextColor(resources.getColor(R.color.green)) }
            colorDarkGreen.setOnClickListener { text.setTextColor(resources.getColor(R.color.dark_green)) }
            colorYellow.setOnClickListener { text.setTextColor(resources.getColor(R.color.yellow)) }
            colorBlue.setOnClickListener { text.setTextColor(resources.getColor(R.color.blue)) }
            colorLightBlue.setOnClickListener { text.setTextColor(resources.getColor(R.color.light_blue)) }
            colorPink.setOnClickListener { text.setTextColor(resources.getColor(R.color.pink)) }
        }
    }

    private fun setOnClickListener(
        fontTypeSelector: ImageView,
        fontSizeSelector: ImageView,
        colorSelector: ImageView,
        textGravitySelector: ImageView
    ) {
        fontTypeSelector.setOnClickListener(this)
        fontSizeSelector.setOnClickListener(this)
        colorSelector.setOnClickListener(this)
        textGravitySelector.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            fontTypeSelector.id -> editFontType()
            fontSizeSelector.id -> editFontSize()
            colorSelector.id -> editFontColor()
            textGravitySelector.id -> setTextGravity()
        }
    }

    private fun editFontColor() {

        if (includerFontColorTopMenuLayout.visibility == View.VISIBLE) {
            includerFontColorTopMenuLayout.visibility = View.GONE
        } else {
            includerFontTypeTopMenuLayout.visibility = View.GONE
            includerFontSizeTopMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            includerFontColorTopMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun editFontSize() {

        if (includerFontSizeTopMenuLayout.visibility == View.VISIBLE) {
            includerFontSizeTopMenuLayout.visibility = View.GONE
        } else {
            includerFontTypeTopMenuLayout.visibility = View.GONE
            includerFontColorTopMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            includerFontSizeTopMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun editFontType() {
        if (includerFontTypeTopMenuLayout.visibility == View.VISIBLE) {
            includerFontTypeTopMenuLayout.visibility = View.GONE
        } else {
            includerFontSizeTopMenuLayout.visibility = View.GONE
            includerFontColorTopMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            includerFontTypeTopMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun setTextGravity() {
        includerFontSizeTopMenuLayout.visibility = View.GONE
        includerFontColorTopMenuLayout.visibility = View.GONE
        includerFontTypeTopMenuLayout.visibility = View.GONE
        bottomLayout.visibility = View.GONE
        text.visibility = View.GONE
        includerTextGravityLayout.visibility = View.VISIBLE
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textSize.min = 12
            text.textSize = progress.toFloat()
        } else {
            val size = progress.toFloat() + 12
            text.textSize = size
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textSize.min = 12
            text.textSize = seekBar?.progress!!.toFloat()
        } else {
            val size = seekBar?.progress!!.toFloat() + 12
            text.textSize = size
        }

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val fontName: String = fontList.get(position)
        when(fontName){
            "arial" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.arial)
            }
            "arialbd" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.arialbd)
            }
            "arialbi" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.arialbi)
            }
            "ariali" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.ariali)
            }
            "ariblk" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.ariblk)
            }
            "calibri" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.calibri)
            }
            "calibrib" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.calibrib)
            }
            "calibrii" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.calibrii)
            }
            "calibril" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.calibril)
            }
            "calibrili" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.calibrili)
            }
            "calibriz" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.calibriz)
            }
            "cambriab" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.cambriab)
            }
            "cambriai" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.cambriai)
            }
            "cambriaz" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.cambriaz)
            }
            "inkfree" -> run {
                text.typeface = ResourcesCompat.getFont(this, R.font.inkfree)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.e("spinner", "onNothingSelected")
    }
}