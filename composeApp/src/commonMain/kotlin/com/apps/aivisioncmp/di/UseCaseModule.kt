package com.apps.aivisioncmp.di

import com.apps.aivisioncmp.domain.usecase.app.GetStringResourceExampleUseCase
import com.apps.aivisioncmp.domain.usecase.chat.CompleteChatUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.CreateConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.DeleteAllConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.DeleteConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.GetAllConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.SearchConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.UpdateConversationUseCase
import com.apps.aivisioncmp.domain.usecase.login.LoginWithEmailUseCase
import com.apps.aivisioncmp.domain.usecase.login.LoginWithTokenUseCase
import com.apps.aivisioncmp.domain.usecase.messages.AddMessageUseCase
import com.apps.aivisioncmp.domain.usecase.messages.DeleteMessagesUseCase
import com.apps.aivisioncmp.domain.usecase.messages.GetMessagesByLimitUseCase
import com.apps.aivisioncmp.domain.usecase.messages.GetMessagesUseCase
import com.apps.aivisioncmp.domain.usecase.messages.UpdateMessageContentUseCase
import com.apps.aivisioncmp.domain.usecase.messages.UpdateMessageStatusUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // Login
    single { LoginWithTokenUseCase(get()) }
    single { LoginWithEmailUseCase(get()) }

    // Conversation
    single { CreateConversationUseCase(get()) }
    single { GetAllConversationUseCase(get()) }
    single { UpdateConversationUseCase(get()) }
    single { SearchConversationUseCase(get()) }
    single { DeleteConversationUseCase(get()) }
    single { DeleteAllConversationUseCase(get()) }

    // Message
    single { AddMessageUseCase(get()) }
    single { DeleteMessagesUseCase(get()) }
    single { GetMessagesUseCase(get()) }
    single { GetMessagesByLimitUseCase(get())}
    single { UpdateMessageContentUseCase(get()) }
    single { UpdateMessageStatusUseCase(get()) }

    // App
    single { GetStringResourceExampleUseCase(get()) }

    // Chat
    single { CompleteChatUseCase(get()) }



}