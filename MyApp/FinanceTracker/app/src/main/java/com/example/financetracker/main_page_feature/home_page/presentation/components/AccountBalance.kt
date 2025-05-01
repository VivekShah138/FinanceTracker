import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            fontSize = 12.sp
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

@Preview(showBackground = true)
@Composable
fun AccountBalancePreview() {
    MaterialTheme {
        AccountBalance(
            currencySymbol = "$",
            amount = "2,500.00"
        )
    }
}
