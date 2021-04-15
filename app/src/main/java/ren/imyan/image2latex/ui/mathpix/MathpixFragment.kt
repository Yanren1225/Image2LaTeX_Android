package ren.imyan.image2latex.ui.mathpix

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ren.imyan.base.BaseFragment
import ren.imyan.image2latex.core.CropImage
import ren.imyan.image2latex.core.CropImageResult
import ren.imyan.image2latex.databinding.FragmentMathpixBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 19:46
 * @website https://imyan.ren
 */
class MathpixFragment : BaseFragment<FragmentMathpixBinding, MathpixViewModel>() {

    private val shortAnimationDuration by lazy {
        context?.resources?.getInteger(android.R.integer.config_shortAnimTime)
    }

    override fun initViewModel(): MathpixViewModel =
        ViewModelProvider(this)[MathpixViewModel::class.java]

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMathpixBinding = FragmentMathpixBinding.inflate(inflater, container, false)

    override fun initView() {

        val cropImage =
            registerForActivityResult(CropImage()) {
                it.data?.data?.let { cropImageUri ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val bitmap = cropImageUri.getBitmap()
                        viewModel.bitmap.value = bitmap
                    }
                }
            }

        val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                cropImage.launch(CropImageResult(it))
            }
        }

        binding.selectImageCard.setOnClickListener {
            selectImage.launch("image/*")
        }

        binding.removeImage.setOnClickListener {
            binding.toSelectImageCard()
        }
    }

    override fun loadDate() {
        viewModel.bitmap.observe(this){
            binding.apply {
                showImage.setImageBitmap(it)
                toShowImageCard()
            }
        }
    }

    private suspend fun Uri.getBitmap(): Bitmap = withContext(Dispatchers.Default) {
        val bitmap = runCatching {
            BitmapFactory.decodeStream(
                context?.contentResolver?.openInputStream(
                    this@getBitmap
                )
            )
        }
        return@withContext bitmap.getOrThrow()
    }

    private fun FragmentMathpixBinding.toShowImageCard() {

        TransitionManager.beginDelayedTransition(
            this.selectImageCard as ViewGroup,
            MaterialContainerTransform().apply {
                startView = this@toShowImageCard.selectImageCard
                endView = this@toShowImageCard.showImageCard
                interpolator = DecelerateInterpolator()
                addTarget(endView as MaterialCardView)
                setPathMotion(MaterialArcMotion())
                scrimColor = Color.TRANSPARENT
            })

        this.showImageCard.visibility = View.VISIBLE
        this.selectImageCard.visibility = View.GONE

        TransitionManager.beginDelayedTransition(this.showImageButtonLayout, MaterialFadeThrough())
        this.showImageButtonLayout.visibility = View.VISIBLE
    }

    private fun FragmentMathpixBinding.toSelectImageCard() {
        TransitionManager.beginDelayedTransition(
            this.showImageCard as ViewGroup,
            MaterialContainerTransform().apply {
                startView = this@toSelectImageCard.showImageCard
                endView = this@toSelectImageCard.selectImageCard
                addTarget(endView as MaterialCardView)
                setPathMotion(MaterialArcMotion())
                scrimColor = Color.TRANSPARENT
            })
        this.selectImageCard.visibility = View.VISIBLE
        this.showImageCard.visibility = View.GONE

        TransitionManager.beginDelayedTransition(this.showImageButtonLayout, MaterialFadeThrough())
        this.showImageButtonLayout.visibility = View.GONE
    }
}