package com.capstone.watuku.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.watuku.R
import com.capstone.watuku.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA = "watu"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setDataDummy()
        getDataDummy()
        setContentView(binding.root)
        setupActionBar()
    }

    private fun setDataDummy() {
        val watu = intent.getParcelableExtra(EXTRA) ?: WatuRecources()
        viewModel.setDataDummy(watu)
    }

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

    private fun getDataDummy() {
        binding.apply {
            viewModel.getDataDummy().apply {
                imagePoster.setImageResource(getDrawableResource(Img ?: ""))
                imageBg.setImageResource(getDrawableResource(Img ?: ""))
                tvTitle.text = title
                tvDevinition.text = definition
                tvUsage.text = usage
            }
        }
    }

    private fun getDrawableResource(drawableName: String) =
        this.resources.getIdentifier(drawableName, null, this.packageName)

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
}

