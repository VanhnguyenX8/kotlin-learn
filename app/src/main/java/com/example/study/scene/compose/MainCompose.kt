package com.example.study.scene.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.study.repository.SettingsRepository
import com.example.study.viewmodel.SettingViewModelFactory
import com.example.study.viewmodel.SettingsViewModel

class MainCompose : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = SettingsRepository(applicationContext)
        val viewModel: SettingsViewModel by viewModels {
            SettingViewModelFactory(repo)
        }
        viewModel.onAppOpened()

        setContent {
            SettingsScreen(viewModel)
        }
    }
}

@Composable
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