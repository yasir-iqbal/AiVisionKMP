package com.apps.aivisioncmp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivisioncmp.ui.theme.Barlow
import com.apps.aivisioncmp.ui.theme.CodeBackground
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.CodeBlockStyle
import com.halilibo.richtext.ui.InfoPanelStyle
import com.halilibo.richtext.ui.RichTextStyle
import com.halilibo.richtext.ui.TableStyle
import com.halilibo.richtext.ui.material3.Material3RichText

actual fun platformTextStyle(): TextStyle = TextStyle(
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)

@Composable
actual fun PlatformAdaptiveText(content: String) = @androidx.compose.runtime.Composable {

    Material3RichText(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
        style = RichTextStyle(
            codeBlockStyle = CodeBlockStyle(
                textStyle = TextStyle(
                    fontFamily = Barlow,
                    fontSize = 14.sp,
                    color = White
                ),
                wordWrap = true,
                modifier = Modifier.background(
                    shape = RoundedCornerShape(6.dp),
                    color = CodeBackground,
                )
            ),
            tableStyle = TableStyle(borderColor = MaterialTheme.colorScheme.onBackground),
            infoPanelStyle = InfoPanelStyle(textStyle = { TextStyle(color = MaterialTheme.colorScheme.primary) }),

            )
    ) {
        Markdown(
            content = content.trimIndent()
        )
    }
}