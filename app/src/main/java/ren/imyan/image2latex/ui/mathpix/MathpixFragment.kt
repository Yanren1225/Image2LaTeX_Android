package ren.imyan.image2latex.ui.mathpix

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ren.imyan.base.ActivityCollector.currActivity
import ren.imyan.base.BaseFragment
import ren.imyan.image2latex.R
import ren.imyan.image2latex.core.CropImage
import ren.imyan.image2latex.core.CropImageResult
import ren.imyan.image2latex.databinding.FragmentMathpixBinding
import ren.imyan.image2latex.ui.main.MainActivity
import ren.imyan.image2latex.util.dp

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 19:46
 * @website https://imyan.ren
 */
class MathpixFragment : BaseFragment<FragmentMathpixBinding, MathpixViewModel>() {

    private val shortAnimationDuration by lazy {
        context?.resources?.getInteger(android.R.integer.config_longAnimTime)?.toLong()
    }

    private val toolbarSize by lazy {
        (requireActivity() as MainActivity).binding.layoutToolbar.toolbar.y
    }

    private val fadeAnim by lazy {
        MaterialFade().apply {
            duration = shortAnimationDuration!!
        }
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
                showIsCropImageDialog(cropImage, it)
            }
        }

        binding.selectImageCard.setOnClickListener {
            selectImage.launch("image/*")
        }

        binding.removeImage.setOnClickListener {
            binding.toSelectImageCard()
            binding.hideResultCard()
        }

        binding.processImage.setOnClickListener {
//            viewModel.getMathpixData()
            binding.showResultCard()
        }
    }

    override fun loadDate() {
        viewModel.bitmap.observe(this) {
            binding.apply {
                showImage.setImageBitmap(it)
//                if (it.height >= 500) {
//                    showImage.maxHeight = 500
//                }
                toShowImageCard()
            }
        }

        viewModel.mathpixData.data.observe(this) {
            Log.d("mathpix_data", it.toString())
        }

        viewModel.mathpixData.state.observe(this) {
            Log.d("mathpix_state", it)
        }
    }

    private fun showIsCropImageDialog(
        cropImage: ActivityResultLauncher<CropImageResult>,
        uri: Uri
    ) {
        MaterialAlertDialogBuilder(currActivity)
            .setTitle(R.string.crop_dialog_title)
            .setNegativeButton(R.string.ok) { _, _ ->
                cropImage.launch(CropImageResult(uri))
            }
            .setNeutralButton(R.string.cancel) { _, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = uri.getBitmap()
                    viewModel.bitmap.value = bitmap
                }
            }
            .create().show()
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

        this.showImageCard.isVisible = true
        this.selectImageCard.isVisible = false

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
        this.selectImageCard.isVisible = true
        this.showImageCard.isVisible = false

        TransitionManager.beginDelayedTransition(this.showImageButtonLayout, MaterialFadeThrough())
        this.showImageButtonLayout.isVisible = false
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentMathpixBinding.showResultCard() {

        binding.resultText.text = """
            \( \lim _{x \rightarrow 3}\left(\frac{x^{2}+9}{x-3}\right) \)
        """.trimIndent()
        binding.resultLatex.setText("$$\\lim _{x \\rightarrow 3}\\left(\\frac{x^{2}+9}{x-3}\\right)$$")

        TransitionManager.beginDelayedTransition(showResultTextCard, MaterialFade().apply {
            duration = 500
        })
        showResultTextCard.isVisible = true

        TransitionManager.beginDelayedTransition(showResultLatexCard, MaterialFade().apply {
            duration = 500
        })
        showResultLatexCard.isVisible = true

    }


    private fun FragmentMathpixBinding.hideResultCard() {


        TransitionManager.beginDelayedTransition(this.showResultTextCard, fadeAnim)
        showResultTextCard.isVisible = false

        TransitionManager.beginDelayedTransition(showResultLatexCard, fadeAnim)
        showResultLatexCard.isVisible = false
    }
}