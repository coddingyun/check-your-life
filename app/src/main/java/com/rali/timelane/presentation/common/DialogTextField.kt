package com.rali.timelane.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
)  {
    OutlinedTextField(
        value,
        onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
}