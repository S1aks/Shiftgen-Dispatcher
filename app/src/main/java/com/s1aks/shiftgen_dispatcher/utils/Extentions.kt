package com.s1aks.shiftgen_dispatcher.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException

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
        } catch (exception: UnknownHostException) {
            flow.emit(ResponseState.Error(RuntimeException("Ошибка соединения с сервером!")))
        } catch (exception: ConnectException) {
            flow.emit(ResponseState.Error(RuntimeException("Ошибка соединения с сервером!")))
        } catch (exception: Throwable) {
            if (exception is CancellationException) {
                throw exception
            }
            flow.emit(ResponseState.Error(exception))
        } finally {
            delay(50)
            flow.emit(ResponseState.Idle)
        }
    }
}

fun <T> ResponseState<T>.onSuccess(
    context: Context,
    loadingState: (Boolean) -> Unit,
    successBlock: () -> Unit
) {
    when (this) {
        ResponseState.Idle -> loadingState(false)

        ResponseState.Loading -> loadingState(true)

        is ResponseState.Success -> successBlock()

        is ResponseState.Error -> toastError(context = context)
    }
}

fun <T> T?.logd(): T? = this.also { Log.d("***", it.toString()) }