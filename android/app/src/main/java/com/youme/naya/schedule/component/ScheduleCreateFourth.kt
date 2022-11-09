package com.youme.naya.schedule.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.R
import com.youme.naya.schedule.ScheduleEditViewModel
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts

@Composable
fun ScheduleCreateFourth(
    viewModel: ScheduleEditViewModel = hiltViewModel(),
) {
    Text("멤버 등록",
        modifier = Modifier.padding(vertical = 12.dp),
        color = PrimaryDark,
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.height(8.dp))
    Image(
        painter = painterResource(R.drawable.schedule_member_register_icon),
        contentDescription = "member_plus",
        modifier = Modifier
            .width(64.dp)
            .height(64.dp),
    )
}