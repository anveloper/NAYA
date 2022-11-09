package com.youme.naya.card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.components.OutlinedSmallButton
import com.youme.naya.components.PrimarySmallButton
import com.youme.naya.ui.theme.NeutralMetal
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@Composable
fun DeleteAlertDialog(
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        buttons = {
            Box(
                Modifier.padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "명함을 삭제하시겠어요?",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "한 번 삭제한 명함은 되돌릴 수 없으니 신중하게 선택하세요",
                            fontFamily = fonts,
                            color = NeutralMetal,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        OutlinedSmallButton(text = "삭제하기") { onDelete() }
                        PrimarySmallButton(text = "돌아가기") { onCancel() }
                    }
                }
            }
        },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}