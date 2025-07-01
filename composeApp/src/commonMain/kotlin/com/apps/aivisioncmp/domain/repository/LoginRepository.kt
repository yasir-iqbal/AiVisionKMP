package com.apps.aivisioncmp.domain.repository

interface LoginRepository {

    suspend fun loginWithToken(token:String):Boolean
    suspend fun loginWithEmail(email:String,pass:String):Boolean
}