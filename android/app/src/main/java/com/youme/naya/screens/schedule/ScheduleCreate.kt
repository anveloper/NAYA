package com.youme.naya.screens.schedule

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.R
import com.youme.naya.components.PrimaryBigButton
import com.youme.naya.schedule.ScheduleMainViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.calendar.AnimatedCalendar
import com.youme.naya.widgets.common.HeaderBar
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import androidx.navigation.NavHostController
import com.youme.naya.card.BusinessCardGridListForSchedule
import com.youme.naya.card.NayaCardGridListForSchedule
import com.youme.naya.components.PrimaryBigButton2
import com.youme.naya.components.RegisterButton
import com.youme.naya.database.entity.Member.Companion.memberIconsCancel
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.schedule.component.*
import com.youme.naya.widgets.common.NayaBcardSwitchButtons
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCreateScreen(
    navController: NavHostController,
    viewModel: ScheduleMainViewModel = hiltViewModel(),
    cardViewModel: CardViewModel = hiltViewModel()
) {
    val componentVariable = remember {
        mutableStateOf(0)
    }

    val memberType = remember {
        mutableStateOf(-1)
    }

    val memberNum = remember {
        mutableStateOf(0)
    }

    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current

    Column (verticalArrangement = Arrangement.SpaceBetween) {
        // 상단 바
        when (componentVariable.value) {
            0 -> HeaderBar(navController = navController, title = "일정 등록")
            else -> {
                TopAppBar(
                    modifier = Modifier.height(64.dp),
                    backgroundColor = NeutralWhite,
                    elevation = 0.dp,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                ) {
                    Row(
                        Modifier.height(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            componentVariable.value = componentVariable.value - 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.ic_baseline_arrow_back_ios_24),
                                contentDescription = "Prev page button",
                                colorFilter = ColorFilter.tint(NeutralLight)
                            )
                        }
                        Text(
                            "일정 등록",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 48.dp),
                            textAlign = TextAlign.Center,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryDark
                        )
                    }
                }
            }
        }

        // 캘린더
        AnimatedCalendar(
            false,
            takeMeToDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            customCalendarEvents = emptyList()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 하위 컴포넌트들
        Column(modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                // 멤버 저장 위한 변수
                val lastKey =
                    if (viewModel.schedulesAll.value.isEmpty()) {
                        0
                    } else {
                        viewModel.schedulesAll.value.last().scheduleId
                    }

                val bottomSheetState =
                    rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                val coroutineScope = rememberCoroutineScope()

                when (componentVariable.value) {
                    0 -> ScheduleCreateFirst()
                    1 -> ScheduleCreateSecond()
                    2 -> ScheduleCreateThird()
                    3 -> {
                        ModalBottomSheetLayout(
                            sheetContent = {
                                LazyColumn (
                                    horizontalAlignment=Alignment.CenterHorizontally
                                ){
                                    when (memberType.value) {
                                        -1 -> item {
                                            Box(modifier = Modifier
                                                .clickable(
                                                    onClick = {
                                                        memberType.value = 1
                                                    })
                                                .padding(vertical = 4.dp)
                                                .fillMaxWidth()
                                                .height(48.dp),
                                                contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = "Nuya 보관함에서 가져오기",
                                                    color = PrimaryBlue,
                                                    style = Typography.body1,
                                                )
                                            }
                                            Box(modifier = Modifier
                                                .clickable(
                                                    onClick = {
                                                        memberType.value = 0
                                                    })
                                                .padding(vertical = 4.dp)
                                                .fillMaxWidth()
                                                .height(48.dp),
                                                contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = "직접 입력",
                                                    color = PrimaryBlue,
                                                    style = Typography.body1,
                                                )
                                            }
                                        }
                                        0 -> item {
                                            Column(modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally) {
                                                MemberInput()
                                                RegisterButton(
                                                    text = "등록",
                                                    onClick = {
                                                        if (lastKey != null) {
                                                            viewModel.insertTemporaryMember(memberType.value,
                                                                memberNum.value % 6,
                                                                lastKey + 1)
                                                        }

                                                        memberNum.value += 1
                                                        memberType.value = -1

                                                        coroutineScope.launch {
                                                            bottomSheetState.hide()
                                                        }
                                                    },
                                                )
                                                Spacer(modifier = Modifier.height(20.dp))
                                            }

                                        }
                                        1 -> item {
                                            Spacer(Modifier.height(20.dp))
                                            Text(
                                                text = "함께 할 Nuya를 선택해주세요",
                                                color = PrimaryDark,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Box(
                                                Modifier
                                                    .fillMaxSize()
                                                    .background(Color.White)
                                            ) {
                                                Column(
                                                    Modifier.height(400.dp)
                                                ) {
                                                    NayaBcardSwitchButtons(
                                                        nayaTab = {
                                                            NayaCardGridListForSchedule(context,
                                                                navController,
                                                                true)
                                                        },
                                                        bCardTab = {
                                                            BusinessCardGridListForSchedule(context,
                                                                navController, cardViewModel,
                                                                true)
                                                        }
                                                    )
                                                    if (viewModel.cardUri.value != "" && lastKey != null) {
                                                        viewModel.insertTemporaryMember(memberType.value,
                                                            memberNum.value % 6,
                                                            lastKey + 1
                                                        )

                                                        memberNum.value += 1
                                                        memberNum.value = 1

                                                        coroutineScope.launch {
                                                            bottomSheetState.hide()
                                                        }
                                                    }
                                                }
                                            }
                                            PrimaryBigButton(text = "다른 방법으로 선택하기",
                                                onClick = {
                                                    memberType.value = -1 })
                                            Spacer(Modifier.height(20.dp))
                                        }
                                    }
                                }
                            },
                            sheetState = bottomSheetState,
                            scrimColor = Color(0XCCFFFFFF),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(Alignment.CenterHorizontally),
                        ) {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column (modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                ){
                                    Text("멤버 등록",
                                        modifier = Modifier.padding(vertical = 12.dp),
                                        color = PrimaryDark,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text("멤버를 클릭하면 목록에서 삭제됩니다.", style = Typography.body2, color = SystemRed)
                                    Spacer(modifier = Modifier
                                        .height(12.dp)
                                        .background(PrimaryBlue))
                                    Image(
                                        painter = painterResource(R.drawable.schedule_member_register_icon),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(64.dp)
                                            .height(64.dp)
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                    coroutineScope.launch {
                                                        bottomSheetState.show()
                                                    }
                                                }
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    LazyVerticalGrid(
                                        modifier = Modifier.fillMaxWidth(),
                                        columns = GridCells.Fixed(5),
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    )
                                    {
                                        items(viewModel.memberList.value.size) { index ->
                                            Row() {
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Image(
                                                        painter = painterResource(memberIconsCancel[viewModel.memberList.value[index].memberIcon!!]),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .clickable(
                                                                enabled = true,
                                                                onClick = {
                                                                    viewModel.deleteTemporaryMember(index)
                                                                }
                                                            )
                                                    )
                                                    viewModel.memberList.value[index].name?.let {
                                                        Text(
                                                            it,
                                                            color = NeutralGray,
                                                            style = Typography.overline
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                PrimaryBigButton2(
                                    text = "다음",
                                    onClick = {
                                        componentVariable.value = componentVariable.value + 1
                                    },
                                )
                            }
                        }
                    }
                    4 -> ScheduleCreateFinal()
                }

                if (componentVariable.value != 3) {
                    PrimaryBigButton(
                        text = when (componentVariable.value) {
                            4 -> "등록하기"
                            else -> "다음"
                        },
                        onClick = {
                            componentVariable.value = componentVariable.value + 1
                            when (componentVariable.value) {
                                5 -> {
                                    if (lastKey != null) {
                                        viewModel.insertSchedule(selectedDate = viewModel.selectedDate.value, scheduleId = lastKey + 1)
                                    }
                                    navController.navigate("schedule")
                                }
                            }
                        },
                    )
                }
            }
        )
    }

}


