package com.apps.aivisioncmp.ui.components


import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.ask_me_anything
import aivisionkmp.composeapp.generated.resources.generate_anything
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivisioncmp.ui.theme.Barlow
import com.apps.aivisioncmp.utils.ConversationType
import com.apps.aivisioncmp.utils.KMPLogger
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


@Composable
fun EditTextField(
    inputText: String,conversationType:String,onTextChange:(String)->Unit, onSendClick:(String)-> Unit
) {
    val logger = remember {   KMPLogger()}
    val isImageGen = conversationType.contentEquals(
        ConversationType.IMAGE.name, true
    )
   // val context = LocalContext.current
    /*val isKeyBoardVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    LaunchedEffect(key1 = isKeyBoardVisible) {
        logger.error("ChatScreen", "iskeyBoard:${isKeyBoardVisible}")
        if (isKeyBoardVisible) {
            //hide fab button
        } else {
            //show fab button
        }
    }*/
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }

    text = inputText

   /* val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val ttsResult = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ttsResult?.let {
                onTextChange(it[0].toString())
            }

        }
    }*/

    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column {
            Divider(
                color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp,
            )
            Box(
                Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Row(Modifier.padding(all = 5.dp), verticalAlignment = Alignment.Bottom) {

                    Row(
                        Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(30.dp)
                            )
                    )
                    {

                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                onTextChange(it)
                            },
                            label = null,
                            placeholder = {
                                Text(
                                   if (isImageGen) stringResource(Res.string.generate_anything) else stringResource(Res.string.ask_me_anything),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontFamily = Barlow,
                                    fontWeight = FontWeight.W600
                                )
                            },
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 16.sp,
                                fontFamily = Barlow,
                                fontWeight = FontWeight.W600
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 50.dp)
                                .heightIn(max = 120.dp)
                                .padding(end = 5.dp)
                                .weight(1f)
                                /*.border(
                                    1.dp,
                                    if (hasFocus) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    RoundedCornerShape(25.dp)
                                )*/
                                .onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
                            shape = RoundedCornerShape(25.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                /*disabledIndicatorColor = Color.Transparent,*/
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent
                            )
                        )

                       /* if (isKeyBoardVisible.not() && text.isEmpty() && conversationType.contentEquals(
                                ConversationType.TEXT.name, true
                            )
                        ) {


                        }*/

                    }

                    IconButton(
                        onClick = {

                            scope.launch {
                                if (text.isNotEmpty()) {
                                    onSendClick(text)
                                   } else {
                                   /* if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                                        Toast.makeText(
                                            context,
                                            "Speech not Available",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        val intent =
                                            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                                        intent.putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                            RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                                        )
                                        intent.putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE,
                                            Locale.getDefault()
                                        )
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk")
                                        launcher.launch(intent)
                                    }*/
                                }
                            }
                        }, modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(90.dp)
                            )

                    ) {
                        Icon(
                            imageVector= if (text.isNotEmpty()) Icons.Rounded.Send else Icons.Rounded.Send,
                            "sendMessage",
                            modifier = Modifier.size(25.dp),
                            tint = White,
                        )
                    }
                }
            }
        }
    }
}