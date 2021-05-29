package com.capstone.watuku

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.watuku.databinding.ActivityPreviewBinding
import com.capstone.watuku.ml.MineralModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
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
            Toast.makeText(this, "Processing", Toast.LENGTH_SHORT).show()
            val index = getIndexLabelFromTfLite()
            val label = getLabel(index)
            Log.d(TAG, "Label: $label")
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

    private fun getIndexLabelFromTfLite(): Int {
        val bitmap = getBitmap(uri)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        val tfModel = MineralModel.newInstance(this)
        val tfBuffer = TensorImage.fromBitmap(resizedBitmap)
        val byteBuffer = tfBuffer.buffer
        Log.d(TAG, "ByteBuffer: $byteBuffer")

        // create input for reference
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = tfModel.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        Log.d(TAG, "outputFeature0: $outputFeature0")

        val outputArray = outputFeature0.floatArray
        outputArray.forEach {
            Log.d(TAG, "$it")
        }
        Log.d(TAG, "outputArray: $outputArray")

        val max = outputArray.maxOrNull()
        Log.d(TAG, "max: $max")
        val maxIndex = outputArray.indexOfFirst { it == max }
        Log.d(TAG, "Index: ${maxIndex.toString()}")

        // Close model to prevent memory leaks
        tfModel.close()

        return maxIndex
    }

    private fun getLabel(index: Int): String {
        val labels = application.assets.open("mineral-label.txt").bufferedReader().use {
            it.readText()
        }.split("\n")

        return labels[index]
    }

    private fun getBitmap(uri: Uri) =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }


    companion object {
        private const val TAG = "CheckingPreviewActivity"
        const val EXTRA_IMAGE_PREVIEW = "extra_image_preview"
    }
}