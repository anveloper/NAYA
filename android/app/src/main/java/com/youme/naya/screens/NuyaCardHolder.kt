package com.youme.naya.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.youme.naya.R
import com.youme.naya.card.CustomCardStackView
import com.youme.naya.constant.MultiFabState
import com.youme.naya.database.entity.Card
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
    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }

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
                nayaTab = { MyNuyaCardList() },
                bCardTab = { MyBusinessCardList() }
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
                ) { navController.navigate("nuyaCreate") }
            ),
            toState,
            true
        ) { state -> toState = state }
    }
}

@Composable
fun MyNuyaCardList() {
    val cardViewModel: CardViewModel = hiltViewModel()
    NuyaCardList(cardViewModel)
}

@Composable
fun NuyaCardList(cardViewModel: CardViewModel = viewModel()) {
    val cards = cardViewModel.cardList.collectAsState().value

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CardInputText(text = name, label = "이름", onTextChange = {
                if (it.all { char -> char.isLetter() || char.isWhitespace() }) name = it
            })
            CardInputText(text = mobile, label = "전화번호", onTextChange = {
                if (it.all { char -> char.isLetter() || char.isWhitespace() }) mobile = it
            })
            CardSaveButton(text = "저장", onClick = {
                if (name.isNotEmpty() && mobile.isNotEmpty()) {
                    cardViewModel.addCard(Card(0, name, mobile))
                    name = ""
                    mobile = ""
                }
            })
        }
        LazyColumn {
            items(cards) { card -> CardRow(card) { cardViewModel.removeCard(it) } }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardInputText(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        maxLines = maxLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun CardSaveButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun CardRow(
    card: Card,
    onCardClicked: (Card) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), color = NeutralLightness, elevation = 6.dp
    ) {
        Column(
            Modifier
                .clickable { onCardClicked(card) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = card.name, style = MaterialTheme.typography.subtitle2)
            Text(text = card.mobile, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun MyBusinessCardList() {
    val testData = listOf<String>(
        "Business Card 1",
        "Business Card 2",
        "Business Card 3",
        "Business Card 4",
        "Business Card 5",
        "Business Card 6"
    )
    CustomCardStackView(testData)
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

//@Composable
//@Preview
//fun NuyaCardHolderScreenPreview() {
//    NuyaCardHolderScreen(rememberNavController())
//}