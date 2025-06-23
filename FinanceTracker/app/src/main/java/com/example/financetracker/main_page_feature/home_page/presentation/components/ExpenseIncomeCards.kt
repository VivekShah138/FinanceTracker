import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.ui.theme.AppTheme

@Composable
fun ExpenseIncomeCards(
    expenseAmount: String,
    incomeAmount: String,
    incomeSymbol: String = "$",
    expenseSymbol: String = "$"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .height(120.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5E5))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Expense Icon",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Expense",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFD32F2F)
                    )
                    Text(
                        text = "$expenseSymbol$expenseAmount",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Clip, // Instead of Ellipsis
                        softWrap = false,
                        color = Color.Black
                    )
                }
            }
        }


        Card(
            modifier = Modifier
                .weight(1f)
                .height(120.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE5FFF1))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = "Income Icon",
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF2E7D32)
                    )
                    Text(
                        text = "$incomeSymbol$incomeAmount",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Clip, // Instead of Ellipsis
                        softWrap = false,
                        color = Color.Black
                    )
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
fun ExpenseIncomeCardsPreview(){

    AppTheme(
        darkTheme = true,
        dynamicColor = true
    ) {

        ExpenseIncomeCards(
            expenseAmount = "5000",
            incomeAmount = "10000"
        )

    }

}