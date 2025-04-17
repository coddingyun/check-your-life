package com.rali.timelane.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}

@Composable
fun DialogOutlinedButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick,
        shape = RoundedCornerShape(8.dp)) {
        Text(text)
    }
}