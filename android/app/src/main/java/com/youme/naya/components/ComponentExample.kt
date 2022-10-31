package com.youme.naya.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExampleScreen() {
    val (text, setValue) = remember {
        mutableStateOf("")
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    // 키보드 제어
    val keyboardController = LocalSoftwareKeyboardController.current
    // focus
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Scaffold( scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester),
                text = text,
                onChange = setValue,
                placeholder = "input test")
            PrimarySmallButton(
                text = "클릭",
                onClick = {
                    // 키보드 내리기
                    keyboardController?.hide()
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Hello $text")
                    }
                    // focus 해제
                    focusManager.clearFocus()
                })
        }
    }


}

@Composable
@Preview
fun ExampleScreenPreview() {
    ExampleScreen()
}