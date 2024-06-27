package com.smbvt.bst.androidcomposepinviewexample.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smbvt.bst.androidcomposepinviewexample.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PINScreenViewModel @Inject constructor(@ApplicationContext context: Context) :
    AndroidViewModel(context as Application) {

    private val applicationContext = getApplication<Application>()

    var pin by mutableStateOf("")
    var rePin by mutableStateOf("")

    sealed class UIEvents {
        data class ShowToast(val message: String) : UIEvents()
    }

    init {
        viewModelScope.launch {
            delay(5000)
            viewModelScope.launch {
                if(pin.isEmpty() && rePin.isEmpty()) {
                    uiEvents.emit(UIEvents.ShowToast(applicationContext.getString(R.string.please_enter_pin)))
                }
            }
        }
    }

    val uiEvents = MutableSharedFlow<UIEvents>()

    fun onChangePIN(pin : String) {
        this.pin = pin
    }

    fun onChangeRePIN(pin : String){
        this.rePin = pin
    }
}