import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuantityBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    sheetState: SheetState
) {

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ){
        var quantity by remember { mutableStateOf(1) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Quantity",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        if (quantity > 1) quantity--
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveCircle,
                        contentDescription = "Decrease",
                        tint = if(quantity > 1) Color.Black else Color.LightGray
                    )
                }

                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .width(60.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                IconButton(
                    onClick = {
                        quantity++
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Increase",
                        tint = if(quantity < 1000) Color.Black else Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onConfirm(quantity)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun QuantityBottomSheetPreview(){
    QuantityBottomSheet(
        onConfirm = {},
        onDismiss = {},
        sheetState = rememberModalBottomSheetState()
    )
}
