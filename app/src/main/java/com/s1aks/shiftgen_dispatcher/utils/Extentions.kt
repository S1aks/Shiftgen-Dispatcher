package com.s1aks.shiftgen_dispatcher.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException

fun <T> T?.logd(): T? = this.also { Log.d("***", it.toString()) } // todo REMOVE

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

fun ResponseState.Error.toastError(context: Context) {
    Toast.makeText(context, this.error.localizedMessage, Toast.LENGTH_SHORT).show()
}

fun NavGraphBuilder.idComposable(
    route: String,
    content: @Composable (id: Int) -> Unit
) {
    composable(
        route = route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        content(id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0)
    }
}

@Composable
fun String.setOutlinedTextFieldBorderColor(): Color =
    if (this.isEmpty()) colors.error.copy(ContentAlpha.high)
    else colors.onSurface.copy(ContentAlpha.disabled)

fun Boolean.setOutlinedTextFieldIconByExpanded(): ImageVector =
    if (this) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

@Composable
fun Color.setOutlinedTextFieldColorsWithThis() = TextFieldDefaults.outlinedTextFieldColors(
    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
    backgroundColor = Color.Transparent,
    disabledBorderColor = this,
    disabledLabelColor = colors.onSurface.copy(ContentAlpha.medium),
)

operator fun Boolean.inc() = !this