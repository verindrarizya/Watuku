package com.capstone.watuku.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.watuku.R
import com.capstone.watuku.databinding.ActivityDetailBinding
import com.capstone.watuku.model.WatuResource

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setWatuData()
        populateDetailActivity()
        setContentView(binding.root)
        setupActionBar()
    }

    private fun setWatuData() {
        val watu = intent.getParcelableExtra<WatuResource>(EXTRA_WATU_DETAIL)
        watu?.let { viewModel.setDataDummy(it) }
    }

    private fun populateDetailActivity() {
        binding.apply {
            viewModel.getDataDummy().apply {

                imgUri?.let {
                    loadImage(it.toUri(), binding.imageBg)
                    loadImage(it.toUri(), binding.imagePoster)
                }

                tvTitle.text = title
                tvDevinition.text = definition
                tvUsage.text = usage
            }
        }
    }

    private fun loadImage(imgUri: Uri,view: ImageView) {
        Glide.with(this@DetailActivity)
            .load(imgUri)
            .apply(RequestOptions())
            .into(view)
    }

    /**
     * For App Bar Menu
     */

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        binding.appBar.bringToFront()
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_share) {
            shareData()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun shareData() {
        viewModel.getDataDummy().apply {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT, """
                $title
                $definition
                $usage
            """.trimIndent()
                )
            }

            startActivity(Intent.createChooser(shareIntent, null))
        }
    }

    companion object {
        const val EXTRA_WATU_DETAIL = "extra_watu_detail"
    }

}

