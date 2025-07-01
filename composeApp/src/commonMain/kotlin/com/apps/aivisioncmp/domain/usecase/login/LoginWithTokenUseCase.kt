package com.apps.aivisioncmp.domain.usecase.login

import com.apps.aivisioncmp.domain.repository.LoginRepository

class LoginWithTokenUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(token: String) = loginRepository.loginWithToken(token)

}