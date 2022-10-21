package com.youme.naya.screens

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class MultiFabState {
    COLLAPSED, EXPANDED
}

class MultiFabItem(
    val identifier: String,
    val icon: ImageBitmap,
    val label: String
)

@Composable
fun NuyaCardHolderScreen() {
    val scrollState = rememberScrollState()
    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.Blue
                    )
                ),
                alpha = 0.4f
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CardList(scrollState = scrollState)
        AddNewCardButton(toState = toState) { state -> toState = state }
    }
}

/**
 * 세로 스크롤이 가능한 카드 리스트 컴포저블
 *
 * @author Sckroll
 */
@Composable
fun CardList(scrollState: ScrollState) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.verticalScroll(scrollState)
    ) {
        itemsIndexed(
            listOf("Sckroll", "hellyeah")
        ) { index, item ->
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
            .width(300.dp)
            .aspectRatio(9 / 5f),
        backgroundColor = Color.White
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
 * @link https://compose.academy/blog/building_a_multi-action_floating_action_button_in_jetpack_compose
 * @author Sckroll
 */
@Composable
fun AddNewCardButton(
    toState: MultiFabState,
    stateChanged: (fabState: MultiFabState) -> Unit
) {
    val transition = updateTransition(targetState = toState)
    val rotation by transition.animateFloat { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }

    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            onClick = {
                stateChanged(
                    if (transition.currentState == MultiFabState.EXPANDED) {
                        MultiFabState.COLLAPSED
                    } else MultiFabState.EXPANDED
                )
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "", modifier = Modifier.rotate(rotation))
        }
    }
}

@Composable
private fun MiniFloatingActionButton(
    item: MultiFabItem,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    val buttonColor = MaterialTheme.colors.secondary

    Canvas(
        modifier = Modifier
            .size(32.dp)
            .clickable(
                onClick = { onFabItemClicked(item) },
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.dp,
                    color = MaterialTheme.colors.onSecondary
                )
            )
    ) {
        drawCircle(color = buttonColor, 56f)
        drawImage(
            item.icon,
            topLeft = Offset(
                (this.center.x) - (item.icon.width / 2),
                (this.center.y) - (item.icon.width / 2)
            )
        )
    }
}

@Composable
@Preview
fun NuyaCardHolderScreenPreview() {
    NuyaCardHolderScreen()
}