package com.goofy.goober.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.goofy.goober.R
import com.goofy.goober.model.Question
import com.goofy.goober.ui.state.BackButtonPressHandler

class QuestionView(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    fun setState(state: State?) {
        state ?: return

        removeAllViewsInLayout()

        state.question.options.values.forEach { option ->
            Button(context).apply {
                text = option
                isAllCaps = false
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.next_button_text_size)
                )
                setOnClickListener { state.clickListener(option) }
            }.run {
                addView(this)
                updateLayoutParams<MarginLayoutParams> {
                    bottomMargin = resources.getDimension(R.dimen.next_button_margin).toInt()
                }
            }
        }
    }

    data class State(
        val question: Question,
        val clickListener: (String) -> Unit,
        val backButtonPressHandler: BackButtonPressHandler? = null
    )
}
