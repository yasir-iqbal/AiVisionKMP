package com.apps.aivisioncmp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ZoomIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apps.aivisioncmp.data.model.ChatMessage
import com.apps.aivisioncmp.data.model.GPTRole
import com.apps.aivisioncmp.utils.ConversationType
import com.apps.aivisioncmp.utils.KMPLogger
import com.apps.aivisioncmp.utils.PlatformAdaptiveText
import com.apps.aivisioncmp.utils.decodeBase64ToImageBitmap
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
private val TAG = "MessageBubble"

@Composable
fun MessageBubble(message: ChatMessage, onImage:(String)->Unit) {
    val logger = KMPLogger()
    val isUSERMsg = message.role.contentEquals(GPTRole.USER.value)
    val topStart = if (message.url.isNotEmpty() || isUSERMsg) 12.dp else 0.dp
    val bottomEnd = if (isUSERMsg) 0.dp else 12.dp

    val isImage = message.type.contentEquals(ConversationType.IMAGE.name)

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth())
    {

        Box(
            modifier = Modifier
                .align(if (isUSERMsg) Alignment.TopEnd else Alignment.TopStart)
                .widthIn(0.dp,if (message.url.isNotEmpty()) { if (message.url.contains("PDF")) 300.dp else 250.dp} else Dp.Unspecified)
                .padding(end = if (isUSERMsg) 0.dp else 10.dp)
                .padding(start = if (isUSERMsg) 10.dp else 0.dp)
                .padding(vertical = 8.dp)
                .background(
                    if (isUSERMsg) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        topStart = topStart,
                        bottomEnd = bottomEnd,
                        bottomStart = 12.dp
                    )
                )
                .pointerInput(Unit) {
                    if (isImage.not() || isUSERMsg) {
                        detectTapGestures(
                            onLongPress = {
                                expanded = true
                            }
                        )
                    }
                }
            ,
        ) {
            //logger.error("List","Item:${message.id}")
            if (isUSERMsg){
                Column {
                    Text(
                        text = message.content,
                        color = White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }else {

                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground/*, fontWeight  = FontWeight.W600*/)) {
                  //  logger.error("MessageBuble","content:${message.content}")
                    PlatformAdaptiveText(message.content)
                }


                if (message.url.isNotEmpty() && isImage) {
                   /* Spacer(modifier = Modifier
                        .size(250.dp))*/
                    logger.debug(TAG,"image:${message.url}")
                    KamelImage(
                        asyncPainterResource("${message.url}"),
                        contentDescription ="image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp)
                            .padding(2.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 10.dp,
                                    bottomStart = 10.dp,
                                    bottomEnd = 10.dp
                                )
                            ),
                        onLoading = { logger.debug(TAG,"loading started")},
                        onFailure = {logger.error(TAG,"${it.message}")
                            it.printStackTrace()})
                   /* var imageBitmap by remember(message.url) {
                        mutableStateOf<ImageBitmap?>(null)
                    }

                    val scope = rememberCoroutineScope()

                    LaunchedEffect(message.url) {
                        scope.launch {
                            val decoded = decodeBase64ToImageBitmap(message.url)
                            logger.error(TAG,"base64:${message.url}")
                            if (decoded != null) {
                                logger.error(TAG,"Decoded")
                                imageBitmap = decoded
                            }
                        }
                    }

                    imageBitmap?.let {
                        Image(
                            painter = BitmapPainter(it),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                    }*/

                    /*if (message.status == DownloadStatusEnum.COMPLETED.value) {
                        GlideImage(
                            imageModel = { message.url },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop
                            ),
                            modifier = Modifier
                                .size(250.dp)
                                .padding(2.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 10.dp,
                                        topEnd = 10.dp,
                                        bottomStart = 10.dp,
                                        bottomEnd = 10.dp
                                    )
                                )
                        )


                        Image(imageVector = Icons.Outlined.ZoomIn, contentDescription = null,
                            Modifier
                                .padding(5.dp)
                                .align(Alignment.TopEnd)
                                .background(OnSurfaceDark, RoundedCornerShape(8.dp))
                                .padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 8.dp)
                                .click {
                                    onImage(message.url)
                                })
                    }
                    if (message.status == DownloadStatusEnum.DOWNLOADING.value)
                    {
                        CircularProgressIndicator(modifier = Modifier
                            .then(Modifier.size(32.dp))
                            .align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary)
                    }*/

                }

            }

           /* MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(surface = MaterialTheme.colorScheme.background),
                shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(8))
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onTertiary,
                            RoundedCornerShape(8.dp)
                        ),
                    properties = PopupProperties(focusable = false)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            clipboardManager.setText(AnnotatedString((message.content.trimIndent())))
                            expanded = false
                        }, text = {
                            Text(
                                text = stringResource(R.string.copy),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }, leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                "DeleteConversation",
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp,
                    )
                    DropdownMenuItem(
                        onClick = {
                            context.startActivity(shareIntent)
                            expanded = false
                        }
                        , text = {
                            Text(
                                text = stringResource(R.string.share),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }, leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                "share Icon",
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    )

                }
            }*/
        }


    }
}




