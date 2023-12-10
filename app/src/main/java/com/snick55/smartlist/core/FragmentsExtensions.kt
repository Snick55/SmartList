package com.snick55.smartlist.core

import android.widget.Button
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.snick55.smartlist.R
import com.snick55.smartlist.login.presentation.State


fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

fun Fragment.paintButton(flag: Boolean, button: Button){
    val color = if (flag) R.color.title_view_color else R.color.gray
    button.isEnabled = flag
    button.background.setTint(ContextCompat.getColor(button.context,color))
}


fun Fragment.fillError(input: TextInputLayout, @StringRes errorMessageRes: Int) {
    if (errorMessageRes == State.NO_ERROR_MESSAGE) {
        input.error = null
        input.isErrorEnabled = false
    } else {
        input.error = getString(errorMessageRes)
        log("${input.error}")
        input.isErrorEnabled = true
    }
}