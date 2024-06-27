package com.smbvt.bst.androidcomposepinviewexample.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.smbvt.bst.androidcomposepinviewexample.R


val Typography.H6Bold_18: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.font_bold),
        ),
        fontSize = 18.sp
    )



val Typography.BodyMediumMedium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.font_normal),
        ),
        fontSize = 14.sp
    )



val Typography.BodyMediumBold: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.font_bold),
        ),
        fontSize = 14.sp
    )