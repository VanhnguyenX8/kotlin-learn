package com.example.study.scene.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.study.repository.SettingsRepository
import com.example.study.viewmodel.SettingViewModelFactory
import com.example.study.viewmodel.SettingsViewModel

class MainCompose : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen(viewModel)
        }
        val repo = SettingsRepository(applicationContext)
        val viewModel: SettingsViewModel by viewModels {
            SettingViewModelFactory(repo)
        }
        viewModel.onAppOpened()


    }
}

fun SettingsScreen(viewModel: SettingsViewModel) {
    val darkMode by viewModel.darkMode.collectAsState()
    val username by viewModel.username.collectAsState()
    val openCount by viewModel.openCount.collectAsState()

    var nameInput by remember { mutableStateOf(username) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Số lần mở app: $openCount")

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark mode")
            Switch(
                checked = darkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) }
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Username") }
        )
        Button(onClick = { viewModel.updateUsername(nameInput) }) {
            Text("Lưu username: hiện tại = $username")
        }
    }
}