package leonov.ru.translator.view.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

import leonov.ru.translator.R
import leonov.ru.model.entity.TranslateResult
import leonov.ru.translator.databinding.ActivityDetailBinding
import leonov.ru.utils.addHttpsPrefix
import leonov.ru.utils.network.NetworkStatus
import leonov.ru.utils.sound.SoundHelper
import leonov.ru.utils.surroundBrackets
import leonov.ru.utils.ui.AlertDialogFragment

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionbarHomeButtonAsUp()
        binding.detailSwipeRefreshLayout.setOnRefreshListener{ startLoadingOrShowError() }
        setData()
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startLoadingOrShowError() {
        NetworkStatus(this).isOnline().observe(
            this@DetailActivity,
            Observer<Boolean> {
                if (it) {
                    setData()
                } else {
                    AlertDialogFragment.newInstance(
                        getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline)
                    ).show(
                        supportFragmentManager,
                        DIALOG_FRAGMENT_TAG
                    )
                    stopRefreshAnimationIfNeeded()
                }
            })
    }

    private fun setData() {

        val translateResult = intent.extras?.getParcelable<TranslateResult>(TRANSLATE_RESULT)
        translateResult?.let {transResult ->
            binding.tvDetailHeader.text = transResult.text
            binding.tvDetailTranscription.text = transResult.transcription.surroundBrackets()
            binding.tvDetailPartOfSpeech.text = transResult.partOfSpeech
            binding.tvDetailDescription.text = transResult.translation
            binding.tvDetailNote.text = transResult.note

            useGlideToLoadPhoto(binding.ivDetailPicture, transResult.imageUrl)

            binding.ivDetailSound.setOnClickListener {
                if (transResult.soundUrl.isNotEmpty())
                    SoundHelper(applicationContext).playUrl(transResult.soundUrl.addHttpsPrefix())
            }
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.detailSwipeRefreshLayout.isRefreshing) {
            binding.detailSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_no_photo_vector)
                    .centerCrop()
            )
            .into(imageView)
    }

    companion object {

        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"

        private const val TRANSLATE_RESULT = "Translate_Result"

        fun getIntent(
            context: Context,
            translateResult: TranslateResult
        ): Intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(TRANSLATE_RESULT, translateResult)
        }
    }

}