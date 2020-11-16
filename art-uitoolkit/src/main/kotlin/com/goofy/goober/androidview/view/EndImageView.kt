package com.goofy.goober.androidview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import coil.load
import com.goofy.goober.databinding.EndImageViewBinding
import com.goofy.goober.model.ImageLoadState

class EndImageView(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val binding = EndImageViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setProps(onRefreshClick: OnClickListener) {
        binding.refresh.setOnClickListener(onRefreshClick)
    }

    fun setState(state: ImageLoadState?) {
        isVisible = state != null
        state ?: return

        binding.image.isVisible = state.imageUrl != null
        binding.image.load(state.imageUrl) {
            listener(
                onStart = { binding.loading.isVisible = true },
                onError = { _, _ -> binding.loading.isVisible = false },
                onSuccess = { _,_ -> binding.loading.isVisible = false}
            )
            crossfade(enable = true)
        }
        binding.loading.isVisible = state.isLoading
        binding.title.isVisible = state.title.isNotBlank()
        binding.title.text = state.title
    }
}
