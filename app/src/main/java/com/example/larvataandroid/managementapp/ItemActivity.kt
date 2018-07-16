package com.example.larvataandroid.managementapp

import android.graphics.Picture
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : AppCompatActivity() {

    private var pictureFileName: String? = null
    // 照片元件
    private val picture: ImageView by bind(R.id.picture)
    // 寫入外部儲存設備授權請求代碼
    private val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
//        initialItem()
    }


//    // 照片檔案名稱
//    private var pictureFileName: String? = null
//    // 照片元件
//    private val picture: ImageView by bind(R.id.picture)
//    // 寫入外部儲存設備授權請求代碼
//    private val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 100


}
