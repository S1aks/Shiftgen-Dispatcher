package com.s1aks.shiftgen_dispatcher.utils

import android.content.Context
import android.widget.Toast
import com.s1aks.shiftgen_dispatcher.data.ResponseState

fun ResponseState.Error.toastError(context: Context) {
    Toast.makeText(context, this.error.localizedMessage, Toast.LENGTH_SHORT).show()
}