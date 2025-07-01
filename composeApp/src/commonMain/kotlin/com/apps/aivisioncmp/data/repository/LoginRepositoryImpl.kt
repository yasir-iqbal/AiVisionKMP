package com.apps.aivisioncmp.data.repository

import com.apps.aivisioncmp.domain.repository.LoginRepository
import kotlinx.coroutines.delay

class LoginRepositoryImpl: LoginRepository {
    override suspend fun loginWithToken(token: String): Boolean {
        delay(1000)
        return false
    }

    override suspend fun loginWithEmail(email:String,pass:String): Boolean {
        delay(1000)
        return true
    }
}