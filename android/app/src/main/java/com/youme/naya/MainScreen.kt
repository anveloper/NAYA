package com.youme.naya

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.youme.naya.custom.MediaCardActivity
import com.youme.naya.graphs.BottomNavGraph
import com.youme.naya.share.ShareActivity
import com.youme.naya.ui.theme.*
import com.youme.naya.utils.addFocusCleaner
import com.youme.naya.widgets.common.HeaderBar
import com.youme.naya.widgets.items.CurrentCard
import com.youme.naya.widgets.share.ShareButtonDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val activity = context as? Activity
    val focusManager = LocalFocusManager.current

    // 현재 위치 추적
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val (shareAlert, setShareAlert) = remember { mutableStateOf(false) }

    // 선택된 카드 가져오기
    val card = CurrentCard.getCurrentCard.value

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { it ->
        Log.i("Activity Result", it.resultCode.toString())
        when (it.resultCode) {
            Activity.RESULT_OK -> {
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            when (currentDestination.toString()) {
                "scheduleCreate" -> {}
                else -> {
                    FloatingActionButton(
                        onClick = {
                            when (currentDestination.toString()) {
                                "schedule" -> navController.navigate("scheduleCreate")
                                "naya" -> launcher.launch(
                                    Intent(
                                        activity,
                                        MediaCardActivity::class.java
                                    )
                                )
                                "nuya" -> launcher.launch(
                                    Intent(
                                        activity,
                                        MediaCardActivity::class.java
                                    )
                                )
                                else -> {
//                                    var intent = Intent(activity, ShareActivity::class.java)
//                                    intent.putExtra("cardUri", card.uri.toString())
//                                    intent.putExtra("filename", card.filename)
//                                    launcher.launch(intent)
                                    setShareAlert(true)
                                }
                            }
                        },
                        backgroundColor = Color.Transparent,
                        shape = CircleShape,
                    ) {
                        Box(
                            Modifier
                                .width(60.dp)
                                .height(
                                    60.dp
                                )
                                .background(
                                    SecondaryGradientBrush,
                                    CircleShape
                                ), Alignment.Center
                        ) {
                            // 아이콘 상황에 따라 변하게
                            fun setCenterIcon(): Int {
                                return when (currentDestination.toString()) {
                                    "schedule" -> R.drawable.nav_schedule_plus_icon
                                    "naya" -> R.drawable.nav_naya_plus_icon
                                    "nuya" -> R.drawable.nav_naya_plus_icon
                                    else -> R.drawable.nav_send_icon
                                }
                            }
                            Icon(
                                painter = painterResource(setCenterIcon()),
                                contentDescription = "send",
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                tint = NeutralWhite
                            )
                        }
                    }

                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            when (currentDestination.toString()) {
                "scheduleCreate" -> {}
                else -> {
                    HeaderBar(navController = navController)
                }
            }
        },
        bottomBar = {
            when (currentDestination.toString()) {
                "scheduleCreate" -> {}
                else -> {
                    BottomBar(navController = navController)
                }
            }
        },
        modifier = Modifier
            .addFocusCleaner(focusManager)
    ) {
        BottomNavGraph(navController = navController)
        if (shareAlert) {
            ShareButtonDialog(activity!!) {
                setShareAlert(false)
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.NuyaCardHolder,
        BottomBarScreen.Spacer,
        BottomBarScreen.NayaCard,
        BottomBarScreen.Calendar,
//        BottomBarScreen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = PrimaryLight,
        modifier = Modifier
            .background(
                color = PrimaryLight,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            )
            .graphicsLayer {
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
                clip = true
            }
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        modifier = Modifier
            .height(72.dp),
        label = {
            Text(
                screen.title,
                fontSize = 10.sp,
                fontFamily = pico,
            )
        },
        icon = {
            screen.icon?.let { painterResource(id = it) }?.let {
                Icon(
                    painter = it,
                    contentDescription = screen.title,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        selectedContentColor = PrimaryBlue,
        unselectedContentColor = NeutralLight,
        onClick = {
            if (screen.route.isNotEmpty()) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                } // 홈에서 뒤로가기 누르면 앱 밖으로 이동
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(rememberNavController())
}