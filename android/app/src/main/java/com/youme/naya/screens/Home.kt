package com.youme.naya.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.R
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.common.NayaBcardSwitchButtons
import com.youme.naya.widgets.home.MyBCardList
import com.youme.naya.widgets.home.MyNayaCardList
import com.youme.naya.widgets.home.TodaySchedule
import kotlinx.coroutines.launch


private val HomeModifier = Modifier
    .fillMaxHeight()
    .background(NeutralWhite)
//    .padding(bottom = 80.dp) // main 전체에 한번에 주는게 맞는 듯합니다.

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val context = LocalContext.current
    var (currentCardId, setCurrentCardId) = rememberSaveable {
        mutableStateOf(1)
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topEnd = 30.dp),
        sheetContent = {
            TodaySchedule(navController = navController)
        },
        sheetPeekHeight = 0.dp,
    ) {
        Column(HomeModifier) {
            Column(modifier = Modifier.fillMaxHeight(0.78f)) {
                NayaBcardSwitchButtons(
                    nayaTab = { MyNayaCardList(context, navController = navController) },
                    bCardTab = { MyBCardList(context, navController = navController) }
                )
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = NeutralWhite,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    ),
                )
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                    clip = true
                }
                .fillMaxSize()
                .shadow(
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    ),
                    clip = true,
                    elevation = 4.dp
                )
                .clickable(onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }),
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier
                    .padding(horizontal = 28.dp, vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "오늘의 일정 ",
                        color = PrimaryDark,
                        style = Typography.h6
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_calendar),
                        "calendar",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
                Spacer(Modifier.height(72.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}