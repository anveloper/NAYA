package com.youme.naya

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.youme.naya.graphs.BottomNavGraph
import com.youme.naya.ui.theme.*
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                backgroundColor = PrimaryBlue,
                shape = RoundedCornerShape(50.dp),
//                modifier = Modifier
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors =  listOf(
//                                SecondaryBasicBlue,
//                                SecondarySystemBlue
//                            ),
//
//                        ),
//                        shape = RoundedCornerShape(30.dp)
//                    )
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.nav_send_icon),
                    contentDescription = "send",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    tint = NeutralWhite
                )

            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = { BottomBar(navController = navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(
    navController: NavHostController) {
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
                    modifier = Modifier.width(24.dp).height(24.dp)
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        selectedContentColor = PrimaryBlue,
        unselectedContentColor = NeutralLight,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            } // 홈에서 뒤로가기 누르면 앱 밖으로 이동
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(rememberNavController())
}