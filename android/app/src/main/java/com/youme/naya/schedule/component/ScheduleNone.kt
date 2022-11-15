package com.youme.naya.schedule.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.youme.naya.R
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.ui.theme.NeutralMedium
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.Typography
import com.youme.naya.ui.theme.pico

@Composable
fun ScheduleNone(selectedDate : String, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth(0.88f),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(60.dp))
        Text("NAYA is FREE", fontFamily = pico, fontSize = 32.sp, color = PrimaryDark, modifier = Modifier.padding(vertical = 4.dp))
        Text("${selectedDate}등록된 일정이 없어요", color = NeutralMedium, style = Typography.body2)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.none_schedule_emogi),
            "none_schedule",
            modifier = Modifier
                .width(95.dp)
                .height(95.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (navController.currentDestination.toString() == "home") {
            PrimaryBigButton(text = "일정 등록하러 가기", onClick = {navController.navigate("scheduleCreate")})
        }
    }
}