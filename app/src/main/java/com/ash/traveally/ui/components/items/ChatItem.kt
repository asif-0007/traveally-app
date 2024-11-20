package com.ash.traveally.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ash.traveally.R
import com.ash.traveally.models.User
import com.ash.traveally.ui.theme.MontserratAlternates

@Composable
fun ChatItem(
    user: User,
    onItemClick: (User) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(user) }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(user.photoUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)
            )
            Column(modifier = Modifier.padding(top = 8.dp).align(Alignment.Top)) {
                Text(
                    text = user.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = MontserratAlternates
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp, start = 2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(height = 20.dp, width = 20.dp)
                    )
                    Text(
                        text = user.city + ", " + user.country,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = MontserratAlternates
                        ),
                        modifier = Modifier.padding(top = 1.dp)
                    )
                }
            }
        }
    }
}
