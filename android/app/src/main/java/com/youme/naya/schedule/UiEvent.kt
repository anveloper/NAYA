package com.youme.naya.schedule

sealed class UiEvent {
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}
