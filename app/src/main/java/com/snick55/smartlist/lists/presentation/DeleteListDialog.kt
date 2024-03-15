package com.snick55.smartlist.lists.presentation

import androidx.appcompat.app.AlertDialog
import com.snick55.smartlist.R

object DeleteListDialog {

    fun confirm(builder: AlertDialog.Builder, block: () -> Unit){
        val dialog = builder.create()
        dialog.setTitle(R.string.confirm_delete_list)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Да"){ _, _ ->
            block.invoke()
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Нет"){ _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

}