package com.apps.aivisioncmp.data.model

data class ChatMessage( val id: Long=0,
                       val recentChatId: Long,
                       val role: String = "",
                       val content: String = "",
                       val type: String = "",
                       val url: String = "",
                        val status:Int =0,
                        val createdAt: String ="")

