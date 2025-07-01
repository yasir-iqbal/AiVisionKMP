package com.apps.aivisioncmp.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.domain.usecase.conversations.CreateConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.DeleteAllConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.DeleteConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.GetAllConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.SearchConversationUseCase
import com.apps.aivisioncmp.domain.usecase.conversations.UpdateConversationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val createConversationUseCase: CreateConversationUseCase,
    private val getAllConversationUseCase: GetAllConversationUseCase,
    private val searchConversationUseCase: SearchConversationUseCase,
    private val deleteConversationUseCase: DeleteConversationUseCase,
    private val deleteAllConversationUseCase: DeleteAllConversationUseCase,
    private val updateConversationUseCase: UpdateConversationUseCase,
): ViewModel() {

    private val _conversations: MutableStateFlow<List<Conversation>> = MutableStateFlow(
        emptyList()
    )
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        true
    )

    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _darkMode = MutableStateFlow(true)
    val darkMode get() = _darkMode.asStateFlow()

    private var searchJob: Job? = null
    // val currentLanguageCode = mutableStateOf("en")

    fun loadThemeMode() = viewModelScope.launch {
        _darkMode.value = false
    }

    fun getAllConversations() = viewModelScope.launch {
        _isLoading.value = true
        _conversations.value = getAllConversationUseCase()
        _isLoading.value = false
    }

    fun deleteConversationById(id: Long) = viewModelScope.launch {
       deleteConversationUseCase(id)
        _conversations.value = getAllConversationUseCase()

    }

    fun clearConversations() = viewModelScope.launch {
        deleteAllConversationUseCase()
        _conversations.value = emptyList()

    }
    fun searchConversation(query:String){
        searchJob?.cancel()
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            _conversations.value = searchConversationUseCase(query)
        }
    }
    fun resetSearch(){
        searchJob?.cancel()
        viewModelScope.launch {
            _conversations.value =getAllConversationUseCase()
        }
    }


    fun getCurrentLanguageCode() = "en"
}