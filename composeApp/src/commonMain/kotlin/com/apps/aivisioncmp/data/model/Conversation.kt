package com.apps.aivisioncmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Conversation( val id: Long=0,
                         val title: String = "",
                         val type: String = "",
                         val content:String="",
                         val createdAt: String ="")
