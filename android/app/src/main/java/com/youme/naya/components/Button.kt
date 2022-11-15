package com.youme.naya.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youme.naya.ui.theme.*

@Composable
fun PrimaryBigButton(
    text: String,
    // 클릭 가능 / 불가능 여부
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .width(300.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryBlue,
            contentColor = NeutralWhite,
            disabledBackgroundColor = PrimaryBlue
                .copy(alpha = 0.7f),
            disabledContentColor = NeutralWhite
                .copy(alpha = 0.3f)
        ),
        shape = Shapes.large,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
            /* disabledElevation = 0.dp */
        ),
        onClick = { onClick() },
        enabled = enabled,


        contentPadding = PaddingValues(),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}

@Composable
fun PrimarySmallButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(120.dp)
            .height(48.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryBlue,
            contentColor = NeutralWhite,
            disabledBackgroundColor = PrimaryBlue
                .copy(alpha = 0.7f),
            disabledContentColor = NeutralWhite
                .copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}

@Composable
fun PrimaryTinySmallButton(
    backgroundColor: Color,
    contentColor: Color,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(72.dp)
            .height(32.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = PrimaryBlue
                .copy(alpha = 0.7f),
            disabledContentColor = NeutralWhite
                .copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}



@Composable
fun OutlinedBigButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(300.dp)
            .height(48.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = NeutralWhite,
            contentColor = PrimaryBlue,
        ),
        contentPadding = PaddingValues(),
        border = BorderStroke(1.dp, SecondaryLightBlue),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}


@Composable
fun OutlinedSmallButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(120.dp)
            .height(48.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = NeutralWhite,
            contentColor = PrimaryBlue,
        ),
        contentPadding = PaddingValues(),
        border = BorderStroke(1.dp, SecondaryLightBlue),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}

@Composable
fun OutlinedTinySmallButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(72.dp)
            .height(32.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = NeutralWhite,
            contentColor = PrimaryBlue,
        ),
        contentPadding = PaddingValues(),
        border = BorderStroke(1.dp, SecondaryLightBlue),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}

@Composable
fun SecondaryIconButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: Int?
) {
    Button(
        modifier = Modifier
            .width(240.dp)
            .height(48.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = NeutralLightness,
            contentColor = PrimaryDark,
        ),
        contentPadding = PaddingValues(),
    )
    {
        icon?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(24.dp)
                    .height(24.dp)
            )
        }
        Text(
            text = text,
            style = Typography.button)

    }
}

@Composable
fun SecondarySelectIconButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: Int,
    // 선택 상태인지
    selected: Boolean = false,
) {
    Button(
        modifier = Modifier
            .width(280.dp)
            .height(40.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) NeutralLightness else SecondaryLightBlue,
            contentColor = PrimaryDark
        ),
        contentPadding = PaddingValues(),
    )
    {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .width(24.dp)
                .height(24.dp),
            tint = if (selected) NeutralLight else PrimaryBlue
        )
        Text(
            text = text,
            style = Typography.button)
    }
}

@Composable
fun RegisterButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.88f)
            .height(48.dp),
        shape = Shapes.large,
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryBlue,
            contentColor = NeutralWhite,
        ),
        contentPadding = PaddingValues(),
    )
    {
        Text(
            text = text,
            style = Typography.button)
    }
}