package com.apps.aivisioncmp.ui.welcome

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivisioncmp.domain.usecase.login.LoginWithEmailUseCase
import com.apps.aivisioncmp.domain.usecase.login.LoginWithTokenUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeScreenViewModel(private val loginWithEmailUseCase: LoginWithEmailUseCase, private val loginWithTokenUseCase: LoginWithTokenUseCase): ViewModel() {

    private var _isProcessing = mutableStateOf(false)
    val isProcessing:Boolean
        get() = _isProcessing.value
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    private var _authError = mutableStateOf(false)
    val authError:Boolean get() = _authError.value

    fun updateProcessingState(isProcessing:Boolean)
    {
        _isProcessing.value = isProcessing
    }

    fun authenticateWithToken(token:String) = viewModelScope.launch {

        val authResult = loginWithTokenUseCase(token)
        if (authResult)
        {
            _isProcessing.value =false
            _loginSuccess.value = true
            delay(400) // let the config load first

        }else{
            _authError.value = true
            _isProcessing.value =false
        }

    }

    fun loginWithEmailAndPass() = viewModelScope.launch {


        val authResult = loginWithEmailUseCase("","")

        if (authResult)
        {
            _isProcessing.value =false
            _loginSuccess.value = true
            delay(400) // let the config load first
        }else{
            _authError.value = true
            _isProcessing.value =false
        }

    }

}