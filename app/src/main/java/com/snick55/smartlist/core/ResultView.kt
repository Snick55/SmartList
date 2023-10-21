package com.snick55.smartlist.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.snick55.smartlist.databinding.ResultViewBinding

class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    var container: Container<*> = Container.Pending
        set(value) {
            field = value
            notifyUpdates()
        }

    private var tryAgainListener: (() -> Unit)? = null

    private val binding: ResultViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ResultViewBinding.inflate(inflater, this, false)
        addView(binding.root)

        if (isInEditMode) {
            container = Container.Success("")
        } else {
            binding.resultProgressBar.isVisible = false
            binding.resultErrorContainer.isVisible = false
            binding.internalResultContainer.isVisible = false
            children.forEach {
                it.isVisible = false
            }
            container = Container.Pending
        }

        binding.tryAgainButton.setOnClickListener {
            tryAgainListener?.invoke()

        }
    }

    fun setTryAgainListener(onTryAgain: () -> Unit) {
        this.tryAgainListener = onTryAgain
    }

    private fun notifyUpdates() {
        val container = this.container
        binding.resultProgressBar.isVisible = container is Container.Pending
        binding.resultErrorContainer.isVisible = container is Container.Error
        binding.internalResultContainer.isVisible = container !is Container.Success

        if (container is Container.Error) {
            val exception = container.exception
            binding.resultErrorTextView.text = exception.message
        }
        children.forEach {
            if (it != binding.root) {
                it.isVisible = container is Container.Success
            }
        }
    }
}

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveValue: LiveData<Container<T>>,
    onSuccess: (T) -> Unit
) {
    liveValue.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.unwrap())
        }
    }
}