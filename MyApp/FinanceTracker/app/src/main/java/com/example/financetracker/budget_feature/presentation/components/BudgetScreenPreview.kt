package com.example.financetracker.budget_feature.presentation.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.budget_feature.presentation.BudgetStates
import com.example.financetracker.core.core_presentation.components.AppTopBar

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BudgetScreenPreview(

){

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Budget",
                showBackButton = true,
                showMenu = false,
                onBackClick = {

                }
            )
        },

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MonthSelectorBudget(
                state = BudgetStates(),
                onEvent = {

                },
                context = context
            )

            // Middle content takes up remaining space

            if (false) {

                NoBudgetMessage(
                    modifier = Modifier.weight(1f)
                )

            } else {


                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    BudgetAmountInput(
                        amount = "100",
                        currencySymbol = "$",
                        onAmountChange = {

                        }
                    )

                    Spacer(Modifier.height(10.dp))


                    ReceiveAlertSwitch(
                        text = "Receive Alert",
                        isCheck = false,
                        onCheckChange = {

                        },
                        fontSize = 24.sp
                    )

                    Spacer(Modifier.height(15.dp))

                    if(true){
                        SliderWithValueInsideCustomThumb(
                            sliderPosition = 50f,
                            onValueChange = {

                            }
                        )
                    }

                }
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(17.dp)
            ) {
                if(false){
                    Text("Save")
                }else{
                    Text("Create")
                }
            }
        }
    }
}