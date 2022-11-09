package com.youme.naya.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream

class PermissionViewModel : ViewModel() {
    private val _termsText = mutableStateOf("")
    val termsText = _termsText

    private val _privacyText = mutableStateOf("")
    val privacyText = _privacyText

    fun loadTerms(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val inputStream: InputStream = context.assets.open("terms.txt")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                val string = String(buffer)
                launch(Dispatchers.Main) {
                    _termsText.value = string
                }
            } catch (e:IOException){
                e.printStackTrace()
            }
        }
    }

    fun loadPrivacy(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val inputStream: InputStream = context.assets.open("privacy.txt")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                val string = String(buffer)
                launch(Dispatchers.Main) {
                    _privacyText.value = string
                }
            } catch (e:IOException){
                e.printStackTrace()
            }
        }
    }
}