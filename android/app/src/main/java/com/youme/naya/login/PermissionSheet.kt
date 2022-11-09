package com.youme.naya.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.R
import com.youme.naya.ui.theme.NeutralWhite

@Composable
fun PermissionSheet(
    permitted: Boolean,
    checkPermission: () -> Unit,
    userConfirm: () -> Unit,
) {

    Box(
        Modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(NeutralWhite)
    ) {
        if (!permitted) {
            PermissionComp { checkPermission() }

        } else {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                TextButton(onClick = { }) {
                    Text(text = "동의")
                }
            }
        }
    }

}

@Composable
fun PermissionComp(
    checkPermission: () -> Unit
) {
    Column(Modifier.fillMaxSize(), Arrangement.SpaceBetween, Alignment.CenterHorizontally) {
        Text("서비스 이용을 위해\n권한을 허용해주세요.")
        PermissionInfo(R.drawable.ic_permission_camera, "카메라", "ㅁㄴㅇㄹ")
        PermissionInfo(R.drawable.ic_permission_gallery, "갤러리", "ㅁㄴㅇㄹ")
        PermissionInfo(R.drawable.ic_permission_call, "연락처", "ㅁㄴㅇㄹ")
        PermissionInfo(R.drawable.ic_permission_location, "위치", "ㅁㄴㅇㄹ")
        PermissionInfo(R.drawable.ic_permission_noti, "알림", "ㅁㄴㅇㄹ")
        TextButton(onClick = {
            checkPermission()
        }) {
            Text("계속하기")
        }
    }
}

@Composable
fun PermissionInfo(
    imageId: Int,
    title: String,
    content: String
) {
    Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
        Image(painterResource(imageId), null)
        Column(Modifier.padding(4.dp), Arrangement.Center, Alignment.Start) {
            Text(text = title)
            Text(text = content)
        }
    }
}