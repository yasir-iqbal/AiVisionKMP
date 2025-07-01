package com.apps.aivisioncmp.domain.usecase.login

import com.apps.aivisioncmp.domain.repository.LoginRepository

class LoginWithEmailUseCase (private val loginRepository: LoginRepository) {

    suspend operator fun invoke(email: String,pass:String) = loginRepository.loginWithEmail(email,pass)

}