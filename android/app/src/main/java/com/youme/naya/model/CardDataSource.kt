package com.youme.naya.model

import com.youme.naya.model.entity.Card

class CardDataSource {

    fun loadCards(): List<Card> {
        return listOf(
            Card(1, "test1", "010-0000-0001"),
            Card(2, "test2", "010-0000-0002"),
            Card(3, "test3", "010-0000-0003"),
            Card(4, "test4", "010-0000-0004"),
            Card(5, "test5", "010-0000-0005"),
            Card(6, "test6", "010-0000-0006"),
            Card(7, "test7", "010-0000-0007"),
        )
    }

}