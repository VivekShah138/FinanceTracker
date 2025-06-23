package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.ui.theme.AppTheme

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun ProfileSetUpPreview(){

    AppTheme(darkTheme = true) {


        Scaffold(
            topBar = {
                AppTopBar(title = "Profile",showBackButton = true, showMenu = false, onBackClick = {} )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                EmailDisplay(label = "Email", email = "Shahvivek138@gmail.com", onChangeEmailClick = {})
//            ChangePasswordSection(onChangePasswordClick = {})

                Names(firstName = "Vivek", lastName = "Shah", onFirstNameChange = {}, onLastNameChange = {})


                SimpleDropdownMenu(label = "Country",selectedText = "India", expanded = false,
                    list = emptyList<String>(), onExpandedChange = {}, onDismissRequest = {}, displayText = { it }, onItemSelect = {})
                PhoneNumberInput(countryCode = "+91", phoneNumber = "9987063119", onPhoneNumberChange = {})
                SimpleDropdownMenu(label = "Base Currency",selectedText = "Rupee", expanded = false,
                    list = emptyList<String>(), onExpandedChange = {}, onDismissRequest = {}, displayText = { it }, onItemSelect = {})
                SaveButton(text = "Save", onClick = {})



            }
        }

    }
}




@Composable
fun Names(
    firstName: String,
    lastName: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    ) {
        // Country Code Input
        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("First Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),

            modifier = Modifier.weight(1f) // Adjust width as needed
        )

        Spacer(modifier = Modifier.width(8.dp)) // Adds space between fields

        // Phone Number Input
        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Last Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.weight(1f) // Takes remaining space
        )
    }
}



