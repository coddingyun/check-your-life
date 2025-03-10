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
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "활동 추가",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // 활동명 입력
                OutlinedTextField(
                    value = blockDialogState?.title ?: "",
                    onValueChange = { newTitle ->
                        makeBlockDialogViewModel.updateTitle(newTitle)
                    },
                    label = { Text("활동명") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 색상 선택 버튼
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "색상",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(blockDialogState?.color!!)
                                .clickable(
                                    onClick = {
                                        colorPickerViewModel.showColorPickerDialog()
                                    }
                                )
                        )
                    }
                    Divider()
                }

                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "시작 시간 선택",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        IconButton(
                            onClick = {
                                dayTimePickerViewModelForStartTime.showTimePickerDialog()
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    if (blockDialogState?.startHour != null && blockDialogState.startMinute != null) {
                        Text(formatHHmm(blockDialogState?.startHour!!, blockDialogState?.startMinute!!))
                    } else {
                        Text(
                            text = "시간을 선택해주세요",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Divider()

                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "끝 시간 선택",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        IconButton(
                            onClick = {
                                dayTimePickerViewModelForEndTime.showTimePickerDialog()
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    if (blockDialogState?.endHour != null && blockDialogState.endMinute != null) {
                        Text(formatHHmm(blockDialogState?.endHour!!, blockDialogState?.endMinute!!))
                    } else {
                        Text(
                            text = "시간을 선택해주세요",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 버튼 (취소 / 확인)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "취소")
                    }
                    if (blockDialogState?.isShowUpdateBlockDialog == true) {
                        OutlinedButton(
                            onClick = {
                                onRemove()
                            },
                            shape = RoundedCornerShape(8.dp)) {
                            Text(text = "삭제")
                        }
                    }
                    Button(
                        onClick = {
                            onConfirm(
                                blockDialogState?.title!!,
                                blockDialogState?.color!!,
                                formatHHmm(blockDialogState?.startHour!!, blockDialogState.startMinute!!),
                                formatHHmm(blockDialogState?.endHour!!, blockDialogState.endMinute!!),
                                blockDialogState.activityType!!
                            )
                        },
                        shape = RoundedCornerShape(8.dp)) {
                        Text(text = "확인")
                    }
                }
            }
        }
    }
}