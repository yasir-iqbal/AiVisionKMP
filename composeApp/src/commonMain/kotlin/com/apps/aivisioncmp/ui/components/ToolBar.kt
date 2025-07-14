package com.apps.aivisioncmp.ui.components

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.app_icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivisioncmp.ui.theme.Barlow
import com.apps.aivisioncmp.ui.theme.CreditsTint
import com.apps.aivisioncmp.utils.click
import com.apps.aivisioncmp.utils.platformTextStyle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ToolBar(
    onClickAction: () -> Unit,
    image: DrawableResource?,
    text: String,
    tint: Color,
    showDivider:Boolean = true,
    optionMenuItems: (@Composable () -> Unit)? = null
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
    {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp).align(Alignment.Center),
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(
                fontWeight = FontWeight.W600,
                fontSize = 17.sp,
                fontFamily = Barlow,
                textAlign = TextAlign.Center, platformStyle = platformTextStyle().platformStyle
            )
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                IconButton(
                    onClick = onClickAction,
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                ) {

                    Icon(
                        painter = painterResource(it),
                        contentDescription = "image",
                        tint = tint,
                        modifier = Modifier
                            .width(27.dp)
                            .height(27.dp)
                    )
                }
            }
            Spacer(modifier = Modifier
                .weight(1f)
                .height(25.dp))

            if (optionMenuItems != null) {
                optionMenuItems()
            }

        }

        if (showDivider)
        {   Divider(
            color = MaterialTheme.colorScheme.tertiary, thickness = 0.8.dp, modifier = Modifier.align(
                Alignment.BottomEnd)
        )
        }
    }


}

@Composable
fun ToolBarChat(
    onClickAction: () -> Unit,
    onStyleAction: () -> Unit,
    image: DrawableResource?,
    text: String,
    tint: Color,
    showDivider:Boolean = true,
    creditsCount:Int=0,
    isStyleMode:Boolean = false,
    isSubMode:Boolean = false,
    isPremium:Boolean = false
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
    {
        if (isStyleMode) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .click { onStyleAction() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                        fontFamily = Barlow,
                        textAlign = TextAlign.Center
                    )
                )

                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "image",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
        }else{
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    fontFamily = Barlow,
                    textAlign = TextAlign.Center
                )
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                IconButton(
                    onClick = onClickAction,
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                ) {

                    Icon(
                        painter = painterResource(it),
                        contentDescription = "image",
                        tint = tint,
                        modifier = Modifier
                            .width(27.dp)
                            .height(27.dp)
                    )
                }
            }
            Spacer(modifier = Modifier
                .weight(1f)
                .height(27.dp))



        }


     /*   Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onTertiary,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .padding(horizontal = 9.dp, vertical = 3.dp)
                    .click {
                        //navigateToCredits()
                    }
            ) {
                if (isSubMode && isPremium) {
                    Icon(
                        painter = painterResource(Res.drawable.app_icon),
                        contentDescription = "image",
                        tint = CreditsTint,
                        modifier = Modifier
                            .width(27.dp)
                            .height(27.dp)
                            .padding(end = 5.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.app_icon),
                        contentDescription = "image",
                        tint = CreditsTint,
                        modifier = Modifier
                            .width(27.dp)
                            .height(27.dp)
                            .padding(end = 3.dp)
                    )
                    Text(
                        text = creditsCount.toString(),
                        color = MaterialTheme.colorScheme.primary,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Barlow,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }*/

        if (showDivider)
        {   Divider(
            color = MaterialTheme.colorScheme.tertiary, thickness = 0.8.dp, modifier = Modifier.align(
                Alignment.BottomEnd)
        )
        }
    }


}