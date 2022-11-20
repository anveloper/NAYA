package com.youme.naya

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.youme.naya.card.BusinessCardCreateDialog
import com.youme.naya.components.OutlinedBigButton
import com.youme.naya.custom.MediaCardActivity
import com.youme.naya.graphs.BottomNavGraph
import com.youme.naya.intro.IntroDialog
import com.youme.naya.intro.IntroViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.utils.addFocusCleaner
import com.youme.naya.utils.convertUri2Path
import com.youme.naya.utils.saveSharedCardImage
import com.youme.naya.widgets.common.HeaderBar
import com.youme.naya.widgets.common.NayaTabStore
import com.youme.naya.widgets.home.CardListViewModel
import com.youme.naya.widgets.home.SharedSaveImageDialog
import com.youme.naya.widgets.share.ShareButtonDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    sharedImageUrl: String,
    introViewModel: IntroViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val focusManager = LocalFocusManager.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val viewModel = viewModel<CardListViewModel>()

    // 현재 위치 추적
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val (shareAlert, setShareAlert) = remember { mutableStateOf(false) }
    val (saveImage, setSaveImage) = remember { mutableStateOf(sharedImageUrl) }

    // 선택된 카드 가져오기
//    val card = CurrentCard.getCurrentCard.value
    val (imgSelector, setImgSelector) = remember { mutableStateOf(false) }
    // 비즈니스 카드 생성 방법 선택 다이얼로그 표시 여부
    var bCardCreateDialogInNaya by remember { mutableStateOf(false) }
    var bCardCreateDialogInNuya by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.i("Activity Result", it.resultCode.toString())
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                viewModel.fetchNayaCards()
            }
            Activity.RESULT_CANCELED -> {
            }
        }
    }
    // 갤러리 액티비티
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data as Uri
            val imgPath = convertUri2Path(context, uri)
            saveSharedCardImage(context, BitmapFactory.decodeFile(imgPath))
            Toast.makeText(context, "카드를 불러왔어요", Toast.LENGTH_SHORT).show()
        }
    }
    val mediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data as Uri
            val imgPath = convertUri2Path(context, uri)
            val mediaIntent = Intent(activity, MediaCardActivity::class.java)
            mediaIntent.putExtra("savedImgAbsolutePath", imgPath)
            launcher.launch(mediaIntent)
        }
    }
    //
//    val introViewModel = viewModel<IntroViewModel>()
    val (isFirst, openIntro) = remember { mutableStateOf(false) }
    setObserver(introViewModel, lifeCycleOwner) { openIntro(true) }

    Scaffold(
        floatingActionButton = {
            if (currentDestination.toString() != "scheduleCreate"
                && currentDestination.toString() != "scheduleDetail/{scheduleId}"
                && currentDestination.toString() != "scheduleEdit/{scheduleId}"
            ) {
                FloatingActionButton(
                    onClick = {
                        when (currentDestination.toString()) {
                            "schedule" -> navController.navigate("scheduleCreate")
                            "naya" -> {
                                if (NayaTabStore.isNayaCard()) {
                                    setImgSelector(true)
                                } else {
                                    bCardCreateDialogInNaya = true
                                }
                            }
                            "nuya" -> {
                                if (NayaTabStore.isNayaCard()) {
                                    val intent = Intent(Intent.ACTION_PICK)
                                    intent.type = "image/*"
                                    galleryLauncher.launch(intent)
                                } else {
                                    bCardCreateDialogInNuya = true
                                }
                            }
                            else -> {
                                setShareAlert(true)
                            }
                        }
                    },
                    backgroundColor = Color.Transparent,
                    shape = CircleShape,
                ) {
                    Box(
                        Modifier
                            .width(64.dp)
                            .height(64.dp)
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
                                .width(44.dp)
                                .height(44.dp),
                            tint = NeutralWhite
                        )
                    }

                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            if (currentDestination.toString() != "scheduleCreate" && currentDestination.toString() != "scheduleDetail/{scheduleId}"
                && currentDestination.toString() != "scheduleEdit/{scheduleId}"
            ) {
                HeaderBar(navController = navController)
            }
        },
        bottomBar = {
            if (currentDestination.toString() != "scheduleCreate" && currentDestination.toString() != "scheduleDetail/{scheduleId}"
                && currentDestination.toString() != "scheduleEdit/{scheduleId}"
            ) {
                BottomBar(navController = navController)
            }
        },
        modifier = Modifier
            .addFocusCleaner(focusManager)
    ) {
        BottomNavGraph(navController = navController)
        if (shareAlert) {
            ShareButtonDialog(activity!!, navController) {
                setShareAlert(false)
            }
        }
        if (bCardCreateDialogInNaya) {
            BusinessCardCreateDialog(navController) {
                bCardCreateDialogInNaya = false
            }
        }
        if (bCardCreateDialogInNuya) {
            BusinessCardCreateDialog(navController, isNuya = true) {
                bCardCreateDialogInNuya = false
            }
        }
        if (saveImage != "") {
            SharedSaveImageDialog(saveImage, navController) { setSaveImage("") }
        }
        if (isFirst) {
            IntroDialog {
                openIntro(false)
                introViewModel.saveIsFirst()
                introViewModel.loadIsFirst()
            }
        }
        if (imgSelector) {
            AlertDialog({ setImgSelector(false) }, {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "어떤 방법으로 카드를 만들까요?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedBigButton(text = "카메라로 촬영하기") {
                        launcher.launch(
                            Intent(
                                activity,
                                MediaCardActivity::class.java
                            )
                        )
                        setImgSelector(false)
                    }
                    OutlinedBigButton(text = "갤러리에서 불러오기") {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        mediaLauncher.launch(intent)
                        setImgSelector(false)
                    }
                }
            }, Modifier.wrapContentSize(),
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White
            )
        }
    }
}

fun setObserver(
    introViewModel: IntroViewModel,
    owner: LifecycleOwner,
    openIntro: () -> Unit
) {
    introViewModel.isFirst.observe(owner) {
        if (it) {
            openIntro()
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.NuyaCard,
        BottomBarScreen.Spacer,
        BottomBarScreen.NayaCard,
        BottomBarScreen.Calendar,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = PrimaryLight,
        modifier = Modifier
            .height(72.dp)
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
            },
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
        modifier = Modifier.padding(bottom = 6.dp),
        label = {
            Text(
                screen.title,
                fontSize = 11.sp,
                fontFamily = pico,
            )
        },
        icon = {
            screen.icon?.let { painterResource(id = it) }?.let {
                Icon(
                    painter = it,
                    contentDescription = screen.title,
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
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