import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.exp

@Composable
fun AccountBalance(
    currencySymbol: String,
    amount: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Account Balance",
            style = MaterialTheme.typography.titleMedium,
//            fontSize = 12.sp
        )
//        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$currencySymbol$amount",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun AccountBalancePreview() {
    MaterialTheme {
        AccountBalance(
            currencySymbol = "$",
            amount = "2,500.00"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Summary(
    incomeAmount:String = "0.00",
    currencySymbol:String = "$",
    expenseAmount:String = "0.00",
    balanceAmount:String = "0.00",

){


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Summary", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Income", style = MaterialTheme.typography.bodySmall)
                    Text(currencySymbol+incomeAmount, color = Color(0xFF4CAF50)) // Green
                }
                Column {
                    Text("Expense", style = MaterialTheme.typography.bodySmall)
                    Text(currencySymbol+expenseAmount, color = Color(0xFFF44336)) // Red
                }
                Column {
                    Text("Balance", style = MaterialTheme.typography.bodySmall)
                    Text(currencySymbol+balanceAmount, fontWeight = FontWeight.Bold)
                }
            }
        }
    }


}
