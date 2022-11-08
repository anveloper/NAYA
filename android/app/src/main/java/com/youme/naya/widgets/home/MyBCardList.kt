package com.youme.naya.widgets.home

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.hilt.navigation.compose.hiltViewModel
import com.youme.naya.R
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.databinding.BusinessCardItemBinding


@Composable
fun MyBCardList(context: Context) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val businessCards = cardViewModel.businessCardList.collectAsState().value

    AndroidViewBinding(
        { inflater, parent, _ ->
            BusinessCardItemBinding.inflate(inflater)
//            BusinessCardItemBinding.inflate(inflater, parent)
        },
        Modifier.fillMaxSize()
    ) {
        businessCards.forEach { card ->
            this.bcardName.text = card.name
            this.bcardEnglishName.text = card.engName
            this.bcardCompany.text = card.company
            this.bcardTeamAndRole.text = card.team + " | " + card.role
            this.bcardAddress.text = card.address
            this.bcardMobile.text = card.mobile
            this.bcardEmail.text = card.email
            this.bcardLogo.text = "로고 이미지"
            this.bcardQrcode.text = "QR코드 이미지"
        }

//        val mCardStackAdapter = CardStackAdapter(context, launcher);
//        stackviewMain.setAdapter(mCardStackAdapter)
//        mCardStackAdapter.updateData(businessCards)
    }



//    val inflatedFrame: View = getLayoutInflater().inflate(R.layout.demo_text, null)
//    val frameLayout: FrameLayout = inflatedFrame.findViewById<View>(R.id.screen) as FrameLayout
//    frameLayout.setDrawingCacheEnabled(true)
//    frameLayout.measure(
//        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//    )
//    frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight())
//    frameLayout.buildDrawingCache(true)
//    val bm: Bitmap = frameLayout.getDrawingCache()



//    val viewModel = viewModel<CardListViewModel>()
//    viewModel.fetchCards()
//    val cardList = viewModel.cardUris.value
//
//    CardHorizontalList(context, cardList)
}