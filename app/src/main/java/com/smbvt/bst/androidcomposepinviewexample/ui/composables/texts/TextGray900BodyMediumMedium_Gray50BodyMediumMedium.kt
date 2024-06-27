package com.smbvt.bst.androidcomposepinviewexample.ui.composables.texts

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.BodyMediumMedium
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Gray50
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Gray900

@Composable
fun TextGray900BodyMediumMedium_Gray50BodyMediumMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        color = if (isSystemInDarkTheme()) Gray50 else Gray900,
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = MaterialTheme.typography.BodyMediumMedium
    )
}