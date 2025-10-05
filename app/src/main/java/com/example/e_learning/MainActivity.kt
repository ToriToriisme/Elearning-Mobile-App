package com.example.e_learning

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "page1"
            ) {
                composable("page1") {
                    NumberInputScreen(navController = navController)
                }
                composable("page2") {
                    EmailValidationScreen(navController = navController)
                }
                composable("page3") {
                    AgeCategorizationScreen(navController = navController)
                }
            }
        }
    }
}

// Page 1: Number Input with List Generation
@Composable
fun NumberInputScreen(navController: NavHostController) {
    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    var list by remember { mutableStateOf(listOf<Int>()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Thực hành 02", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Nhập vào số lượng") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                val number = input.toIntOrNull()
                if (number == null) {
                    error = true
                    list = emptyList()
                } else {
                    error = false
                    list = (1..number).toList()
                }
            }) {
                Text("Tạo")
            }
        }

        if (error) {
            Text(
                text = "Dữ liệu bạn nhập không hợp lệ",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        list.forEach { num ->
            Button(
                onClick = {
                    val message = when {
                        num % 2 == 0 -> "Đúng: $num là số chẵn"
                        num % 2 != 0 -> "Sai: $num là số lẻ"
                        else -> "Null"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = num.toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("page2") }) {
                Text("Next: Email")
            }
        }
    }
}

// Page 2: Email Validation
@Composable
fun EmailValidationScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Thực hành 02", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { 
                email = it
                errorMessage = ""
                successMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Email") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                when {
                    email.isBlank() -> {
                        errorMessage = "Email không hợp lệ"
                        successMessage = ""
                    }
                    !email.contains("@") -> {
                        errorMessage = "Email không đúng định dạng"
                        successMessage = ""
                    }
                    else -> {
                        errorMessage = ""
                        successMessage = "Bạn đã nhập email hợp lệ"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiểm tra")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (successMessage.isNotEmpty()) {
            Text(
                text = successMessage,
                color = Color.Green,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("page1") }) {
                Text("Back: Numbers")
            }
            Button(onClick = { navController.navigate("page3") }) {
                Text("Next: Age")
            }
        }
    }
}

// Page 3: Age Categorization
@Composable
fun AgeCategorizationScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Thực hành 01", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Họ và tên") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = age,
            onValueChange = { age = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Tuổi") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val ageNumber = age.toIntOrNull()
                if (ageNumber != null) {
                    result = when {
                        ageNumber < 2 -> "em bé (<2)"
                        ageNumber in 2..6 -> "trẻ em (2-6)"
                        ageNumber in 6..65 -> "Người lớn (6-65)"
                        ageNumber > 65 -> "Người già (>65)"
                        else -> "Không xác định"
                    }
                } else {
                    result = "Tuổi không hợp lệ"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiểm tra")
        }

        if (result.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tên: $name",
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Tuổi: $age",
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Phân loại: $result",
                fontSize = 16.sp,
                color = Color.Blue,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("page2") }) {
                Text("Back: Email")
            }
            Button(onClick = { navController.navigate("page1") }) {
                Text("Home: Numbers")
            }
        }
    }
}
