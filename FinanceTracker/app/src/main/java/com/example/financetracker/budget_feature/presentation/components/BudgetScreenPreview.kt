package com.example.financetracker.budget_feature.presentation.components


import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.financetracker.ui.theme.AppTheme

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

        Surface(

        ) {

        }

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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BudgetScreenPreview2(

){

    AppTheme(
        dynamicColor = false,
        darkTheme = true
    ) {

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

                Surface(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f).padding(vertical = 8.dp)
                        .padding(horizontal = 16.dp),
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        MonthSelectorBudget(
                            state = BudgetStates(),
                            onEvent = {

                            },
                            context = context
                        )
                    }


                    if (false) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            NoBudgetMessage()
                        }

                    } else {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
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

                                if (true) {
                                    SliderWithValueInsideCustomThumb(
                                        sliderPosition = 50f,
                                        onValueChange = {

                                        }
                                    )
                                }

                            }
                        }

                    }


                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        if (false) {
                            Text("Save")
                        } else {
                            Text("Create")
                        }
                    }
                }

            }
        }

    }

}