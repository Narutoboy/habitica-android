package com.habitrpg.android.habitica.ui.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.habitrpg.android.habitica.R
import com.habitrpg.android.habitica.models.responses.TaskDirection
import com.habitrpg.android.habitica.models.tasks.Task
import com.habitrpg.android.habitica.ui.helpers.bindView

class HabitViewHolder(itemView: View, scoreTaskFunc: ((Task, TaskDirection) -> Unit), openTaskFunc: ((Task) -> Unit)) : BaseTaskViewHolder(itemView, scoreTaskFunc, openTaskFunc) {

    private val btnPlusWrapper: FrameLayout by bindView(itemView, R.id.btnPlusWrapper)
    private val btnPlusIconView: ImageView by bindView(itemView, R.id.btnPlusIconView)
    private val btnPlus: Button by bindView(itemView, R.id.btnPlus)
    private val btnMinusWrapper: FrameLayout by bindView(itemView, R.id.btnMinusWrapper)
    private val btnMinusIconView: ImageView by bindView(itemView, R.id.btnMinusIconView)
    private val btnMinus: Button by bindView(itemView, R.id.btnMinus)
    private val streakTextView: TextView by bindView(itemView, R.id.streakTextView)

    override val taskIconWrapperIsVisible: Boolean
        get() {
            var isVisible: Boolean = super.taskIconWrapperIsVisible
            if (this.streakTextView.visibility == View.VISIBLE) {
                isVisible = true
            }
            return isVisible
        }

    init {
        btnPlus.setOnClickListener { onPlusButtonClicked() }
        btnMinus.setOnClickListener { onMinusButtonClicked() }
    }

    override fun bind(data: Task, position: Int, displayMode: String) {
        this.task = data
        if (data.up == true) {
            this.btnPlusWrapper.setBackgroundResource(data.lightTaskColor)
            if (data.lightTaskColor == R.color.yellow_100) {
                this.btnPlusIconView.setImageResource(R.drawable.habit_plus_yellow)
            } else {
                this.btnPlusIconView.setImageResource(R.drawable.habit_plus)
            }
            this.btnPlus.visibility = View.VISIBLE
            this.btnPlus.isClickable = true
        } else {
            this.btnPlusWrapper.setBackgroundResource(R.color.habit_inactive_gray)
            this.btnPlusIconView.setImageResource(R.drawable.habit_plus_disabled)
            this.btnPlus.visibility = View.GONE
            this.btnPlus.isClickable = false
        }

        if (data.down == true) {
            this.btnMinusWrapper.setBackgroundResource(data.lightTaskColor)
            if (data.lightTaskColor == R.color.yellow_100) {
                this.btnMinusIconView.setImageResource(R.drawable.habit_minus_yellow)
            } else {
                this.btnMinusIconView.setImageResource(R.drawable.habit_minus)
            }
            this.btnMinus.visibility = View.VISIBLE
            this.btnMinus.isClickable = true
        } else {
            this.btnMinusWrapper.setBackgroundResource(R.color.habit_inactive_gray)
            this.btnMinusIconView.setImageResource(R.drawable.habit_minus_disabled)
            this.btnMinus.visibility = View.GONE
            this.btnMinus.isClickable = false
        }

        var streakString = ""
        if (data.counterUp != null && data.counterUp ?: 0 > 0 && data.counterDown != null && data.counterDown ?: 0 > 0) {
            streakString = streakString + "+" + data.counterUp.toString() + " | -" + data.counterDown?.toString()
        } else if (data.counterUp != null && data.counterUp ?: 0 > 0) {
            streakString = streakString + "+" + data.counterUp.toString()
        } else if (data.counterDown != null && data.counterDown ?: 0 > 0) {
            streakString = streakString + "-" + data.counterDown.toString()
        }
        if (streakString.isNotEmpty()) {
            streakTextView.text = streakString
            streakTextView.visibility = View.VISIBLE
        } else {
            streakTextView.visibility = View.GONE
        }
        super.bind(data, position, displayMode)
    }

    private fun onPlusButtonClicked() {
        task?.let { scoreTaskFunc.invoke(it, TaskDirection.UP) }
    }

    private fun onMinusButtonClicked() {
        task?.let { scoreTaskFunc.invoke(it, TaskDirection.DOWN) }
    }

    override fun setDisabled(openTaskDisabled: Boolean, taskActionsDisabled: Boolean) {
        super.setDisabled(openTaskDisabled, taskActionsDisabled)

        this.btnPlus.isEnabled = !taskActionsDisabled
        this.btnMinus.isEnabled = !taskActionsDisabled
    }
}
