package com.example.study.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.study.service.settingDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

class SettingsRepository (private  val context: Context){

    /// gom lai trong object Keys de sau khi can goi Keys. tranh roi rac
    private  object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val USERNAME = stringPreferencesKey("username")
        val OPEN_COUNT = intPreferencesKey("open_count")
    }

    /// can try catch voi IO vi van co nhung truong hop nhu la
    /// dien thoai het sach dung luong bo nho
    /// he dieu hanh cua android xung dot voi phan quyen doc ghi file
    /// dien thoai dot ngot bi sap nguon luc app ghi du lieu vao DataStore
    /// => file cover
    private  val safeDataStoreFlow: Flow<Preferences>  = context.settingDataStore.data.catch {
        e ->
        if(e is IOException) emit(emptyPreferences()) else throw e
    }

    val darkMoedFlow : Flow<Boolean> = context.settingDataStore.data.catch {
        e  ->
        if(e is IOException) emit(emptyPreferences()) else throw e
    }.map { prefs -> prefs[Keys.DARK_MODE] ?: false }

    val userNameFlow : Flow<String> = safeDataStoreFlow.map {
        e -> e[Keys.USERNAME] ?: ""
    }
    val openCountFlow: Flow<Int> = safeDataStoreFlow.map {
        e -> e[Keys.OPEN_COUNT] ?: 0
    }
    /// khi dung edit thi bat buoc dung suspend
    /// no la ham tam dung
    /// va o ngoai khi dung ban can boc trong coroutine
    suspend fun setDarkMode(enable: Boolean) {
        context.settingDataStore.edit {
            preferences -> preferences[Keys.DARK_MODE] = enable
        }
    }

    suspend fun  setUserName(name: String) {
        context.settingDataStore.edit {
            preferences -> preferences[Keys.USERNAME] = name
        }
    }

    suspend fun incrementOpenCout() {
        context.settingDataStore.edit {
            preferences -> preferences[Keys.OPEN_COUNT] = (preferences[Keys.OPEN_COUNT] ?: 0) + 1
        }
    }

    suspend fun clearAll() {
        context.settingDataStore.edit {
            preferences -> preferences.clear()
        }
    }

}