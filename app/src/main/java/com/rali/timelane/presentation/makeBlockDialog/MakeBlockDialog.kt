package com.rali.checkyourlife

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.rali.timelane.presentation.activityBlock.ActivityViewModel
import com.rali.timelane.presentation.activityBlock.ActivityType
import com.rali.timelane.presentation.colorPicker.ColorPickerDialog
import com.rali.timelane.presentation.colorPicker.ColorPickerViewModel
import com.rali.timelane.presentation.common.CustomAlertDialog
import com.rali.timelane.presentation.dayTimePicker.DayTimePickerViewModelForEndTime
import com.rali.timelane.presentation.dayTimePicker.DayTimePickerViewModelForStartTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MakeBlockDialog(
    dayTimePickerViewModelForStartTime: DayTimePickerViewModelForStartTime = hiltViewModel(),
    dayTimePickerViewModelForEndTime: DayTimePickerViewModelForEndTime = hiltViewModel(),
    colorPickerViewModel: ColorPickerViewModel = hiltViewModel(),
    makeBlockDialogViewModel: MakeBlockDialogViewModel = hiltViewModel(),
    activityViewModel: ActivityViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onRemove: () -> Unit,
    onConfirm: (String, Color, String, String, ActivityType) -> Unit,
) {
    val timePickerStateForStartTime = dayTimePickerViewModelForStartTime.timePickerState.value
    val timePickerStateForEndTime = dayTimePickerViewModelForEndTime.timePickerState.value
    val colorPickerState = colorPickerViewModel.colorPickerState.value
    val blockDialogState = makeBlockDialogViewModel.blockDialogState.value
    val mainState = homeViewModel.homeState.value
    val validationState by makeBlockDialogViewModel.validationState.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        makeBlockDialogViewModel.initValidationState()
    }

    LaunchedEffect(validationState) {
        if (validationState is ValidationResult.Valid) {
            if ((validationState as ValidationResult.Valid).isCopy) {
                activityViewModel.addActivity(
                    Activity(
                        title = blockDialogState!!.title,
                        date = mainState?.date!!,
                        colorInt = blockDialogState.color.toArgb(),
                        startTime = formatHHmm(blockDialogState?.startHour!!, blockDialogState.startMinute!!),
                        endTime = formatHHmm(blockDialogState?.endHour!!, blockDialogState.endMinute!!),
                        type = ActivityType.REALITY.name,
                    )
                )
                onDismiss()
            } else {
                onConfirm(
                    blockDialogState?.title!!,
                    blockDialogState?.color!!,
                    formatHHmm(blockDialogState?.startHour!!, blockDialogState.startMinute!!),
                    formatHHmm(blockDialogState?.endHour!!, blockDialogState.endMinute!!),
                    blockDialogState.activityType!!
                )
            }
        }
    }

    val activities = when (blockDialogState?.activityType) {
        ActivityType.PLAN -> activityViewModel.plannedActivities.collectAsState(initial = emptyList())
        else -> activityViewModel.actualActivities.collectAsState(initial = emptyList())
    }

    val actualActivities = activityViewModel.actualActivities.collectAsState()

    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        onDismiss()
    }) {
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
                }
            )
        }

        if (showDeleteConfirmation) {
            CustomAlertDialog(
                dialogTitle = "활동 삭제",
                dialogText = "정말 이 활동을 삭제하시겠습니까?",
                onConfirm = {
                    showDeleteConfirmation = false
                    onRemove()
                },
                onDismiss = {
                    showDeleteConfirmation = false
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 8.dp, y = (-8).dp)  // 오른쪽 위로 더 이동
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Dialog"
                        )
                    }

                    Text(
                        text = if (blockDialogState!!.isShowMakeBlockDialog) "활동 추가" else "활동 수정",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 20.dp)
                    )
                }

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

                Spacer(modifier = Modifier.height(1.dp))

                // 에러 메시지 표시
                if (validationState is ValidationResult.Invalid) {
                    Text(
                        text = (validationState as ValidationResult.Invalid).message,
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }

                if (blockDialogState?.isShowUpdateBlockDialog == true && 
                    blockDialogState.activityType == ActivityType.PLAN) {
                    Button(
                        onClick = {
                            makeBlockDialogViewModel.validateBlock(
                                blockDialogState?.id,
                                blockDialogState?.title,
                                blockDialogState?.startHour,
                                blockDialogState?.startMinute,
                                blockDialogState?.endHour,
                                blockDialogState?.endMinute,
                                actualActivities.value.filter { it.date == mainState?.date!! },
                                mainState?.date!!,
                                isCopy = true,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    ) {
                        Text(
                            text = "동일 시간에 완료했어요!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (blockDialogState?.isShowUpdateBlockDialog == true) {
                        OutlinedButton(
                            onClick = {
                                showDeleteConfirmation = true
                            },
                            shape = RoundedCornerShape(8.dp)) {
                            Text(text = "삭제")
                        }
                    }

                    // 버튼 클릭 시 유효성 검사 실행
                    Button(
                        onClick = {
                            makeBlockDialogViewModel.validateBlock(
                                blockDialogState?.id,
                                blockDialogState?.title,
                                blockDialogState?.startHour,
                                blockDialogState?.startMinute,
                                blockDialogState?.endHour,
                                blockDialogState?.endMinute,
                                activities.value.filter { it.date == mainState?.date!! },
                                mainState?.date!!,
                                isCopy = false,
                            )
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "확인")
                    }

                }
            }
        }
    }
}