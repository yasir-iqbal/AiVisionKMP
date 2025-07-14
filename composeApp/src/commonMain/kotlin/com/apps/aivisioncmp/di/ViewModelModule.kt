package com.apps.aivisioncmp.di

import com.apps.aivisioncmp.ui.chats.ChatViewModel
import com.apps.aivisioncmp.ui.conversations.ConversationViewModel
import com.apps.aivisioncmp.ui.welcome.WelcomeScreenViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { WelcomeScreenViewModel(get(), get()) }
    single { ConversationViewModel(get(), get(),get(),get(),get(),get()) }
    factory { ChatViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(),get()) }
}