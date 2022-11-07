package com.youme.naya.card

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.youme.naya.BaseActivity
import com.youme.naya.components.OutlinedSmallButton
import com.youme.naya.components.PrimarySmallButton
import com.youme.naya.database.entity.Card
import com.youme.naya.database.viewModel.CardViewModel
import com.youme.naya.graphs.CardDetailsNavGraph
import com.youme.naya.ui.theme.AndroidTheme
import com.youme.naya.ui.theme.fonts
import com.youme.naya.widgets.common.HeaderBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsActivity : BaseActivity(TransitionMode.VERTICAL) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val cardId = intent.getIntExtra("cardId", 1)
            val navController = rememberNavController()

            AndroidTheme {
                Scaffold(topBar = {
                    HeaderBar(navController = navController)
                }) {
                    CardDetailsNavGraph(navController, cardId)
                }
            }
        }
    }
}

