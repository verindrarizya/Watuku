package com.capstone.watuku

import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.watuku.databinding.ActivityPreviewBinding
import java.io.File

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppBar()

        val data = intent.extras
        uri = Uri.parse(data?.getString(EXTRA_IMAGE_PREVIEW))
        Log.d(TAG, "Get Uri from intent: $uri")

        loadImage()
        // Set listener for the button
        setButtonCancelListener()
        setButtonSendListener()
    }

    private fun initAppBar() {
        supportActionBar?.apply {
            title = getString(R.string.preview)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun loadImage() {
        Glide.with(this)
                .load(uri)
                .apply(RequestOptions())
                .into(binding.imgPreview)
    }

    private fun setButtonCancelListener() {
        binding.buttonCancel.setOnClickListener {
            deleteImage()
            finish()
        }
    }

    private fun setButtonSendListener() {
        binding.buttonSend.setOnClickListener {
            // Upload
            // Lalu ambil data
            // pindah activity ke detail
            // dan tampilkan data yang tadi sudah diambil
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        deleteImage()
        return super.onSupportNavigateUp()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // when back pressed also delete image
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            deleteImage()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun deleteImage() {
        // Get file from Uri
        val photoFile = File(uri.path)

        photoFile.delete()

        // scan so other app know this photo has deleted
        MediaScannerConnection.scanFile(
                this,
                arrayOf(photoFile.absolutePath),
                null,
                null
        )

        // then close this activity and back to camera
        finish()
    }

    companion object {
        private const val TAG = "CheckingPreviewActivity"
        const val EXTRA_IMAGE_PREVIEW = "extra_image_preview"
    }
}