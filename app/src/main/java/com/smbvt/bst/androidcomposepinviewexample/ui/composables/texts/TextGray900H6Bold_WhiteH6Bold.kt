package com.smbvt.bst.androidcomposepinviewexample.ui.composables.texts

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Gray900
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.H6Bold_18
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.White

@Composable
fun TextGray900H6Bold_WhiteH6Bold(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        color = if (isSystemInDarkTheme()) White else Gray900,
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = MaterialTheme.typography.H6Bold_18
    )
}
