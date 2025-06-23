import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TransactionTypeSegmentedButton(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Transaction Type",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        ) {
            listOf("Expense", "Income").forEach { type ->
                val isSelected = type == selectedType
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTypeSelected(type) }
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.inverseSurface else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = type,
                        color = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionTypeSegmentedButton2(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Transaction Type",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.offset(x = (-14).dp,y = (-5).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Expense", "Income").forEach { type ->
                val isSelected = type == selectedType
                Row(
                    modifier = Modifier.clickable {
                        onTypeSelected(type)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            onTypeSelected(type)
                        }
                    )
                    Text(
                        text = type,
                        textAlign = TextAlign.Start
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonPreview() {
    var selectedType by remember { mutableStateOf("Expense") }

    TransactionTypeSegmentedButton2(
        selectedType = selectedType,
        onTypeSelected = { selectedType = it }
    )
}
