package com.youme.naya.widgets.home

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.BusinessCardBinding
import com.youme.naya.widgets.items.CardItem
import com.youme.naya.widgets.items.CardItemPlus
import kotlinx.coroutines.launch


@Composable
fun MyBCardList(context: Context, navController: NavHostController) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val businessCards = cardViewModel.businessCardListInNaya.collectAsState().value

    // page를 이동하기 위한 상태 값
    val currentCardId = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 처음 아이템의 padding을 정해주기 위한 식
    val display = context.resources?.displayMetrics
    val deviceWidth = display?.widthPixels
    val deviceHeight = display?.heightPixels

    fun px2dp(px: Int) =
        px / ((context.resources.displayMetrics.densityDpi?.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)

    val listVerticalPadding = (px2dp(deviceWidth!!) - 200) / 2
    val listSize = businessCards.size

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = listVerticalPadding.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            state = currentCardId
        ) {
            items(businessCards) { value ->
                CardItem(bCard = value)
            }
            item() {
                CardItemPlus(navController = navController, isBCard = true)
            }
        }
        Spacer(Modifier.height(16.dp))
        Row {
            if (listSize == 0) {
                Text(text = "명함을 등록해 보세요", color = Color(0xFFCED3D6))
            } else {
                if (listSize > 2) {
                    IconButton(onClick = {
                        coroutineScope.launch { currentCardId.animateScrollToItem(0) }
                    }) {
                        Icon(
                            Icons.Filled.ArrowLeft,
                            "move to start"
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = {
                        coroutineScope.launch { currentCardId.animateScrollToItem(listSize) }
                    }) {
                        Icon(
                            Icons.Outlined.Add,
                            "plus"
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = {
                        coroutineScope.launch { currentCardId.animateScrollToItem(listSize - 1) }
                    }) {
                        Icon(
                            Icons.Filled.ArrowRight,
                            "move to end"
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                } else {
                    IconButton(onClick = {
                        coroutineScope.launch { currentCardId.animateScrollToItem(listSize) }
                    }) {
                        Icon(
                            Icons.Outlined.Add,
                            "plus"
                        )
                    }
                }
            }
        }
    }
}
