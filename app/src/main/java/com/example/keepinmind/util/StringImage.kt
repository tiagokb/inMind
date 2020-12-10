package com.example.keepinmind.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream


class StringImage {
    companion object {

        fun drawableToString(imageDrawable: Drawable?): String {

            if (imageDrawable == null){
                return ""
            }

            val drawable: BitmapDrawable = imageDrawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val byteArray: ByteArray = baos.toByteArray()
            baos.close()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        fun string64ToBitmap(encodedString: String): Bitmap? {

            if (encodedString == ""){
                return null
            }

            val decodedByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
            return bitmap
        }

    }
}