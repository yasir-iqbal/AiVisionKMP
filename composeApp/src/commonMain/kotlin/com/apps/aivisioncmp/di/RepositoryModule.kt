package com.apps.aivisioncmp.di

import com.apps.aivisioncmp.data.repository.ChatRepositoryImpl
import com.apps.aivisioncmp.data.repository.ConversationRepositoryImpl
import com.apps.aivisioncmp.data.repository.LocalResourceRepositoryImpl
import com.apps.aivisioncmp.data.repository.LoginRepositoryImpl
import com.apps.aivisioncmp.data.repository.MessageRepositoryImpl
import com.apps.aivisioncmp.domain.repository.ChatRepository
import com.apps.aivisioncmp.domain.repository.ConversationRepository
import com.apps.aivisioncmp.domain.repository.LocalResourceRepository
import com.apps.aivisioncmp.domain.repository.LoginRepository
import com.apps.aivisioncmp.domain.repository.MessageRepository
import org.koin.dsl.module

val repositoryModule = module {
//Repository
    single<LoginRepository> { LoginRepositoryImpl() }
    single<ConversationRepository> { ConversationRepositoryImpl(get())  }
    single<MessageRepository> { MessageRepositoryImpl(get())  }
    single<ChatRepository> { ChatRepositoryImpl(get())  }
    single<LocalResourceRepository> { LocalResourceRepositoryImpl()  }
}