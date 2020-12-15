package com.example.keepinmind.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.example.keepinmind.R
import com.example.keepinmind.listener.ColorClickListener
import com.example.keepinmind.listener.IntentPickListener
import com.example.keepinmind.ui.fragments.IntentDialogFragment

class PostActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    AdapterView.OnItemSelectedListener {

    private var REQUEST_CODE = 0
    private var colorChangingRequestCode = 0

    //content, the user will edit this elements
    private lateinit var photo: ImageView
    private lateinit var text: EditText

    //side layout to select how element will be edited
    private lateinit var sideSelector: LinearLayout

    //side layout elements
    private lateinit var sideLayoutImage: ImageView
    private lateinit var sideLayoutText: ImageView

    //the two bottom layouts - for image editing and text editing
    private lateinit var bottomTextLayout: LinearLayout
    private lateinit var bottomPhotoLayout: LinearLayout

    //bottom text layout elements
    private lateinit var fontTypeSelector: ImageView
    private lateinit var fontSizeSelector: ImageView
    private lateinit var textGravitySelector: ImageView

    //bottom photo layout elements
    private lateinit var photoUpload: ImageView
    private lateinit var photoShadow: ImageView

    //colorSelector - for two bottomlayouts
    private lateinit var fontColorSelector: ImageView
    private lateinit var photoColorSelector: ImageView

    private lateinit var photoShadowSelector: View
    private lateinit var photoBackgroundColor: View

    //bottomTextLayout actions
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
    private lateinit var colorTransparent: ImageView

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

    //includer layout for editing components
    private lateinit var includerFontTypeTopMenuLayout: LinearLayout
    private lateinit var includerFontSizeTopMenuLayout: LinearLayout
    private lateinit var includerColorSideMenuLayout: LinearLayout
    private lateinit var includerTextGravityLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        photo = findViewById(R.id.post_photo_background)
        text = findViewById(R.id.text_post)

        sideSelector = findViewById(R.id.post_menu_side_chooser)

        sideLayoutImage = findViewById(R.id.side_menu_iv_photo)
        sideLayoutText = findViewById(R.id.side_menu_iv_text)

        bottomTextLayout = findViewById(R.id.post_menu_bottom_text)
        bottomPhotoLayout = findViewById(R.id.post_menu_bottom_photo)

        fontTypeSelector = findViewById(R.id.post_menu_bottom_font_type)
        fontSizeSelector = findViewById(R.id.post_menu_bottom_font_size)
        textGravitySelector = findViewById(R.id.post_menu_bottom_text_position)

        photoUpload = findViewById(R.id.post_menu_bottom_photo_upload)
        photoShadow = findViewById(R.id.post_menu_bottom_photo_shadow)

        fontColorSelector = findViewById(R.id.post_menu_bottom_font_color)
        photoColorSelector = findViewById(R.id.post_menu_bottom_photo_color)

        photoShadowSelector = findViewById(R.id.post_background_shadow)
        photoBackgroundColor = findViewById(R.id.post_background_color)

        textSize = findViewById(R.id.post_menu_top_font_size_seek_bar)
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
        colorTransparent = findViewById(R.id.color_transparent)

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

        includerFontTypeTopMenuLayout = findViewById(R.id.post_menu_top_font_type)
        includerFontSizeTopMenuLayout = findViewById(R.id.post_menu_top_font_size)
        includerColorSideMenuLayout = findViewById(R.id.post_menu_top_font_color)
        includerTextGravityLayout = findViewById(R.id.post_text_gravity_layout)

        setOnSideMenuClickListener(sideLayoutImage, sideLayoutText)

        setOnClickListener(
            fontTypeSelector,
            fontSizeSelector,
            fontColorSelector,
            photoColorSelector,
            textGravitySelector,
            photoUpload,
            photoShadow
        )

        textFont.onItemSelectedListener = this

        textSize.setOnSeekBarChangeListener(this)

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

        sideLayoutImage.performClick()
    }

    private fun setOnSideMenuClickListener(
        sideLayoutImage: ImageView,
        sideLayoutText: ImageView
    ) {
        sideLayoutImage.setOnClickListener {
            text.visibility = View.GONE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sideLayoutImage.drawable.setTint(
                    resources.getColor(
                        R.color.white, theme
                    )
                )
                sideLayoutText.drawable.setTint(
                    resources.getColor(
                        R.color.notSelectedItemMenu, theme
                    )
                )
            } else {
                sideLayoutImage.drawable.setTint(resources.getColor(R.color.white))
                sideLayoutText.drawable.setTint(resources.getColor(R.color.notSelectedItemMenu))
            }

            bottomTextLayout.visibility = View.GONE
            bottomPhotoLayout.visibility = View.VISIBLE
        }
        sideLayoutText.setOnClickListener {
            text.visibility = View.VISIBLE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sideLayoutImage.drawable.setTint(
                    resources.getColor(
                        R.color.notSelectedItemMenu, theme
                    )
                )
                sideLayoutText.drawable.setTint(
                    resources.getColor(
                        R.color.white, theme
                    )
                )
            } else {
                sideLayoutImage.drawable.setTint(resources.getColor(R.color.notSelectedItemMenu))
                sideLayoutText.drawable.setTint(resources.getColor(R.color.white))
            }

            bottomPhotoLayout.visibility = View.GONE
            bottomTextLayout.visibility = View.VISIBLE
        }
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
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityTopCenter.setOnClickListener {
            text.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityTopEnd.setOnClickListener {
            text.gravity = Gravity.TOP or Gravity.END
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityCenterStart.setOnClickListener {
            text.gravity = Gravity.CENTER_VERTICAL or Gravity.START
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityCenter.setOnClickListener {
            text.gravity = Gravity.CENTER
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityCenterEnd.setOnClickListener {
            text.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityBottomStart.setOnClickListener {
            text.gravity = Gravity.BOTTOM or Gravity.START
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityBottomCenter.setOnClickListener {
            text.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
        }
        gravityBottomEnd.setOnClickListener {
            text.gravity = Gravity.BOTTOM or Gravity.END
            includerTextGravityLayout.visibility = View.GONE
            text.visibility = View.VISIBLE
            bottomTextLayout.visibility = View.VISIBLE
            sideSelector.visibility = View.VISIBLE
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
        val listener: ColorClickListener = object : ColorClickListener {
            override fun colorChanging(requestCode: Int, colorId: Int) {
                if (requestCode > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        text.setTextColor(resources.getColor(colorId, theme))
                    } else {
                        text.setTextColor(resources.getColor(colorId))
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        photoBackgroundColor.setBackgroundColor(resources.getColor(colorId, theme))
                    } else {
                        photoBackgroundColor.setBackgroundColor(resources.getColor(colorId))
                    }
                }
            }

        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Log.e("teste", "new textColor")
            colorWhite.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.white)
            }
            colorBlack.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.black)
            }
            colorRed.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.red)
            }
            colorOrange.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.orange)
            }
            colorGreen.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.green)
            }
            colorDarkGreen.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.dark_green)
            }
            colorYellow.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.yellow)
            }
            colorBlue.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.blue)
            }
            colorLightBlue.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.light_blue)
            }
            colorPink.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, R.color.pink)
            }
            colorTransparent.setOnClickListener {
                listener.colorChanging(colorChangingRequestCode, android.R.color.transparent)
            }
        }
    }

    private fun setOnClickListener(
        fontTypeSelector: ImageView,
        fontSizeSelector: ImageView,
        fontColorSelector: ImageView,
        photoColorSelector: ImageView,
        textGravitySelector: ImageView,
        photoUpload: ImageView,
        photoShadow: ImageView
    ) {
        fontTypeSelector.setOnClickListener(this)
        fontSizeSelector.setOnClickListener(this)
        fontColorSelector.setOnClickListener(this)
        photoColorSelector.setOnClickListener(this)
        textGravitySelector.setOnClickListener(this)
        photoUpload.setOnClickListener(this)
        photoShadow.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            fontTypeSelector.id -> editFontType()
            fontSizeSelector.id -> editFontSize()
            fontColorSelector.id -> editColorFromText()
            photoColorSelector.id -> editColorFromPhoto()
            textGravitySelector.id -> setTextGravity()
            photoUpload.id -> uploadPhoto()
            photoShadow.id -> editShadow()
        }
    }

    private fun uploadPhoto() {
        val dialogInterface = IntentDialogFragment(object : IntentPickListener {
            override fun actionPick(requestCode: Int) {
                REQUEST_CODE = requestCode
                intentTakeAShotOrImageFromGallery()
            }
        })

        dialogInterface.show(supportFragmentManager, "intentInterface")
    }

    private fun intentTakeAShotOrImageFromGallery() {
        when (REQUEST_CODE) {
            1 -> openCamera()
            2 -> openGallery()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, 1)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(intent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, 2)
    }

    private fun editShadow() {
        if (photoShadowSelector.visibility == View.GONE) {
            photoShadowSelector.visibility = View.VISIBLE
        } else {
            photoShadowSelector.visibility = View.GONE
        }
    }

    private fun editColorFromPhoto() {
        colorChangingRequestCode = 0

        if (includerColorSideMenuLayout.visibility == View.VISIBLE) {
            includerColorSideMenuLayout.visibility = View.GONE
            sideSelector.visibility = View.VISIBLE
        } else {
            includerFontTypeTopMenuLayout.visibility = View.GONE
            includerFontSizeTopMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            sideSelector.visibility = View.GONE
            includerColorSideMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun editColorFromText() {
        colorChangingRequestCode = 1

        if (includerColorSideMenuLayout.visibility == View.VISIBLE) {
            includerColorSideMenuLayout.visibility = View.GONE
            sideSelector.visibility = View.VISIBLE
        } else {
            includerFontTypeTopMenuLayout.visibility = View.GONE
            includerFontSizeTopMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            sideSelector.visibility = View.GONE
            includerColorSideMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun editFontSize() {

        if (includerFontSizeTopMenuLayout.visibility == View.VISIBLE) {
            includerFontSizeTopMenuLayout.visibility = View.GONE
            sideSelector.visibility = View.VISIBLE
        } else {
            includerFontTypeTopMenuLayout.visibility = View.GONE
            includerColorSideMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            sideSelector.visibility = View.GONE
            includerFontSizeTopMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun editFontType() {
        if (includerFontTypeTopMenuLayout.visibility == View.VISIBLE) {
            includerFontTypeTopMenuLayout.visibility = View.GONE
            sideSelector.visibility = View.VISIBLE
        } else {
            includerFontSizeTopMenuLayout.visibility = View.GONE
            includerColorSideMenuLayout.visibility = View.GONE
            includerTextGravityLayout.visibility = View.GONE
            sideSelector.visibility = View.GONE
            includerFontTypeTopMenuLayout.visibility = View.VISIBLE
        }
    }

    private fun setTextGravity() {
        includerFontSizeTopMenuLayout.visibility = View.GONE
        includerColorSideMenuLayout.visibility = View.GONE
        includerFontTypeTopMenuLayout.visibility = View.GONE
        bottomTextLayout.visibility = View.GONE
        text.visibility = View.GONE
        sideSelector.visibility = View.GONE
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
        when (fontName) {
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != RESULT_OK || data == null) {
            Toast.makeText(this, "Erro Inesperado, Tente novamente mais tarde!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        when (requestCode) {
            1 -> {
                val bundle = data.extras
                val bitmap = bundle?.let {
                    it.get("data")
                } as Bitmap
                photo.setImageBitmap(bitmap)
            }
            2 -> {
                val inputStream = data.data?.let { contentResolver.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                photo.setImageBitmap(bitmap)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}