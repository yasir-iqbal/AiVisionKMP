package com.apps.aivisioncmp.ui.components

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.app_name
import aivisionkmp.composeapp.generated.resources.image_generating
import aivisionkmp.composeapp.generated.resources.square
import aivisionkmp.composeapp.generated.resources.stop_generating
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivisioncmp.ui.theme.Barlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StopGenerateButton(modifier: Modifier, isImageGen: Boolean=false, onClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable (onClick = {
                    if (isImageGen.not()) {
                        onClick()
                    }
                })
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.onSecondary
                )
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.onTertiary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            if (isImageGen.not()) {
                Icon(
                    painter = painterResource(Res.drawable.square),
                    contentDescription = stringResource(Res.string.app_name),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(width = 30.dp, height = 30.dp)


                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(
                text = if (isImageGen) stringResource(Res.string.image_generating) else stringResource(Res.string.stop_generating),
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Barlow,
                    lineHeight = 25.sp
                )
            )
        }
    }
}