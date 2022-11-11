package com.youme.naya.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youme.naya.ui.theme.*

@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    isHintVisible: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier
            .width(300.dp)
            .height(52.dp),
        value = text,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            color = SecondarySystemBlue),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        enabled = isEnabled,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = PrimaryBlue,
            backgroundColor = NeutralLightness,
            focusedBorderColor = PrimaryBlue,
            unfocusedBorderColor = SecondaryLightBlue,
            disabledBorderColor = NeutralLight,
            disabledTextColor = NeutralGray
        ),
        singleLine = false,
        placeholder = {
            if (isHintVisible) {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        color = NeutralMedium))
            }
        }
    )
}

@Composable
@Preview
fun InputPreview() {
    BasicTextField(
        text = "input test",
        placeholder = "input test")
}