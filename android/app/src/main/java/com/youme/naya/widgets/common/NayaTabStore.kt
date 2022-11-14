package com.youme.naya.widgets.common

class NayaTabStore {
    companion object {
        private var currTabState = "naya"

        fun isNayaCard(): Boolean {
            return currTabState == "naya"
        }
        fun setCurrTabState(state: String) {
            currTabState = state
        }
    }
}