package com.example.hellofromcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewConteiner()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ViewConteiner() {
    Scaffold(
        topBar = { Toolbar() }
    ) { paddingValues ->
        HelloFrom(paddingValues)
    }
}

@Composable
fun HelloFrom(paddingValues: PaddingValues) {
    var name by rememberSaveable { mutableStateOf("") }
    val maxLength = 20
    var showGreeting by rememberSaveable { mutableStateOf(false) }
    val kb = LocalSoftwareKeyboardController.current
    var showError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(showError) {
        if (showError) {
            kotlinx.coroutines.delay(2000)
            showError = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                if (it.length <= maxLength) {
                    name = it
                }else {
                    showError = true
                }
                showGreeting = false
            },
            label = { Text(text = "Introduce Tu Nombre") },
            supportingText = {
                Text(
                    text = "${name.length}/$maxLength",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.End
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                //cambia el color a error osea rojo cuando intentas rebasar el numero de caracteres
                focusedBorderColor = if (showError) MaterialTheme.colorScheme.error else colorResource(R.color.background),
                unfocusedBorderColor = if (showError) MaterialTheme.colorScheme.error else colorResource(R.color.background),
                focusedLabelColor = if (showError) MaterialTheme.colorScheme.error else colorResource(R.color.background),
                unfocusedLabelColor = if (showError) MaterialTheme.colorScheme.error else colorResource(R.color.background),
                ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (name.isNotEmpty()){
                    showGreeting = true
                    kb?.hide()
                }
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.teal_700)),
            enabled = name.isNotBlank(),
        ) {
            Text("Saludar")
            if (name.isBlank()){
                colorResource(R.color.grey)
            }
        }
        if (showGreeting && name.isNotEmpty()){
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Hola $name, ¿como estas?",
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(R.color.teal_700)
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
    TopAppBar(
        title = { Text(text = "Hello From Compose") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.background),
            titleContentColor = colorResource(R.color.white)
        )
    )
}