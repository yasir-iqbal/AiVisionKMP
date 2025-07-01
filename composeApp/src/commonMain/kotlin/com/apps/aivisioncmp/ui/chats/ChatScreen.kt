package com.apps.aivisioncmp.ui.chats

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.examples
import aivisionkmp.composeapp.generated.resources.generate_image
import aivisionkmp.composeapp.generated.resources.generate_text
import aivisionkmp.composeapp.generated.resources.image_inspirations
import aivisionkmp.composeapp.generated.resources.round_arrow_back_24
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apps.aivisioncmp.ui.components.Examples
import com.apps.aivisioncmp.ui.components.ImageExamples
import com.apps.aivisioncmp.ui.components.ToolBarChat
import com.apps.aivisioncmp.utils.ConversationType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import androidx.compose.foundation.lazy.items
import com.apps.aivisioncmp.ui.components.EditTextField
import com.apps.aivisioncmp.ui.components.MessageBubble
import com.apps.aivisioncmp.ui.components.StopGenerateButton

private const  val ANIMATION_DURATION = 50
@Composable
fun ChatScreen(navigateToBack: () -> Unit,
                    data: ChatData,
                    viewModel: ChatViewModel = koinInject())
{

    val messages by viewModel.messages.collectAsState()
    val isAiProcessing by viewModel.isAiProcessing.collectAsState()
    val conversationType by viewModel.currentConversationType.collectAsState()
    val displayMode by viewModel.displayType.collectAsState()
    val examples by viewModel.examples.collectAsState()
    val isSubMode = true
    val title = viewModel.title.value
    val isImageLoadingFailed = remember { mutableStateOf(false) }
    var inputText by remember {
        mutableStateOf("")
    }
    var imageUrlForDetail by remember {
        mutableStateOf("")
    }
    var showImageDetailSheet by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        viewModel.initWithArg(data)
    }

    val listBottomPadding = animateDpAsState(
        if (isAiProcessing) {
            100.dp
        } else {
            0.dp
        },
        animationSpec = tween(ANIMATION_DURATION), label = ""
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // toolbar
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            //val isStyleMode = viewModel.generationModel == GenerationModel.STABILITY && conversationType == ConversationType.IMAGE
            ToolBarChat(
                onClickAction = {
                    viewModel.cancelMessageJob()
                    navigateToBack()
                },
                onStyleAction = { },
                image = Res.drawable.round_arrow_back_24,
                text = if (conversationType == ConversationType.TEXT) {
                    stringResource(Res.string.generate_text)
                } else {
                        stringResource(Res.string.generate_image)
                }  ,
                MaterialTheme.colorScheme.onBackground
            )

        }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {

                if (displayMode == DisplayType.EXAMPLE) {

                    if (conversationType == ConversationType.TEXT) {
                        Examples(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            examples = examples,
                            inputText = if (title.isEmpty()) stringResource( Res.string.examples) else title,
                            onInput = { inputText = it }) {

                        }
                    } else {

                        ImageExamples(inputText = stringResource( Res.string.image_inspirations), onInput ={inputText=it} )
                    }
                } else {


                    val lazyListState = rememberLazyListState()
                    LaunchedEffect(messages.size) {
                        lazyListState.scrollToItem(0)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(bottom = listBottomPadding.value)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(top = 120.dp, bottom = 8.dp),
                        reverseLayout = true,
                        state = lazyListState
                    ) {
                        items(items = messages,
                            key = { message -> message.id }) { message ->
                            MessageBubble(message = message, onImage = {
                                imageUrlForDetail = it
                                showImageDetailSheet = true
                            })
                        }
                    }


                }

                Column(
                    Modifier
                        .fillMaxHeight()
                ) {

                    Spacer(modifier = Modifier.weight(1f))
                    AnimatedVisibility(
                        visible = isAiProcessing,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ),
                        content = {
                            StopGenerateButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                isImageGen = false
                            ) {
                                viewModel.stopAIContentGeneration()
                            }
                        })
                }

             


            }





       /* NoCreditsInfoBottomSheet(
            showSheet = showNoCreditsBottomSheet ,
            minCreditsRequired = minCreditsRequired ,
            onDismiss = { showNoCreditsBottomSheet=false },
            onUpgrade = { navigateToPremium()
            })*/



      /*  ImageDetailSheet(mediaPath = imageUrlForDetail, showSheet = showImageDetailSheet, onDismiss = {
            showImageDetailSheet = false
            imageUrlForDetail=""
            if (isPremium.not())
            {
                displayIntersAd(context)
            }
        })*/


        EditTextField(inputText = inputText,conversationType=conversationType.name, onTextChange = {
            inputText = it
        }, onSendClick = {
                userText->
            if (userText.isNotEmpty()) {

                /*if (Utils.isConnectedToNetwork(context).not()) {
                    Toast.makeText(context,context.getString(R.string.no_conection_try_again),
                        Toast.LENGTH_LONG).show()
                    return@EditTextField
                }*/


                if (isAiProcessing.not() && userText.isNotBlank())
                {
                    viewModel.sendMessage(userText)
                    inputText = ""
                }

            }
        })
    }
}