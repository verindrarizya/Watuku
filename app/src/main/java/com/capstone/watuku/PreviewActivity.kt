package com.capstone.watuku

import android.content.Intent
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
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.watuku.databinding.ActivityPreviewBinding
import com.capstone.watuku.detail.DetailActivity
import com.capstone.watuku.detail.DetailActivity.Companion.EXTRA_WATU_DETAIL
import com.capstone.watuku.ml.MineralModel
import com.capstone.watuku.model.WatuResource
import com.capstone.watuku.util.DataDummy.listData
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class PreviewActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var uri: Uri
    private var codeFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppBar()

        val data = intent.extras
        if (data != null) {
            uri = Uri.parse(data.getString(EXTRA_IMAGE_PREVIEW))
            codeFlag = data.getInt(EXTRA_FLAG)
        }
        Log.d(TAG, "Get Uri from intent: $uri")
        Log.d(TAG, "FLAG VALUE: $codeFlag")

        loadImage()

        // Set listener for the button
        binding.buttonCancel.setOnClickListener(this)
        binding.buttonSend.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.buttonCancel.id -> {
                deleteImage()
                finish()
            }

            binding.buttonSend.id -> {
                Toast.makeText(this, "Processing", Toast.LENGTH_SHORT).show()
                val index = getIndexLabelFromTfLite()

                val watuDetail = listData[index]
                watuDetail.imgUri = uri.toString()

                moveToDetailActivity(watuDetail)
            }
        }
    }

    private fun moveToDetailActivity(data: WatuResource) {
        val intent = Intent(this, DetailActivity::class.java)
         intent.putExtra(EXTRA_WATU_DETAIL, data)

        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        deleteImage()
        finish()
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

        // If flagCode is Gallery then don;t delete, so folder in gallery not deleted
        if (codeFlag == FLAG_GALLERY) return

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
        Log.d(TAG, "Index: $maxIndex")

        // Close model to prevent memory leaks
        tfModel.close()

        return maxIndex
    }
    private fun getBitmap(uri: Uri) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri)).copy(Bitmap.Config.ARGB_8888, true)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }

    companion object {
        private const val TAG = "CheckingPreviewActivity"

        const val EXTRA_IMAGE_PREVIEW = "extra_image_preview"
        const val EXTRA_FLAG = "extra_flag"

        const val FLAG_CAMERA = 20
        const val FLAG_GALLERY = 21
    }
}