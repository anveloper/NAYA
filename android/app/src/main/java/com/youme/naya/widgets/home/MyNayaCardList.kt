package com.youme.naya.widgets.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MyNayaCardList(context: Context) {
    val viewModel = viewModel<CardListViewModel>()
    viewModel.fetchCards()
    val cardList = viewModel.cardUris.value

    CardHorizontalList(context, cardList)
}
