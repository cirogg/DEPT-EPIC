package com.cirogg.deptepicchallenge.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cirogg.deptepicchallenge.AnimatedGifEncoder
import com.cirogg.deptepicchallenge.api.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.*
import java.io.File.separator


class DownloadRepository(
    private val retrofitInstance: RetrofitInstance
) {

    private val listOfBitmaps = mutableListOf<Bitmap>()

    fun downloadAndSave(date: String, imageName: String): Flow<Bitmap> = flow {
        val call = retrofitInstance.epicApi.getPhoto(date, imageName)
        val image = call.body()
        if (call.isSuccessful) {
            val bmp = BitmapFactory.decodeStream(image?.byteStream())
            emit(bmp)
            //saveFile(image, date, imageName)
        } else {
            //emit(ResponseBody.create(null, ""))
        }
    }

    fun saveFile(bitmap: Bitmap?, date: String, imageName: String, context: Context): Boolean {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                val values = contentValues(imageName)
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + date.replace("/", "-"))
                values.put(MediaStore.Images.Media.IS_PENDING, true)
                values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
                // RELATIVE_PATH and IS_PENDING are introduced in API 29.

                val uri: Uri? = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                if (uri != null) {
                    saveImageToStream(bitmap!!, context.contentResolver.openOutputStream(uri))
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    context.contentResolver.update(uri, values, null, null)
                }
            } else {
                val directory =
                    File(Environment.getExternalStorageDirectory().toString() + separator + date.replace("/", "-"))

                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val fileName = "$imageName.jpg"
                val file = File(directory, fileName)
                saveImageToStream(bitmap!!, FileOutputStream(file))
                if (file.absolutePath != null) {
                    val values = contentValues(imageName)
                    values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                    // .DATA is deprecated in API 29
                    context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("saveFile", e.toString())
            return false
        }

        return true

    }

    private fun contentValues(imageName: String): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                listOfBitmaps.add(bitmap)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun generateGIF(): Flow<ByteArray>? = flow {
        val bos = ByteArrayOutputStream()
        val encoder = AnimatedGifEncoder()
        encoder.start(bos)
        for (bitmap in listOfBitmaps) {
            encoder.addFrame(bitmap)
        }
        encoder.finish()
        listOfBitmaps.clear()
        emit(bos.toByteArray())
    }

}