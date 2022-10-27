package com.youme.naya.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.R
import com.youme.naya.card.CustomCardStackView
import com.youme.naya.card.TestStackAdapter
import com.youme.naya.databinding.CardStackViewMainBinding
import com.youme.naya.ui.theme.*

enum class MultiFabState {
    COLLAPSED, EXPANDED
}

class MultiFabItem(
    val identifier: String,
    val icon: ImageBitmap,
//    val icon: ImageVector,
    val label: String,
    val action: (() -> Unit)?
)

@Composable
fun NuyaCardHolderScreen(navController: NavHostController) {
    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
    val ctx = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        NeutralWhite,
                        NeutralLight
                    )
                ),
            )
            .padding(32.dp)
            .addFocusCleaner(focusManager)
    ) {
        SearchInput()
        NayaBcardSwitchButtons()
        Box(Modifier.fillMaxSize()) {
//            CardList()
            CustomCardStackView()
            MultiFloatingActionButton(
                listOf(
                    MultiFabItem(
                        "camera",
                        ContextCompat.getDrawable(ctx, R.drawable.ic_outline_add_a_photo_24)!!
                            .toBitmap().asImageBitmap(),
                        "카메라 열기"
                    ) { },
                    MultiFabItem(
                        "write",
                        ContextCompat.getDrawable(ctx, R.drawable.ic_outline_keyboard_alt_24)!!
                            .toBitmap().asImageBitmap(),
                        "직접 입력"
                    ) { navController.navigate("nuyaCreate") }
                ),
                toState,
                true
            ) { state -> toState = state }
        }
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

/**
 * 검색 창 컴포저블
 *
 * @author Sckroll
 */
@Composable
fun SearchInput() {
    var textState by remember {
        mutableStateOf(TextFieldValue())
    }
    val source = remember {
        MutableInteractionSource()
    }
    var focused by remember {
        mutableStateOf(false)
    }
    val focusRequester by remember {
        mutableStateOf(FocusRequester())
    }

    BasicTextField(
        value = textState, onValueChange = { textState = it },
        singleLine = true,
        interactionSource = source,
        modifier = Modifier
            .padding(bottom = 24.dp)
            .shadow(8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focused = it.isFocused }
    ) { innerTextField ->
        Row(
            Modifier
                .background(NeutralLightness, RoundedCornerShape(percent = 20))
                .padding(16.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = Icons.Outlined.Search.name,
                modifier = Modifier.padding(end = 16.dp)
            )

            if (!focused && textState.text.isEmpty()) {
                Text("이름, 전화번호, 회사명, 직책으로 검색", color = NeutralMedium)
            }
            innerTextField()
        }
    }
}

/**
 * NAYA 카드 / 명함 전환 버튼 컴포저블
 *
 * @author Sckroll
 */
@Composable
fun NayaBcardSwitchButtons() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Text("NAYA")
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(34.dp)
                .padding(horizontal = 16.dp)
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text("BUSINESS")
        }
    }
}

/**
 * 세로 스크롤이 가능한 카드 리스트 컴포저블
 *
 * @author Sckroll
 */
@Composable
fun CardList() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(
            listOf("Test 1", "Test 2", "Test 3", "Test 4")
        ) { _, item ->
            CardContainer(item = item)
        }
    }
}

/**
 * 카드 컨테이너 컴포저블
 *
 * @author Sckroll
 */
@Composable
fun CardContainer(item: String) {
    Card(
        modifier = Modifier
            .aspectRatio(9 / 5f)
            .width(300.dp),
        backgroundColor = NeutralWhite
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "your name is $item"
            )
        }
    }
}

/**
 * 우측 하단에 위치한 카드 추가 버튼
 *
 * @author Sckroll
 */
@Composable
fun MultiFloatingActionButton(
    items: List<MultiFabItem>,
    toState: MultiFabState,
    showLabel: Boolean = true,
    stateChanged: (fabState: MultiFabState) -> Unit,
) {
    val transition = updateTransition(targetState = toState, label = "transition")
    val rotation by transition.animateFloat(
        label = "rotation"
    ) { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }
    val scale by transition.animateFloat(
        label = "scale"
    ) { state ->
        if (state == MultiFabState.EXPANDED) 56f else 0f
    }
    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        },
        label = "alpha"
    ) { state ->
        if (state == MultiFabState.EXPANDED) 1f else 0f
    }
    val shadow by transition.animateDp(
        transitionSpec = {
            tween(durationMillis = 50)
        },
        label = "shadow"
    ) { state ->
        if (state == MultiFabState.EXPANDED) 2.dp else 0.dp
    }
//    val backgroundAlpha = if (toState == MultiFabState.EXPANDED) 0.4f else 0f

    /**
     * FAB 터치 시 반투명 오버레이 화면
     */
//    Box(
//        modifier = Modifier
//            .alpha(animateFloatAsState(backgroundAlpha).value)
//            .background(
//                Color.Black
//            )
//            .fillMaxSize()
//    )
    /**
     * 실제 FAB 부분
     */
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(bottom = 64.dp)
        ) {
            items.forEach { item ->
                MiniFabItem(item, alpha, shadow, scale, showLabel, transition)
                Spacer(modifier = Modifier.height(24.dp))
            }
            FloatingActionButton(
                onClick = {
                    stateChanged(
                        if (transition.currentState == MultiFabState.EXPANDED) {
                            MultiFabState.COLLAPSED
                        } else MultiFabState.EXPANDED
                    )
                },
                backgroundColor = PrimaryBlue,
                contentColor = NeutralWhite
            ) {
                Icon(Icons.Filled.Add, "", modifier = Modifier.rotate(rotation))
            }
        }
    }
}

/**
 * 카드 추가 버튼 클릭 시 나타나는 소형 플로팅 액션 버튼(FAB) 및 텍스트
 *
 * @author Sckroll
 */
@Composable
private fun MiniFabItem(
    item: MultiFabItem,
    alpha: Float,
    shadow: Dp,
    scale: Float,
    showLabel: Boolean,
    transition: Transition<MultiFabState>
) {
    val buttonColor = SecondaryLightBlue
//    val shadowColor = ContextCompat.getColor(LocalContext.current, R.color.neutral_dark_gray)
    val interactionSource = MutableInteractionSource();
//    val painter = rememberVectorPainter(image = item.icon)
    var isExpanded = transition.currentState == MultiFabState.EXPANDED

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        if (showLabel) {
            Text(
                item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(animateFloatAsState(targetValue = alpha).value)
                    .shadow(
                        animateDpAsState(targetValue = shadow).value
                    )
                    .background(color = MaterialTheme.colors.surface)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),

                )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Canvas(
            modifier = if (isExpanded) Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp,
                        color = NeutralDarkGray
                    )
                ) {
                    if (isExpanded) {
                        item.action?.let { it() }
                    }
                } else Modifier.size(32.dp)
        ) {
//            drawCircle(
//                Color(shadowColor),
//                center = Offset(this.center.x + 2f, this.center.y + 7f),
//                radius = scale
//            )
            drawCircle(color = buttonColor, scale)
            drawImage(
                item.icon,
                topLeft = Offset(
                    (this.center.x) - (item.icon.width / 2),
                    (this.center.y) - (item.icon.width / 2)
                ),
                alpha = alpha
            )
//            translate(left = 10f, top = 10f) {
//                with(painter) {
//                    draw(
//                        alpha = alpha,
//                        size = painter.intrinsicSize
//                    )
//                }
//            }
        }
    }
}

@Composable
@Preview
fun NuyaCardHolderScreenPreview() {
    NuyaCardHolderScreen(rememberNavController())
}