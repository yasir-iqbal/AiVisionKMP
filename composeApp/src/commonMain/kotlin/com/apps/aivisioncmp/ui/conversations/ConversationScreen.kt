package com.apps.aivisioncmp.ui.conversations

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.app_icon
import aivisionkmp.composeapp.generated.resources.are_you_sure_delete_all_history
import aivisionkmp.composeapp.generated.resources.confirmation
import aivisionkmp.composeapp.generated.resources.conversations
import aivisionkmp.composeapp.generated.resources.delete
import aivisionkmp.composeapp.generated.resources.generate_image
import aivisionkmp.composeapp.generated.resources.generate_text
import aivisionkmp.composeapp.generated.resources.no_chats
import aivisionkmp.composeapp.generated.resources.outline_chat_24
import aivisionkmp.composeapp.generated.resources.outline_image_24
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.apps.aivisioncmp.data.model.Conversation
import com.apps.aivisioncmp.ui.components.ImageTextButton
import com.apps.aivisioncmp.ui.components.ToolBar
import com.apps.aivisioncmp.ui.components.TopBarSearch
import com.apps.aivisioncmp.ui.dialogs.ConfirmationDialog
import com.apps.aivisioncmp.ui.theme.Barlow
import com.apps.aivisioncmp.utils.ConversationType
import com.apps.aivisioncmp.utils.toFormattedDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject


@Composable
fun ConversationScreen( navigateToChat: ( chatId:Long?,String) -> Unit,viewModel: ConversationViewModel = koinInject()){

    val darkMode by remember { mutableStateOf(false) } /*mainActivityViewModel.darkMode.collectAsState()*/
    val recentChats by viewModel.conversations.collectAsState()
    val isSubMode = true
    val isLoading by viewModel.isLoading.collectAsState()

    var clearConversationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val currentLanguageCode = viewModel.getCurrentLanguageCode()
      /*  Utils.changeLanguage(context, currentLanguageCode)
        Utils.changeLanguage(context.applicationContext, currentLanguageCode)*/
        viewModel.loadThemeMode()
        viewModel.getAllConversations()

    }
   // val activity = LocalContext.current as Activity
    var isSearchBar by remember { mutableStateOf(false) }


 /*   BackHandler(true) {

        if (isSearchBar.not())
        {
            activity.finish()
        }else{
            isSearchBar = false
            viewModel.resetSearch()
        }
    }*/



    if (clearConversationDialog)
    {
        ConfirmationDialog(title = stringResource(Res.string.confirmation), message = stringResource(Res.string.are_you_sure_delete_all_history), onCancel = {
            clearConversationDialog = false
        }) {
            viewModel.clearConversations()
            clearConversationDialog=false
        }
    }

    Box(
        Modifier
            .fillMaxSize()/*.background(MaterialTheme.colorScheme.background)*/
            .padding(bottom = 1.dp)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            if (isSearchBar) {

                TopBarSearch(onSearchText = {

                    viewModel.searchConversation(it)
                }) {
                    isSearchBar = !isSearchBar
                    viewModel.resetSearch()
                }

            } else {
                ToolBar(
                    onClickAction = { },
                    image = Res.drawable.app_icon,
                    text = stringResource(Res.string.conversations),
                    MaterialTheme.colorScheme.onBackground,
                    showDivider = recentChats.isEmpty().not(),
                    optionMenuItems = {
                        if (recentChats.isNotEmpty()){
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        isSearchBar = !isSearchBar
                                    },
                                    modifier = Modifier
                                        .width(27.dp)
                                        .height(27.dp)
                                ) {

                                    Icon(
                                        imageVector =Icons.Filled.Search ,
                                        contentDescription = "image",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .width(27.dp)
                                            .height(27.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(15.dp))
                                IconButton(
                                    onClick = {
                                        clearConversationDialog=true
                                    },
                                    modifier = Modifier
                                        .width(27.dp)
                                        .height(27.dp)
                                ) {

                                    Icon(
                                        imageVector =Icons.Outlined.Delete,
                                        contentDescription = "image",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .width(27.dp)
                                            .height(27.dp)
                                    )
                                }
                            }
                        }

                    }
                )
            }



            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (recentChats.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        /*painter = painterResource(id = if (darkMode) R.drawable.outline_empty else R.drawable.outline_empty_light),*/
                        painter = painterResource( Res.drawable.app_icon),
                        contentDescription = null,
                        modifier = Modifier.size(180.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(Res.string.no_chats),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Barlow,
                            lineHeight = 25.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(top = 15.dp)

                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 12.dp, bottom = 2.dp)
                        .padding(horizontal = 8.dp)
                ) {

                    items(items = recentChats, key = { it.id }) { conversation ->

                        RecentItem(recentItem = conversation, onItemClick = {
                            navigateToChat(
                                it.id,
                                it.type
                            )
                        }){
                            viewModel.deleteConversationById(it.id)
                        }

                    }
                }

            }

        }
        var clickTs by remember {
            mutableStateOf(Clock.System.now().toEpochMilliseconds())
        }

        val margin = 14.dp
        val padding =  7.dp

        Row (
            Modifier
                .fillMaxWidth()
                .padding(bottom = if (recentChats.isEmpty()) 50.dp else 16.dp)
                .align(Alignment.BottomCenter)) {
            ImageTextButton(
                Modifier
                    .weight(1f)
                    .padding(start = margin, end = padding),text = stringResource( Res.string.generate_text), imageId = Res.drawable.outline_chat_24, isDarkMode = darkMode) {
                val currentTs = Clock.System.now().toEpochMilliseconds()
                if (currentTs-clickTs<1001)
                {
                    return@ImageTextButton
                }
                clickTs = currentTs

                navigateToChat(
                    null,
                    ConversationType.TEXT.name
                )
            }


            ImageTextButton(
                    Modifier
                        .weight(1f)
                        .padding(start = padding, end = margin/*14.dp*/),
                    text = stringResource(Res.string.generate_image),
                    imageId = Res.drawable.outline_image_24,
                    isDarkMode = darkMode
                ) {
                    navigateToChat(
                        null,
                        ConversationType.IMAGE.name
                    )
                }
        }
    }


}

@Composable
fun RecentItem(recentItem : Conversation, onItemClick:(Conversation)->Unit, onDelete:(Conversation)->Unit)
{
    val scope = rememberCoroutineScope()
    val currentItem by rememberUpdatedState(recentItem)

    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .indication(interactionSource, LocalIndication.current)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onSecondary,
                RoundedCornerShape(12.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        expanded = true
                    }, onTap = {
                        scope.launch {
                            delay(100)
                            onItemClick(currentItem)
                        }
                    }, onPress = { offset ->
                        //tapped = true

                        val press = PressInteraction.Press(offset)
                        interactionSource.emit(press)

                        tryAwaitRelease()

                        interactionSource.emit(PressInteraction.Release(press))

                        //tapped = false

                    }
                )
            }
            /* .border(
                 2.dp,
                 MaterialTheme.colorScheme.onTertiary,
                 RoundedCornerShape(16.dp)
             )*/
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
            /* .padding(start = 8.dp, top = 4.dp, bottom = 6.dp)*/
        ) {
            Text(
                text = currentItem.title.replaceFirstChar { it.uppercase() },
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    /*fontFamily = Barlow,*/

                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row (verticalAlignment = Alignment.CenterVertically){

                if (currentItem.type.contentEquals(ConversationType.IMAGE.name) && currentItem.content.isNotEmpty())
                {
                   /* GlideImage(
                        imageModel = {currentItem.content},
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop
                        ),
                        modifier = Modifier
                            .size(25.dp)
                            .clip(
                                RoundedCornerShape(5.dp)
                            )
                    )
*/
                    Spacer(modifier = Modifier.width(5.dp))
                }

                val content = if (currentItem.content.isNotEmpty()) currentItem.content else currentItem.createdAt.toFormattedDate()

                Text(
                    text = if (currentItem.type.contentEquals(ConversationType.IMAGE.name)) "Image" else content/*createdAt.toFormattedDate()*/,
                    /* color = MaterialTheme.colorScheme.onSurface,*/
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        /* fontFamily = Barlow,*/

                    ), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
        }
        Icon(
            imageVector = Icons.Rounded.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(start = 5.dp)
                .size(23.dp)
        )


        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(surface = MaterialTheme.colorScheme.background),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(6.dp))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onSecondary,
                        RoundedCornerShape(6.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onTertiary,
                        RoundedCornerShape(6.dp)
                    ),
                properties = PopupProperties(focusable = false)
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onDelete(currentItem)
                    },
                    text = {
                        Text(
                            text = stringResource(Res.string.delete),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            "DeleteConversation",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                )

            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
    //Divider( color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp)
}
