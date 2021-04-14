package ren.imyan.image2latex.ui.mathpix

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import ren.imyan.base.BaseFragment
import ren.imyan.image2latex.databinding.FragmentMathpixBinding

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-14 19:46
 * @website https://imyan.ren
 */
class MathpixFragment : BaseFragment<FragmentMathpixBinding, MathpixViewModel>() {

    private val shortAnimationDuration by lazy{
        context?.resources?.getInteger(android.R.integer.config_shortAnimTime)
    }

    override fun initViewModel(): MathpixViewModel =
        ViewModelProvider(this)[MathpixViewModel::class.java]

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMathpixBinding = FragmentMathpixBinding.inflate(inflater, container, false)

    override fun initView() {

        val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            val bitmap = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(it))
            binding.apply {

                selectImageCard.visibility = View.GONE

                showImageCard.apply {
                    visibility = View.VISIBLE
                }
                showImage.setImageBitmap(bitmap)
            }
        }

        binding.selectImageCard.setOnClickListener {
            selectImage.launch("image/*")
        }

        binding.removeImage.setOnClickListener {
            binding.apply {
                showImageCard.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration!!.toLong())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            selectImageCard.visibility = View.GONE
                        }
                    })

                selectImageCard.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate()
                        .alpha(1f)
                        .setDuration(shortAnimationDuration!!.toLong())
                        .setListener(null)
                }
            }
        }
    }

    override fun loadDate() {
    }
}