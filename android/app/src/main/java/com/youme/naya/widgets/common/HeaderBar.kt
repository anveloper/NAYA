package com.youme.naya.widgets.common

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.youme.naya.R

@Composable
fun HeaderBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val activity = LocalContext.current as? Activity

    var title = ""
    var logo = false
    var closeActivityButton = false

    when (navBackStackEntry?.destination?.route) {
        "home" -> {
            logo = true
        }
        "nuya" -> {
            logo = true
        }
        "bCardEdit" -> {
            logo = false
            title = "카드 직접 등록"
        }
        "details" -> {
            logo = false
            title = "카드 상세 보기"
            closeActivityButton = true
        }
    }

    TopAppBar(
        modifier = Modifier.height(64.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (logo) {
                Row(
                    Modifier
                        .height(24.dp)
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.home_logo_image),
                        contentDescription = "logo",
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 4.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.home_logo_text),
                        contentDescription = "logo text",
                        modifier = Modifier.fillMaxHeight()
                    )
                }
                Row(
                    Modifier.height(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(R.drawable.home_icon_alarm),
                            contentDescription = "Alarm button"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(R.drawable.home_icon_setting),
                            contentDescription = "Settings button"
                        )
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (closeActivityButton) Arrangement.SpaceBetween else Arrangement.Start
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_arrow_back_ios_24),
                            contentDescription = "Prev page button"
                        )
                    }
                    Text(
                        title,
                        modifier = if (closeActivityButton) Modifier else Modifier
                            .fillMaxWidth()
                            .padding(end = 48.dp),
                        textAlign = TextAlign.Center
                    )
                    if (closeActivityButton) {
                        IconButton(
                            onClick = { activity?.finish() }
                        ) {
                            Icon(Icons.Filled.Close, Icons.Filled.Close.toString())
                        }
                    }
                }
            }
        }
    }
}