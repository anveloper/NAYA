package com.youme.naya.database

import com.youme.naya.database.entity.Card

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
            Card(8, "test8", "010-0000-0008"),
            Card(9, "test9", "010-0000-0009"),
            Card(10, "test10", "010-0000-0010"),
        )
    }

}