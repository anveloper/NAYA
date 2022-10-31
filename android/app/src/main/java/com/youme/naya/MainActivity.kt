package com.youme.naya

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youme.naya.card.TempNayaCardListAdapter
import com.youme.naya.db.viewModel.NayaCardViewModel
import com.youme.naya.db.viewModel.NayaCardViewModelFactory
import com.youme.naya.ui.theme.AndroidTheme


private var mainContext: Context? = null

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val nayaCardViewModel: NayaCardViewModel by viewModels {
        NayaCardViewModelFactory((application as NayaApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainContext = applicationContext
        setContent {
            AndroidTheme {
                navController = rememberNavController()
                MainScreen(navController)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TempNayaCardListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        nayaCardViewModel.allCards.observe(this, Observer { cards ->
            cards?.let { adapter.submitList(it) }
        })
    }

}

fun getMainContext(): Context? {
    return mainContext?.applicationContext
}

@Preview(
    name = "naya Project",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainPreview() {
//    MainScreen()
}