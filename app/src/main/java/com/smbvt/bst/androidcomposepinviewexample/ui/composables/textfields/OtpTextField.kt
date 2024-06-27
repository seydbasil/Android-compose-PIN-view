package com.smbvt.bst.androidcomposepinviewexample.ui.composables.textfields


import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.isDigitsOnly
import com.smbvt.bst.androidcomposepinviewexample.ui.composables.texts.TextGray900H6Bold_WhiteH6Bold
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.AlphaBlue1A
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.BorderWidth1
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.ColorFFC0C0C0
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Dark2
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Dark3
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Dark4
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Gray100
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.Gray700
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PaddingDefault16
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PaddingDefault4
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.PrimaryBlue900
import com.smbvt.bst.androidcomposepinviewexample.ui.theme.RoundedCorner12
import kotlinx.coroutines.delay

const val PIN_LENGTH = 6

/**
 * @see OutlinedOtpTextField is a customized single text filed where each characters are seems like single passcode character filed
 * @param showFieldFocusIndicator if true, an indicator will show for the whole text filed.
 * @param showFieldItemFocusIndicator if true, focused passcode character field will show indicator.
 * @param showItemFocusIndicatorEvenTextEntered if true, focused passcode character field will show indicator even text entered.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedOtpTextField(
    modifier: Modifier = Modifier,
    isPINView: Boolean = false,
    value: String,
    aspectRatio: Float,
    onValueChange: (String) -> Unit,
    codeLength: Int = PIN_LENGTH,
    onFilled: () -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = PasswordVisualTransformation(),
    requestFocus: Boolean,
    showFieldFocusIndicator: Boolean = true,
    showFieldItemFocusIndicator: Boolean = true,
    showItemFocusIndicatorEvenTextEntered: Boolean = true,
    onChangeFocus: (hasFocus: Boolean) -> Unit = {}
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var hasFocus by remember {
        mutableStateOf(false)
    }

    val updatedOnValueChange by rememberUpdatedState(onValueChange)
    val updatedOnFilled by rememberUpdatedState(onFilled)
    val keyboardController = LocalSoftwareKeyboardController.current

    val code by remember(value) {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }


    var fieldIndicatorAnimationValue by remember { mutableStateOf(0f) } // Initial value

    val fieldIndicatorAnimationTarget = animateFloatAsState(
        targetValue = if (hasFocus && showFieldFocusIndicator) 0.05f else 0f, // Target value to animate to
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    LaunchedEffect(Unit) { // Trigger animation on composition
        fieldIndicatorAnimationValue =
            fieldIndicatorAnimationTarget.value // Update state with animated value
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isSystemInDarkTheme()) {
                        Modifier
                    } else Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Transparent,
                                Transparent,
                                Transparent,
                                Gray700.copy(alpha = fieldIndicatorAnimationTarget.value),
                                Transparent,
                                Transparent,
                                Transparent
                            )
                        )
                    )
                )
                .padding(
                    all = PaddingDefault4
                ), contentAlignment = Alignment.Center
        ) {
            val customTextSelectionColors = TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            )

            CompositionLocalProvider(
                LocalTextToolbar provides EmptyTextToolbar,
                LocalTextSelectionColors provides customTextSelectionColors
            ) {
                BasicTextField(modifier = Modifier
                    .focusRequester(focusRequester = focusRequester)
                    .onFocusChanged { state ->
                        onChangeFocus(state.isFocused)
                        hasFocus = state.isFocused
                    }
                    .fillMaxWidth(),
                    value = code,
                    onValueChange = {
                        if (!it.text.isDigitsOnly() || it.text.length > codeLength) return@BasicTextField

                        updatedOnValueChange(it.text)

                        if (it.text.length == codeLength) {
                            updatedOnFilled()
                        }
                    },
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.NumberPassword
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        //keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    enabled = enabled,
                    readOnly = readOnly,
                    decorationBox = {
                        OtpInputDecoration(
                            isPINView = isPINView,
                            code = code.text,
                            aspectRatio = aspectRatio,
                            codeLength = codeLength,
                            enabled = enabled,
                            visualTransformation = visualTransformation,
                            isFocused = hasFocus,
                            showItemFocusIndicator = showFieldItemFocusIndicator,
                            showItemFocusIndicatorEvenTextEntered = showItemFocusIndicatorEvenTextEntered
                        )
                    })
            }
        }
    }

    LaunchedEffect(requestFocus) {
        Log.e("LaunchedEffect", "requestFocus : $requestFocus")
        delay(100)
        keyboardController?.show()
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun OtpInputDecoration(
    modifier: Modifier = Modifier,
    isPINView: Boolean = false,
    aspectRatio: Float,
    code: String,
    codeLength: Int,
    enabled: Boolean,
    visualTransformation: VisualTransformation,
    isFocused: Boolean,
    showItemFocusIndicator: Boolean,
    showItemFocusIndicatorEvenTextEntered: Boolean
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(PaddingDefault16)
        ) {
            for (i in 0 until codeLength) {
                val text = if (i < code.length) code[i].toString() else ""
                val focusedItemIndex = code.length
                OtpEntry(
                    isPINView = isPINView,
                    aspectRatio = aspectRatio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .aspectRatio(1f),
                    text = text,
                    enabled = enabled,
                    visualTransformation = visualTransformation,
                    isItemFocused = focusedItemIndex == i,
                    isLastItem = i == codeLength - 1,
                    isTextFieldFocused = isFocused,
                    showItemFocusIndicator = showItemFocusIndicator,
                    showItemFocusIndicatorEvenTextEntered = showItemFocusIndicatorEvenTextEntered
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun OtpEntry(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    aspectRatio: Float,
    isPINView: Boolean = false,
    visualTransformation: VisualTransformation,
    isItemFocused: Boolean,
    isLastItem: Boolean,
    isTextFieldFocused: Boolean,
    showItemFocusIndicator: Boolean,
    showItemFocusIndicatorEvenTextEntered: Boolean
) {

    val borderColor = if (text.isEmpty()) {
        if (isItemFocused && isTextFieldFocused && showItemFocusIndicator) {
            if (isSystemInDarkTheme()) {
                Gray700
            } else {
                ColorFFC0C0C0
            }
        } else {
            if (isSystemInDarkTheme()) {
                Dark4
            } else {
                Color.Transparent
            }
        }
    } else {
        if (isLastItem && showItemFocusIndicatorEvenTextEntered && isTextFieldFocused) {
            if (isSystemInDarkTheme()) {
                Gray700
            } else {
                ColorFFC0C0C0
            }
        } else {
            if (isSystemInDarkTheme()) {
                PrimaryBlue900
            } else {
                PrimaryBlue900
            }
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .background(
                color = if (isSystemInDarkTheme()) {
                    if (text.isNotEmpty()) AlphaBlue1A else {
                        if (isPINView) Dark3 else Dark2
                    }
                } else {
                    if (text.isNotEmpty()) AlphaBlue1A else Gray100
                }, shape = RoundedCornerShape(RoundedCorner12)
            )
            .border(
                width = BorderWidth1,
                brush = Brush.verticalGradient(colors = listOf<Color>(Transparent, borderColor)),
                shape = RoundedCornerShape(RoundedCorner12)
            ), contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(), targetState = text, transitionSpec = {
                ContentTransform(
                    targetContentEnter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                    initialContentExit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
                    sizeTransform = null
                )
            }, contentAlignment = Alignment.Center, label = "textVisibility"
        ) { text ->
            if (text.isNotBlank()) {
                TextGray900H6Bold_WhiteH6Bold(
                    modifier = Modifier, text = "*", textAlign = TextAlign.Center
                )
            }
        }
    }
}

internal object EmptyTextToolbar : TextToolbar {
    override val status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {}

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?,
    ) {
    }
}
