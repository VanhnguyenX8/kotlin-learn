package com.example.study.scene.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.copy
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.study.repository.SettingsRepository
import com.example.study.viewmodel.SettingViewModelFactory
import com.example.study.viewmodel.SettingsViewModel
import java.nio.file.Files

class MainCompose : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = SettingsRepository(applicationContext)
        val viewModel: SettingsViewModel by viewModels {
            SettingViewModelFactory(repo)
        }
        viewModel.onAppOpened()

        setContent {
            FullFeaturesScreen()
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

///study full compose

/// data class nos se tu sing ra toString, equals, hascode, copy
data class SampleTask(val id: Int, val title: String, val isDone: Boolean)


/// dung material 3 o trang thai thu nghiem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullFeaturesScreen() {
    // --- 1. QUẢN LÝ STATE (TRẠNG THÁI) ---
    var textInput by remember { mutableStateOf("") } // Trạng thái ô nhập liệu
    var isDarkMode by remember { mutableStateOf(false) } // Trạng thái nút Switch
    var sliderValue by remember { mutableFloatStateOf(0.5f) } // Trạng thái thanh Slider
    var isLoading by remember { mutableStateOf(false) } // Trạng thái vòng xoay loading

    // Danh sách công việc (dùng mutableStateListOf để Compose tự cập nhật khi add/remove)
    val tasks = remember { mutableStateListOf(
        SampleTask(1, "Học Jetpack Compose", true),
        SampleTask(2, "Code giao diện XML", false)
    ) }

    // --- 2. LAYOUT CHÍNH: SCAFFOLD ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compose All-in-One") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (textInput.isNotBlank()) {
                    tasks.add(SampleTask(tasks.size + 1, textInput, false))
                    textInput = "" // Reset ô nhập
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Thêm")
            }
        }
    ) { innerPadding ->

        // --- 3. BOX: Dùng để xếp chồng (ProgressIndicator đè lên trên cùng) ---
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(if (isDarkMode) Color.DarkGray else Color.White)
        ) {

            // --- 4. COLUMN: Sắp xếp theo chiều dọc ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // --- 5. CARD & ROW & IMAGE: Header thông tin người dùng ---
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Image bo tròn
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null,
                               )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text("Nguyễn Văn A", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Mức độ hoàn thành: ${(sliderValue * 100).toInt()}%", fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- 6. INTERACTION: Switch & Slider ---
                Text("Cài đặt hệ thống", fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Chế độ tối (Dark Mode)")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
                }

                Text("Âm lượng thông báo")
                Slider(value = sliderValue, onValueChange = { sliderValue = it })

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // --- 7. INPUT: TextField ---
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    label = { Text("Nhập công việc mới...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // --- 8. LIST: LazyColumn (Thay thế RecyclerView) ---
                Text("Danh sách việc cần làm (${tasks.size})", fontWeight = FontWeight.SemiBold)

                LazyColumn(
                    modifier = Modifier.weight(1f), // Chiếm phần còn lại của màn hình
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tasks) { task ->
                        // Item layout
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Checkbox(
                                    checked = task.isDone,
                                    onCheckedChange = { isChecked ->
                                        // Cập nhật trạng thái item (Change State)
                                        val index = tasks.indexOf(task)
                                        tasks[index] = task.copy(isDone = isChecked)
                                    }
                                )
                                Text(
                                    text = task.title,
                                    modifier = Modifier.weight(1f),
                                    style = if (task.isDone) LocalTextStyle.current.copy(color = Color.Gray) else LocalTextStyle.current
                                )
                                IconButton(onClick = { tasks.remove(task) }) {
                                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                                }
                            }
                        }
                    }
                }

                // --- 9. BUTTON & LOADING: Giả lập tải dữ liệu ---
                Button(
                    onClick = { isLoading = true },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Đồng bộ dữ liệu")
                }
            }

            // Hiển thị vòng xoay nếu đang loading (Dùng Box để căn giữa)
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable { isLoading = false }, // Click để tắt loading
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}