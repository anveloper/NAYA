package com.youme.naya.widgets.common

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.youme.naya.R
import com.youme.naya.ui.theme.NeutralLight
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryDark
import com.youme.naya.ui.theme.fonts
import com.youme.naya.widgets.calendar.SearchHeaderBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderBar(
    navController: NavHostController,
    title: String = ""
) {
    var title = title
    var logo: Boolean = false
    var main: Boolean = true

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val activity = LocalContext.current as? Activity

    var closeActivityButton = false

    when (navBackStackEntry?.destination?.route) {
        "home" -> {
            logo = true
            main = true
        }
        "nuya" -> {
            logo = true
            main = true
        }
        "bCardModify/card={card}" -> {
            logo = false
            title = "카드 수정하기"
            closeActivityButton = true
        }
        "details" -> {
            logo = false
            main = true
            title = "카드 상세 보기"
            closeActivityButton = true
        }
        "schedule" -> {
            main = false
        }
    }

    if (main) {
        TopAppBar(
            modifier = Modifier.height(64.dp),
            backgroundColor = NeutralWhite,
            elevation = 0.dp,
            contentPadding = PaddingValues(horizontal = 8.dp),
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
                }
                else {
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
    } else {
        LazyColumn {
            stickyHeader {
                SearchHeaderBar()
            }
        }
    }
}
