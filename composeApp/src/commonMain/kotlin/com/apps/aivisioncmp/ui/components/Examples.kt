package com.apps.aivisioncmp.ui.components

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.app_name
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apps.aivisioncmp.utils.platformTextStyle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Examples(
    modifier: Modifier = Modifier,
    examples: List<StringResource>,
    image:DrawableResource?=null,
    inputText: String, onInput: (String)->Unit,onAssistants:()->Unit
) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

         Row(modifier = Modifier.clip(RoundedCornerShape(20.dp)).clickable { onAssistants() }.background(color = MaterialTheme.colorScheme.onSecondary,
             shape = RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
             if (image!=null){
                 Image(
                     painter = painterResource(image),
                     contentDescription = stringResource(Res.string.app_name),
                     modifier = Modifier
                         .size(28.dp).padding(3.dp)
                 )
             }
             else{
                 Icon(imageVector =Icons.Default.LightMode , contentDescription ="",modifier=Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurface )
             }

             Spacer(modifier = Modifier.width(8.dp))
          Text(
                text = inputText,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.W700, platformStyle = platformTextStyle().platformStyle
                ),
                textAlign = TextAlign.Center
            )
             Spacer(modifier = Modifier.width(8.dp))
             Icon(imageVector =Icons.Default.ArrowDropDown , contentDescription ="",modifier=Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurface )

         }

            val resolvedExamples = examples.map { stringResource(it) }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(resolvedExamples) { example ->

                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            onClick = {
                                onInput( example.replace("\n", ""))
                            })
                        .background(
                            color = MaterialTheme.colorScheme.onSecondary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)) {
                    Text(
                        text =  example,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        textAlign = TextAlign.Start,
                         modifier= Modifier.weight(1f)

                    )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Rounded.Send, contentDescription = "", tint = MaterialTheme.colorScheme.onSurface)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }


        }
    }
}

