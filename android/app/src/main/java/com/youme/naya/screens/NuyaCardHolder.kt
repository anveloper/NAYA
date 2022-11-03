package com.youme.naya.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.R
import com.youme.naya.card.CustomCardStackView
import com.youme.naya.constant.MultiFabState
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ui.theme.*
import com.youme.naya.widgets.common.NayaBcardSwitchButtons


class MultiFabItem(
    val identifier: String,
    val icon: ImageBitmap,
    val label: String,
    val action: (() -> Unit)?
)

@Composable
fun NuyaCardHolderScreen(navController: NavHostController) {
    val ctx = LocalContext.current
    val cardViewModel: CardViewModel = hiltViewModel()
    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
    var isBCard = false

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            SearchInput()
            NayaBcardSwitchButtons(
                nayaTab = {
                    isBCard = false
                    CustomCardStackView(cardViewModel, false)
                },
                bCardTab = {
                    isBCard = true
                    CustomCardStackView(cardViewModel, true)
                }
            )
        }
        MultiFloatingActionButton(
            listOf(
                MultiFabItem(
                    "camera",
                    ContextCompat.getDrawable(ctx, R.drawable.ic_outline_add_a_photo_24)!!
                        .toBitmap().asImageBitmap(),
                    "카메라 열기"
                ) { navController.navigate("nuyaDetails") },
                MultiFabItem(
                    "write",
                    ContextCompat.getDrawable(ctx, R.drawable.ic_outline_keyboard_alt_24)!!
                        .toBitmap().asImageBitmap(),
                    "직접 입력"
                ) { navController.navigate("nuyaCreate/${if (isBCard) "BCard" else "NayaCard"}") }
            ),
            toState,
            true
        ) { state -> toState = state }
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
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = textState, onValueChange = { textState = it },
        singleLine = true,
        interactionSource = source,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focused = it.isFocused },
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }),
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.BottomEnd
    ) {
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
    val interactionSource = MutableInteractionSource();
    val isExpanded = transition.currentState == MultiFabState.EXPANDED

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
            drawCircle(color = buttonColor, scale)
            drawImage(
                item.icon,
                topLeft = Offset(
                    (this.center.x) - (item.icon.width / 2),
                    (this.center.y) - (item.icon.width / 2)
                ),
                alpha = alpha
            )
        }
    }
}

@Composable
@Preview
fun NuyaCardHolderScreenPreview() {
    NuyaCardHolderScreen(rememberNavController())
}