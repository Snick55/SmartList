package com.snick55.smartlist.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.snick55.smartlist.databinding.ResultViewBinding
import com.snick55.smartlist.lists.domain.EmptyListsException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
            if (exception is EmptyListsException){
                binding.tryAgainButton.visibility = View.INVISIBLE
            }
            binding.resultErrorTextView.text = exception.message
        }
        children.forEach {
            if (it != binding.root && it !is FloatingActionButton ) {
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

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveFlow: Flow<Container<T>>,
    onSuccess: (T) -> Unit
){
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.CREATED){
            liveFlow.collect {
                container = it
                if (it is Container.Success) {
                    onSuccess(it.unwrap())
                }
            }
        }
    }
}

