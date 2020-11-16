package com.goofy.goober.androidview.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import coil.load
import com.goofy.goober.R
import com.goofy.goober.databinding.EndImageViewBinding
import com.goofy.goober.model.ImageLoadState
import com.goofy.goober.model.Question

class EndImageView(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val binding = EndImageViewBinding.inflate(LayoutInflater.from(context), this, true)
//
//    init {
//        orientation = VERTICAL
//    }

    fun setState(state: ImageLoadState?) {
        isVisible = state != null
        state ?: return
        binding.image.isVisible = state.imageUrl != null
        binding.image.load(state.imageUrl) { crossfade(enable = true) }
        binding.loading.isVisible = state.isLoading
        binding.title.isVisible = state.title.isNotBlank()
        binding.title.text = state.title
    }
}
