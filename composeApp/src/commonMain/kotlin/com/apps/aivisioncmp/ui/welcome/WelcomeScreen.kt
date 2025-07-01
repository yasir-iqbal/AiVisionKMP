package com.apps.aivisioncmp.ui.welcome

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.app_icon
import aivisionkmp.composeapp.generated.resources.app_name
import aivisionkmp.composeapp.generated.resources.continue_guest
import aivisionkmp.composeapp.generated.resources.google_signin_error
import aivisionkmp.composeapp.generated.resources.or
import aivisionkmp.composeapp.generated.resources.policy_text
import aivisionkmp.composeapp.generated.resources.powered_by
import aivisionkmp.composeapp.generated.resources.privacy_policy
import aivisionkmp.composeapp.generated.resources.sign_in_with_google
import aivisionkmp.composeapp.generated.resources.terms_service
import aivisionkmp.composeapp.generated.resources.user_continue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivisioncmp.ui.components.GoogleLoginButton
import com.apps.aivisioncmp.ui.components.NormalLoginButton
import com.apps.aivisioncmp.utils.SignInType
import com.apps.aivisioncmp.ui.theme.Barlow
import com.apps.aivisioncmp.ui.theme.Montserrat
import com.apps.aivisioncmp.ui.theme.stronglyDeemphasizedAlpha
import com.apps.aivisioncmp.utils.Constants
import com.apps.aivisioncmp.utils.KMPLogger
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
private val logger = KMPLogger()
@Composable
fun WelcomeScreen(navigateToConversation:() -> Unit,viewModel: WelcomeScreenViewModel = koinInject()){

    var loginError = viewModel.authError
    val signInResult by viewModel.loginSuccess.collectAsState()

    LaunchedEffect(signInResult)
    {
        if (signInResult)
        {
            logger.debug("WelcomeScreen","signin trigger with coroutine")
           // Log.e(TAG,"signin trigger with coroutine")
           navigateToConversation()
        }

    }

    WelcomeUI(onGoogleButtonClick = {
        if (!viewModel.isProcessing)
        {
            loginError = false
            viewModel.updateProcessingState(true)
            viewModel.authenticateWithToken("")
            //viewModel.continueWithGuest()
        }
    }, onNormalButtonClick = {
        if (!viewModel.isProcessing)
        {
            loginError = false
            viewModel.updateProcessingState(true)
            viewModel.loginWithEmailAndPass()
            //viewModel.continueWithGuest()
        }
    },viewModel.isProcessing,loginError)
}

@Composable
fun WelcomeUI(onGoogleButtonClick:()->Unit,onNormalButtonClick:()->Unit,isProcessing:Boolean,isLoginError:Boolean)
{
    Box(modifier  = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background) ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp, bottom = 50.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Image(
                painterResource(Res.drawable.app_icon),
                contentDescription = stringResource(Res.string.app_name),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(180.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))

            Text(modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(Res.string.app_name),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(imageVector = Icons.Outlined.Bolt, contentDescription ="powered by icon",colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                ) )
                Text(
                    text = stringResource(Res.string.powered_by),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

            }



            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                if (Constants.SignInMode == SignInType.Both || Constants.SignInMode == SignInType.Gmail) {
                    Spacer(modifier = Modifier.height(25.dp))
                    GoogleLoginButton(
                        text = stringResource( Res.string.sign_in_with_google),
                        onClick = onGoogleButtonClick
                    )
                }
                if (Constants.SignInMode == SignInType.Both ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource( Res.string.or),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha),
                    )
                }

                if (Constants.SignInMode == SignInType.Both || Constants.SignInMode == SignInType.Guest) {
                    Spacer(modifier = Modifier.height(20.dp))
                    NormalLoginButton(
                        text = stringResource(if (Constants.SignInMode == SignInType.Guest) Res.string.user_continue else Res.string.continue_guest),
                        onClick = onNormalButtonClick
                    )
                }

                if (isProcessing)
                {
                    Spacer(modifier = Modifier.height(12.dp))
                    CircularProgressIndicator()
                }
                if (isLoginError)
                {
                    Spacer(modifier = Modifier.height(15.dp))
                    TextFieldError(textError = stringResource( Res.string.google_signin_error))
                }
            }

        }

        Column(Modifier.align(Alignment.BottomEnd)) {
            Divider( modifier = Modifier
                .padding(start = 4.dp)
                .padding(end = 4.dp),
                color = MaterialTheme.colorScheme.tertiary, thickness = 1.dp,
            )
            PolicyText()
        }
    }
}

@Composable
fun PolicyText() {
    val uriHandler = LocalUriHandler.current
    val terms = stringResource(resource = Res.string.terms_service)
    val privacy = stringResource(resource = Res.string.privacy_policy)

    val annotatedString = buildAnnotatedString {
        append("${stringResource(resource = Res.string.policy_text)} ")
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = terms, annotation = terms)
            append(terms)
        }
        append(" & ")
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = privacy, annotation = privacy)
            append(privacy)
        }
    }
    ClickableText(text = annotatedString,style = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    ), modifier = Modifier.padding(5.dp), onClick ={ offset->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.let { span ->
                if (span.item.contentEquals(terms))
                {
                    runCatching {
                        uriHandler.openUri("")
                    }.onFailure { it.printStackTrace() }
                }
                else
                {
                    runCatching {
                        uriHandler.openUri("")
                    }.onFailure { it.printStackTrace() }
                }
            }
    } )

    // Text(text = annotatedString,style = MaterialTheme.typography.body1, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}

@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}