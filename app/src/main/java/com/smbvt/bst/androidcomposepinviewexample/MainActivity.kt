package com.smbvt.bst.androidcomposepinviewexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.smbvt.bst.androidcomposepinviewexample.ui.PINScreen
import com.smbvt.bst.androidcomposepinviewexample.ui.PINScreenViewModel
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.AndroidComposePINViewExampleTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel: PINScreenViewModel = hiltViewModel()

            AndroidComposePINViewExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    PINScreen(
                        uiEvents = viewModel.uiEvents,
                        pin = viewModel.pin,
                        rePin = viewModel.rePin,
                        onChangePIN = viewModel::onChangePIN,
                        onChangeRePIN = viewModel::onChangeRePIN
                    )
                }
            }
        }
    }
}