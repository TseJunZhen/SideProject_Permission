package com.example.larvataandroid.managementapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.os.Build

class MainActivity : AppCompatActivity() {

    private val PERMISSION_WRITE_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE"

    private lateinit var buttonSaved: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialView()
        onclickItem()
    }

    fun initialView() {
        buttonSaved = btnSavePicture
    }
    fun onclickItem() {
        buttonSaved.setOnClickListener(View.OnClickListener {
            if (!hasPermission()) {
                if (needCheckPermission()) {
                    return@OnClickListener
                }
            }
            doSavePicture()
        })
    }

    private fun needCheckPermission(): Boolean {
        //MarshMallow(API-23)之後要在 Runtime 詢問權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val perms = arrayOf(PERMISSION_WRITE_STORAGE)
            val permsRequestCode = 200
            requestPermissions(perms, permsRequestCode)
            return true
        }
        return false
    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.checkSelfPermission(this, PERMISSION_WRITE_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else true
    }


    fun doSavePicture() {
        if (saveToPictureFolder()) {
            Toast.makeText(this, "儲存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "儲存失敗", Toast.LENGTH_SHORT).show();
        }

    }

    fun saveToPictureFolder(): Boolean {
        val picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        Log.d(">>>", "Pictures Folder path: " + picDir.getAbsolutePath())
        //假如有該目錄
        if (picDir.exists()) {
            //儲存圖片
            val pic = File(picDir, "pic.jpg")
            imgPicture.isDrawingCacheEnabled = true
            imgPicture.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_AUTO
            val bmp = imgPicture.drawingCache
            return saveBitmap(bmp, pic)
        }
        return false

    }

    private fun saveBitmap(bmp: Bitmap?, pic: File?): Boolean {
        if (bmp == null || pic == null) return false
        //
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(pic)
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, out)
            out!!.flush()
            scanGallery(this, pic)
            Log.d(">>>", "bmp path: " + pic.absolutePath)
            return true
        } catch (e: Exception) {
            Log.e(">>>", "save bitmap failed!")
            e.printStackTrace()
        } finally {
            try {
                if (out != null) {
                    out!!.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return false
    }

    private fun scanGallery(ctx: Context, file: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.data = contentUri
        ctx.sendBroadcast(mediaScanIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.size > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(">>>", "取得授權，可以執行動作了")
                    doSavePicture()
                }
            }
        }
    }
    // 加入載入選單資源的函式
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

}