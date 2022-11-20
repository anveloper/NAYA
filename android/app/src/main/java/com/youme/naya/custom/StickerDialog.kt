package com.youme.naya.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun StickerDialog(
    setNewSticker: () -> Unit,
    addSticker: (Int) -> Unit
) {
    AlertDialog(onDismissRequest = { setNewSticker() }, buttons = {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(StickerList.getList) { sticker ->
                IconButton(onClick = {
                    addSticker(sticker)
                    setNewSticker()
                }) {
                    Image(painterResource(sticker), null)
                }
            }
        }
    })
}

