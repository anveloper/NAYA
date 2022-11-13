package com.youme.naya.screens


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.card.BusinessCardGridList
import com.youme.naya.card.BusinessCardStackList
import com.youme.naya.card.NayaCardGridList
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.ocr.DocumentScannerActivity
import com.youme.naya.ocr.StillImageActivity
import com.youme.naya.ui.theme.NeutralWhite
import com.youme.naya.ui.theme.PrimaryBlue
import com.youme.naya.utils.convertUri2Path
import com.youme.naya.widgets.common.NayaBcardSwitchButtons

@Composable
fun NayaCardScreen() {
    val cardViewModel: CardViewModel = hiltViewModel()
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            NayaBcardSwitchButtons(
                nayaTab = {
                    NayaCardGridList(context)
                },
                bCardTab = {
//                    BusinessCardStackList(context, cardViewModel)
                    BusinessCardGridList(context, cardViewModel)
                }
            )
        }
    }
}

@Preview
@Composable
fun NayaCardScreenPreview() {
    NayaCardScreen()
}
