package com.apps.aivisioncmp.ui.components

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.app_name
import aivisionkmp.composeapp.generated.resources.image_1
import aivisionkmp.composeapp.generated.resources.image_2
import aivisionkmp.composeapp.generated.resources.image_3
import aivisionkmp.composeapp.generated.resources.image_4
import aivisionkmp.composeapp.generated.resources.image_5
import aivisionkmp.composeapp.generated.resources.image_6
import aivisionkmp.composeapp.generated.resources.image_7
import aivisionkmp.composeapp.generated.resources.image_8
import aivisionkmp.composeapp.generated.resources.image_9
import aivisionkmp.composeapp.generated.resources.image_10
import aivisionkmp.composeapp.generated.resources.image_inp_1
import aivisionkmp.composeapp.generated.resources.image_inp_2
import aivisionkmp.composeapp.generated.resources.image_inp_3
import aivisionkmp.composeapp.generated.resources.image_inp_4
import aivisionkmp.composeapp.generated.resources.image_inp_5
import aivisionkmp.composeapp.generated.resources.image_inp_6
import aivisionkmp.composeapp.generated.resources.image_inp_7
import aivisionkmp.composeapp.generated.resources.image_inp_8
import aivisionkmp.composeapp.generated.resources.image_inp_9
import aivisionkmp.composeapp.generated.resources.image_inp_10
import aivisionkmp.composeapp.generated.resources.image_try
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.aivisioncmp.data.model.ImageExample
import com.apps.aivisioncmp.ui.theme.Barlow
import com.apps.aivisioncmp.utils.platformTextStyle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageExamples(
    modifier: Modifier = Modifier,
    inputText: String, onInput: (String)->Unit
) {

    val data = mutableListOf(
        ImageExample(Res.drawable.image_1, stringResource(Res.string.image_inp_1)),
        ImageExample(Res.drawable.image_2,stringResource( Res.string.image_inp_2)),
        ImageExample(Res.drawable.image_3,stringResource(Res.string.image_inp_3)),
        ImageExample(Res.drawable.image_4,stringResource(Res.string.image_inp_4)),
        ImageExample(Res.drawable.image_5,stringResource(Res.string.image_inp_5)),
        ImageExample(Res.drawable.image_6,stringResource(Res.string.image_inp_6)),
        ImageExample(Res.drawable.image_7,stringResource(Res.string.image_inp_7)),
        ImageExample(Res.drawable.image_8,stringResource(Res.string.image_inp_8)),
        ImageExample(Res.drawable.image_9,stringResource(Res.string.image_inp_9)),
        ImageExample(Res.drawable.image_10,stringResource(Res.string.image_inp_10))
    )

    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

         Row {
             Icon(imageVector =Icons.Default.LightMode , contentDescription ="",modifier=Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurface )
             Spacer(modifier = Modifier.width(8.dp))
          Text(
                text = inputText,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.W700, platformStyle = platformTextStyle().platformStyle
                ),
                textAlign = TextAlign.Center
            )
         }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                items(data)
                {
                    ImageItem(item = it,onInput)
                }
            }



        }
    }
}


@Composable
fun ImageItem(item: ImageExample, onTry:(String)->Unit)
{
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
            horizontalAlignment = Alignment.Start,

        ) {
            Box {
            Image(
                painter = painterResource(item.resId),
                contentDescription = stringResource(Res.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3.6f)
                    .clip(
                        RoundedCornerShape(
                            15.dp
                        )
                    )
                    .clickable(
                        onClick = {
                            onTry(item.text)
                        }),
                contentScale = ContentScale.Crop
            )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .align(Alignment.BottomEnd)
                        ,
                    text = stringResource( Res.string.image_try),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Barlow
                    ),
                    textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis,

                    )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(modifier = Modifier.fillMaxWidth(),
                text = item.text,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = Barlow
                ), textAlign = TextAlign.Center, maxLines = 2,overflow = TextOverflow.Ellipsis
            )
        }

}

