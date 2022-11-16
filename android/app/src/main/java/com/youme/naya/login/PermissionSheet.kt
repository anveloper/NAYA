package com.youme.naya.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.R
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun PermissionSheet(
    permitted: Boolean,
    viewModel: PermissionViewModel,
    checkPermission: () -> Unit,
    userConfirm: () -> Unit,
) {

    Box(
        Modifier
            .fillMaxWidth()
            .height(536.dp)
            .background(NeutralWhite)
    ) {
        if (!permitted) {
            PermissionComp { checkPermission() }
        } else {
            PrivacyComp(viewModel) { userConfirm() }
        }
    }
}

@Composable
fun PrivacyComp(
    viewModel: PermissionViewModel,
    userConfirm: () -> Unit
) {
    val (terms, setTerms) = remember { mutableStateOf(false) }
    val (privacy, setPrivacy) = remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 36.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterHorizontally
    ) {
        Text(
            text = "서비스 이용을 위해\n이용약관 동의가 필요합니다",
            color = PrimaryDark,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = fonts
        )
        Column(
            Modifier
                .fillMaxWidth(0.8f), Arrangement.SpaceBetween, Alignment.CenterHorizontally
        ) {
            TextButton(onClick = { setTerms(!terms) }) {
                Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Check, null,
                        Modifier.size(24.dp),
                        if (terms) PrimaryBlue else NeutralGray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "이용약관",
                        color = PrimaryDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = fonts
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "(필수)",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = fonts
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                Modifier
                    .fillMaxWidth(0.96f)
                    .border(width = 1.dp,
                        color = NeutralLightness,
                        shape = RoundedCornerShape(8.dp))
                    .fillMaxHeight(0.24f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f)
                        .verticalScroll(rememberScrollState())
                ){
                Text(
                    text = viewModel.termsText.value,
                    color = NeutralGray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    fontFamily = fonts
                )}
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = { setPrivacy(!privacy) }) {
                Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Check, null,
                        Modifier.size(24.dp),
                        if (privacy) PrimaryBlue else NeutralGray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "개인정보 처리방침",
                        color = PrimaryDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = fonts
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "(필수)",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = fonts
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                Modifier
                    .fillMaxWidth(0.96f)
                    .border(width = 1.dp,
                        color = NeutralLightness,
                        shape = RoundedCornerShape(8.dp))
                    .fillMaxHeight(0.52f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f)
                        .verticalScroll(rememberScrollState())
                ){
                    Text(
                        text = viewModel.privacyText.value,
                        color = NeutralGray,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = fonts
                    )
                }

            }
        }
        PrimaryBigButton(text = "동의합니다", terms && privacy) {
            userConfirm()
        }
    }
}


@Composable
fun PermissionComp(
    checkPermission: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 36.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterHorizontally
    ) {
        Text(
            text = "서비스 이용을 위해\n권한을 허용해주세요",
            color = PrimaryDark,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = fonts
        )
        Column(
            Modifier
                .fillMaxWidth(0.82f), Arrangement.SpaceBetween, Alignment.CenterHorizontally
        ) {
            PermissionInfo(R.drawable.ic_permission_camera, "카메라", "명함 사진/동영상 촬영을 위해 필요합니다.")
            Spacer(modifier = Modifier.height(12.dp))
            PermissionInfo(R.drawable.ic_permission_gallery, "갤러리", "명함 사진/동영상 접근을 위해 필요합니다.")
            Spacer(modifier = Modifier.height(12.dp))
            PermissionInfo(R.drawable.ic_permission_call, "연락처", "연락처 저장 및 명함 연동을 위해 필요합니다.")
            Spacer(modifier = Modifier.height(12.dp))
            PermissionInfo(R.drawable.ic_permission_location, "위치", "일정과 연동된 위치 정보를 위해 필요합니다.")
            Spacer(modifier = Modifier.height(12.dp))
            PermissionInfo(R.drawable.ic_permission_noti, "알림", "일정에 대한 알림이 포함될 수 있습니다.")
        }
        PrimaryBigButton(text = "계속하기") {
            checkPermission()
        }
    }
}

@Composable
fun PermissionInfo(
    imageId: Int,
    title: String,
    content: String
) {
    Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
        Image(painterResource(imageId), null, Modifier.size(40.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.padding(4.dp), Arrangement.Center, Alignment.Start) {
            Text(
                text = title,
                color = PrimaryDark,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = fonts
            )
            Text(
                text = content,
                color = NeutralGray,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = fonts
            )
        }
    }
}

@Preview(
    name = "Privacy Sheet Preview",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PrivacyCompPreview() {
    PrivacyComp(PermissionViewModel()) {}
}

@Preview(
    name = "Permission Sheet Preview",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PermissionCompPreview() {
    PermissionComp {}
}