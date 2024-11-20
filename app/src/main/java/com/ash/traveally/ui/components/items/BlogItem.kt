package com.ash.traveally.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ash.traveally.R
import com.ash.traveally.models.Blog
import com.ash.traveally.ui.components.buttons.LikeButton
import com.ash.traveally.ui.theme.MontserratAlternates

@Composable
fun BlogItem(
    blog: Blog,
    onItemClick: (Blog) -> Unit,
    onLikeClick: (Blog) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.clickable { onItemClick(blog) }
    ) {
        Row(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(height = 32.dp, width = 32.dp)
            )
            Text(
                text = blog.city + ", " + blog.country,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = MontserratAlternates
                ),
                modifier = Modifier.padding(start = 2.dp, top = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = blog.author!!.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = MontserratAlternates
                ),
                modifier = Modifier.padding(top = 10.dp, end = 4.dp)
            )
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(blog.author!!.photoUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)
            )
        }
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(blog.placePhoto)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        )
        LikeButton(onClick = onLikeClick, modifier = Modifier.padding(start = 4.dp), blog = blog)
        Text(
            text = blog.likes.toString() + " likes",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(start = 12.dp)
        )
        Text(
            text = blog.title,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.W600
            ),
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
        )
    }
}