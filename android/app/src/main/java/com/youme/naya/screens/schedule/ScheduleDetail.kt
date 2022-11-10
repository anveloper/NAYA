package com.youme.naya.screens.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.youme.naya.R
import com.youme.naya.ui.theme.*

private val CalendarHeaderBtnGroupModifier = Modifier
    .fillMaxWidth()
    .height(64.dp)
    .padding(start = 8.dp, end = 8.dp)

@Composable
fun ScheduleDetailScreen(
    navController: NavController,
    scheduleId : Int
) {
    Row(
        CalendarHeaderBtnGroupModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TopAppBar(
            modifier = Modifier.height(64.dp),
            backgroundColor = NeutralWhite,
            elevation = 0.dp,
            contentPadding = PaddingValues(horizontal = 8.dp),
        ) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_arrow_back_ios_24),
                            contentDescription = "Prev page button",
                            colorFilter = ColorFilter.tint(NeutralLight)
                        )
                    }
                    Text(
                        "수정",
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = PrimaryBlue,
                        modifier = Modifier.padding(horizontal = 8.dp).clickable(onClick = {
                            navController.navigate("scheduleEdit/${scheduleId}")
                        })
                    )
            }
            }
        }
    }
}
