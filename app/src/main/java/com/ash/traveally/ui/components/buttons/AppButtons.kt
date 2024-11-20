package com.ash.traveally.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ash.traveally.R
import com.ash.traveally.models.Blog
import com.ash.traveally.models.Place
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates

@Composable
fun FullWidthButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(12.dp))
    ) {
        Text(
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.W500
            ),
            color = Color.White,
            text = text
        )
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .height(60.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(12.dp))
    ) {
        Text(
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.W500
            ),
            color = Color.White,
            text = text
        )
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(4.dp)
        )
    }
}

@Composable
fun LikeButton(modifier: Modifier = Modifier, onClick: (Place) -> Unit, place: Place) {
    IconButton(
        onClick = { onClick(place) },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = if (place.isFavourite) R.drawable.ic_favorite else R.drawable.ic_favourite_outline),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(4.dp)
            ,
            tint = Color.Red
        )
    }
}

@Composable
fun LikeButton(modifier: Modifier = Modifier, onClick: (Blog) -> Unit, blog: Blog) {
    IconButton(
        onClick = { onClick(blog) },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = if (blog.isFavourite) R.drawable.ic_favorite else R.drawable.ic_favourite_outline),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(4.dp)
            ,
            tint = Color.Red
        )
    }
}

@Composable
fun SaveButton(modifier: Modifier = Modifier, onClick: (Blog?) -> Unit, blog: Blog?) {
    IconButton(
        onClick =  { onClick(blog) },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = if (blog != null && blog.isSaved) R.drawable.ic_bookmark else R.drawable.ic_bookmark_outline),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(4.dp)
            ,
        )
    }
}

@Composable
fun CallButton(modifier: Modifier = Modifier) {
    IconButton(
        onClick = {  },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_call),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(4.dp)
        )
    }
}

@Composable
fun SendButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_send),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .background(color = Color.Transparent,shape = CircleShape)
                .padding(4.dp)
        )
    }
}