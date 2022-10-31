package com.youme.naya

import android.app.Application
import com.youme.naya.db.NayaDatabase
import com.youme.naya.db.repository.NayaCardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NayaApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { NayaDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NayaCardRepository(database.nayaCardDao()) }

}