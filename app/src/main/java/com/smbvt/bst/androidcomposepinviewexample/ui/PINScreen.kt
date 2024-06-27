package com.smbvt.bst.androidcomposepinviewexample.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smbvt.bst.androidcomposepinviewexample.R
import com.smbvt.bst.androidcomposepinviewexample.ui.composables.textfields.OutlinedOtpTextField
import com.smbvt.bst.androidcomposepinviewexample.ui.composables.texts.TextGray900BodyMediumMedium_Gray50BodyMediumMedium
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PaddingDefault16
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PaddingDefault24
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PaddingDefault8
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PaddingDefault80
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun PINScreen(
    modifier: Modifier = Modifier, uiEvents: MutableSharedFlow<PINScreenViewModel.UIEvents>,
    pin : String = "",
    rePin : String = "",
    onChangePIN : (pin : String) -> Unit = {},
    onChangeRePIN : (pin : String) -> Unit = {}
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        uiEvents.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.RESUMED
        ).onEach { value ->
            when (value) {
                is PINScreenViewModel.UIEvents.ShowToast -> {
                    Toast.makeText(context, value.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(this)
    })

    Column(
        modifier = modifier
            .padding(start = PaddingDefault16, end = PaddingDefault16)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        var focusIndex by remember {
            mutableStateOf(0)
        }

        Spacer(modifier = Modifier.height(PaddingDefault80))

        TextGray900BodyMediumMedium_Gray50BodyMediumMedium(text = stringResource(id = R.string.enter_new_pin))
        Spacer(modifier = Modifier.height(PaddingDefault8))

        OutlinedOtpTextField(value = pin, aspectRatio = 1f, onValueChange = {
            onChangePIN(it)
        }, requestFocus = focusIndex == 0, isPINView = true, onFilled = {
            focusIndex = 1
            Log.e("onFilled", "focusIndex : $focusIndex")

        }, onChangeFocus = {
            if (it) focusIndex = 0
        })

        Spacer(modifier = Modifier.height(PaddingDefault24))
        TextGray900BodyMediumMedium_Gray50BodyMediumMedium(text = stringResource(id = R.string.confirm_new_pin))
        Spacer(modifier = Modifier.height(PaddingDefault8))

        OutlinedOtpTextField(value = rePin, aspectRatio = 1f, onValueChange = {
            onChangeRePIN(it)
        }, requestFocus = focusIndex == 1, isPINView = true, onFilled = {

        }, onChangeFocus = {
            if (it) focusIndex = 1
        })
    }
}

@Composable
@Preview
fun PreviewPINScreen() {
    PINScreen(uiEvents = MutableSharedFlow())
}