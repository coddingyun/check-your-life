package com.rali.timelane.presentation.colorPicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.checkyourlife.MakeBlockDialogViewModel

@Composable
fun ColorPickerDialog(
    colorPickerViewModel: ColorPickerViewModel = hiltViewModel(),
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onConfirm: (Color) -> Unit
) {
    //var selectedColor by remember { mutableStateOf(initialColor) }
    //var colorPickerState = colorPickerViewModel.colorPickerState.value
    val blockDialogState = makeBlockDialogViewModel.blockDialogState.value

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "색상을 선택하세요", fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))

                ColorPicker(selectedColor = blockDialogState?.color!!) { newColor ->
                    makeBlockDialogViewModel.setColor(newColor)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onDismiss() }) {
                        Text(text = "취소")
                    }

                    Button(onClick = { onConfirm(blockDialogState.color!!) }) {
                        Text(text = "확인")
                    }
                }
            }
        }
    }
}
