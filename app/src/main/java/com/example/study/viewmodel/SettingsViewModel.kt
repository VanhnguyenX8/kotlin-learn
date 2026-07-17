package com.example.study.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.study.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repo: SettingsRepository) : ViewModel() {
    /// scope; coroutine này sẽ theo vòng đời của SettingsViewModel
    /// neu SettingsViewModel bi huy thi coroutine cung bi huy theo
    /// voi started khi nguoi dung quay man hinh thi no van giu lai coroutine
    /// trong 5s khong can phai doc lai dataStore tu dau, con khong thi se hong
    /// vi day goi IO nen gia tri ban dau can duoc cap
    /// nen initialValue moi can
    /// dung stateIn de cho no nam trong Ram cua viewModel
    /// sau nay ung dung chi can doc 1 lan ma khong can can thiep IO
    val darkMode = repo.darkMoedFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val username = repo.userNameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )
    val openCount = repo.openCountFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch { repo.setDarkMode(enabled) }
    }

    fun updateUsername(name: String) {
        viewModelScope.launch { repo.setUserName(name) }
    }

    fun onAppOpened() {
        viewModelScope.launch { repo.incrementOpenCout() }
    }

}
/// ViewModel ma co tham so thi can Factory quan ly View model
/// se khong luu lai data khi xoay man hinh
class SettingViewModelFactory(private  val repo: SettingsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SettingsViewModel(repo) as T
    }
}