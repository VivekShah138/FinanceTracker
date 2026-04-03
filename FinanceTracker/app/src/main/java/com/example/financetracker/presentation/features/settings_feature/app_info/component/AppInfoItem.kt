package com.example.financetracker.presentation.features.settings_feature.app_info.component

import androidx.compose.ui.graphics.vector.ImageVector

data class AppInfoItem(
    val leadingIcon: ImageVector,
    val externalIcon: ImageVector? = null,
    val headlineContent: String,
    val supportingContent: String? = null,
    val onClick: (() -> Unit)? = null
)