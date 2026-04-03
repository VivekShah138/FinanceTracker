package com.example.financetracker.presentation.features.settings_feature.help_and_feedback.app_info.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.R
import androidx.compose.foundation.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.example.financetracker.presentation.core_components.AppTopBar
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import java.util.Calendar

@Composable
fun AppInfoScreen2(
    navController: NavController
) {
    // Get context to access version info
    val context = navController.context

    // Get version and build number
    val versionName = getVersionName(context)
    val versionCode = getVersionCode(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "Finance Tracker",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Version: $versionName ($versionCode)",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        ListItem(
            headlineContent = {
                Text(
                    text = "Open Source Licenses",
                    fontWeight = FontWeight.Medium
                )
            },
            supportingContent = {
                Text("Libraries used in this app")
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    context.startActivity(
                        Intent(context, OssLicensesMenuActivity::class.java)
                    )
                }
        )
    }
}

@Composable
fun AppInfoScreen(
    navController: NavController
) {

    // Get context to access version info
    val context = navController.context

    // Get version and build number
    val versionName = getVersionName(context)
    val versionCode = getVersionCode(context)

    val year = Calendar.getInstance().get(Calendar.YEAR)

    Scaffold(
        topBar = {
            AppTopBar(
                title = "About App",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .padding(paddingValues)
        ) {


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.logo2),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFF415F91))
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Finance Tracker",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Version $versionName (Build $versionCode)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider(
                thickness = 0.5.dp
            )

            Spacer(modifier = Modifier.height(32.dp))

            AppInfoSection(
                title = "DEVELOPER",
                itemCardList = listOf(
                    AppInfoItem(
                        leadingIcon = Icons.Default.Person,
                        headlineContent = "Developed by Vivek Shah",
                        supportingContent = "Android Developer",
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Link,
                        headlineContent = "LinkedIn",
                        externalIcon = Icons.AutoMirrored.Filled.OpenInNew
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Code,
                        headlineContent = "GitHub",
                        externalIcon = Icons.AutoMirrored.Filled.OpenInNew
                    )
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppInfoSection(
                title = "APP INFORMATION",
                itemCardList = listOf(
                    AppInfoItem(
                        leadingIcon = Icons.Default.Security,
                        headlineContent = "Privacy Policy",
                        externalIcon = Icons.Default.ChevronRight,
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Gavel,
                        headlineContent = "Open Source Licenses",
                        externalIcon = Icons.Default.ChevronRight,
                    ),
                    AppInfoItem(
                        leadingIcon = Icons.Default.Email,
                        headlineContent = "Contact Support",
                        externalIcon = Icons.Default.ChevronRight,
                    ),
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "© $year Finance Tracker App. All rights reserved.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
    }
}

fun getVersionName(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "10.0"
    } catch (e: Exception) {
        "1.0"
    }
}

fun getVersionCode(context: Context): Long {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toLong()
        }
    } catch (e: Exception) {
        1L
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppInfoScreenPreview(){
    FinanceTrackerTheme(darkTheme = false) {
        AppInfoScreen(
            navController = rememberNavController()
        )
    }
}