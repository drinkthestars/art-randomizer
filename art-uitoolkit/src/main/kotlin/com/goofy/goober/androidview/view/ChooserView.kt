package com.goofy.goober.androidview.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.goofy.goober.R
import com.goofy.goober.model.QuestionsState

class ChooserView(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    fun setState(state: State?) {
        removeAllViewsInLayout()
        isVisible = state?.questionsState != null
        val questionsState = state?.questionsState ?: return
        questionsState.question.options.values.forEach { option ->
            Button(context).apply {
                text = option
                isAllCaps = false
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.next_button_text_size)
                )
                setOnClickListener { state.clickListener(questionsState, option) }
            }.run {
                addView(this)
                updateLayoutParams<MarginLayoutParams> {
                    bottomMargin = resources.getDimension(R.dimen.next_button_margin).toInt()
                }
            }
        }
    }

    data class State(
        val questionsState: QuestionsState?,
        val clickListener: (questionsState: QuestionsState, choice: String) -> Unit
    )
}
