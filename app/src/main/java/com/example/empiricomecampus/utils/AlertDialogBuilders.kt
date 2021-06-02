package com.example.empiricomecampus.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AlertDialogBuilders : DialogFragment() {
    fun createDeleteAlert(context: Context, positiveClick: () -> Unit) {

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Da li ste sigurni da želite obrisati odabrani unos?")
            .setPositiveButton("Obriši") { _, _ ->
                positiveClick()
            }
            .setNegativeButton("Odustani") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Upozorenje")
        alert.show()
    }

}