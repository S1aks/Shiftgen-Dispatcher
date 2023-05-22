package com.s1aks.shiftgen_dispatcher.utils

import android.content.Context
import android.widget.Toast
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun ResponseState.Error.toastError(context: Context) {
    Toast.makeText(context, this.error.localizedMessage, Toast.LENGTH_SHORT).show()
}

fun <T> CoroutineScope.setFlow(
    flow: MutableStateFlow<ResponseState<T>>,
    block: suspend () -> ResponseState<T>
) {
    launch {
        flow.emit(ResponseState.Loading)
        try {
            flow.emit(block())
        } catch (exception: RuntimeException) {
            if (exception is CancellationException) {
                throw exception
            }
            flow.emit(ResponseState.Error(exception))
        } finally {
            delay(200)
            flow.emit(ResponseState.Idle)
        }
    }
}