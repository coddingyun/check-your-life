package com.example.checkyourlife

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MakeBlockDialog(
    dayTimePickerViewModelForStartTime: DayTimePickerViewModelForStartTime = hiltViewModel(),
    dayTimePickerViewModelForEndTime: DayTimePickerViewModelForEndTime = hiltViewModel(),
    colorPickerViewModel: ColorPickerViewModel = hiltViewModel(),
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onRemove: () -> Unit,
    onConfirm: (String, Color, String, String, ActivityType) -> Unit,
    //makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
    ) {
    val timePickerStateForStartTime = dayTimePickerViewModelForStartTime.timePickerState.value
    val timePickerStateForEndTime = dayTimePickerViewModelForEndTime.timePickerState.value
    val colorPickerState = colorPickerViewModel.colorPickerState.value
    val blockDialogState = makeBlockDialogViewModel.blockDialogState.value
    var activityName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    Dialog(onDismissRequest = {}) {
        // TODO: 활동명, 컬러, 시작시간, 종료시간 받기

        if (timePickerStateForStartTime?.isShowTimePicker == true) {
            DayTimePicker(
                onDismiss = {
                    timePickerStateForStartTime.onDismiss() // 🔹 함수 호출하도록 수정
                },
                onConfirm = { hour, minute ->
                    timePickerStateForStartTime.onConfirm(hour, minute) // 🔹 hour, minute을 전달하도록 수정
                    makeBlockDialogViewModel.setStartTime(hour, minute)
                }
            )
        }

        if (timePickerStateForEndTime?.isShowTimePicker == true) {
            DayTimePicker(
                onDismiss = {
                    timePickerStateForEndTime.onDismiss() // 🔹 함수 호출하도록 수정
                },
                onConfirm = { hour, minute ->
                    timePickerStateForEndTime.onConfirm(hour, minute) // 🔹 hour, minute을 전달하도록 수정
                    makeBlockDialogViewModel.setEndTime(hour, minute)
                }
            )
        }

        if (colorPickerState?.isShowColorPicker == true) {
            ColorPickerDialog(
                //initialColor = colorPickerState.color,
                onDismiss = {
                    colorPickerState.onDismiss()
                },
                onConfirm = { color ->
                    colorPickerState.onConfirm(color)
                    //makeBlockDialogViewModel.setColor(color)
                }
            )
        }

        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(320.dp)
                    .wrapContentHeight()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "활동 추가", fontSize = 18.sp)

                // 활동명 입력
                OutlinedTextField(
                    value = blockDialogState?.title ?: "",
                    onValueChange = { newTitle ->
                        makeBlockDialogViewModel.updateTitle(newTitle)
                    },
                    label = { Text("활동명") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // 색상 선택 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "색상 선택")
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        colorPickerViewModel.showColorPickerDialog()
                    }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.Gray
                        )
                    }
                    Text(blockDialogState?.color.toString() ?: "컬러를 선택해주세요")
                }

                Column {
                    Row {
                        Text("시작 시간 선택")
                        IconButton(onClick = {
                            dayTimePickerViewModelForStartTime.showTimePickerDialog()
                        }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Gray
                            )
                        }
                    }
                    if (blockDialogState?.startHour != null && blockDialogState.startMinute != null) {
                        Text(formatHHmm(blockDialogState?.startHour!!, blockDialogState?.startMinute!!))
                    } else {
                        Text("시간을 선택해주세요")
                    }

                    Row {
                        Text("끝 시간 선택")
                        IconButton(onClick = {
                            dayTimePickerViewModelForEndTime.showTimePickerDialog()
                        }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Gray
                            )
                        }
                    }
                    if (blockDialogState?.endHour != null && blockDialogState.endMinute != null) {
                        Text(formatHHmm(blockDialogState?.endHour!!, blockDialogState?.endMinute!!))
                    } else {
                        Text("시간을 선택해주세요")
                    }
                }

                // 버튼 (취소 / 확인)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onDismiss() }) {
                        Text(text = "취소")
                    }
                    if (blockDialogState?.isShowUpdateBlockDialog == true) {
                        Button(onClick = {
                            onRemove()
                        }) {
                            Text(text = "삭제")
                        }
                    }
                    Button(onClick = {
                        onConfirm(
                            blockDialogState?.title!!,
                            blockDialogState?.color!!,
                            formatHHmm(blockDialogState?.startHour!!, blockDialogState.startMinute!!),
                            formatHHmm(blockDialogState?.endHour!!, blockDialogState.endMinute!!),
                            blockDialogState.activityType!!
                        )
                    }) {
                        Text(text = "확인")
                    }
                }
            }
        }
    }
}