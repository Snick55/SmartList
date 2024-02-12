package com.snick55.smartlist.members.presentation

import androidx.appcompat.app.AlertDialog
import com.snick55.smartlist.R


object AddMemberDialog {

    fun confirm(builder: AlertDialog.Builder, block: () -> Unit){
        val dialog = builder.create()
        dialog.setTitle(R.string.confirm_add_member)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Да"){ _, _ ->
            block.invoke()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Отмена"){ _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

}