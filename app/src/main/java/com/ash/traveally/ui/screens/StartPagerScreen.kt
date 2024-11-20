package com.ash.traveally.ui.screens

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ash.traveally.R
import com.ash.traveally.ui.theme.MontserratAlternates
import com.ash.traveally.utils.Screens.HOME_SCREEN


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartPagerScreen(onClick: () -> Unit) {
    val images = listOf(
        R.drawable.pexels_photo_by_guillaume_meurice,
        R.drawable.pexels_photo_by_pixabay,
        R.drawable.pexels_photo_by_marlene_lepp_nen
    )
    val title = listOf(
        "Explore your \nInterests",
        "Find your \nTravel mate",
        "Plan your \nTrip"
    )

    val pageCount = images.size
    val pagerState = rememberPagerState(pageCount = {pageCount})
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState
        ) {
            Image(
                painter = painterResource(id = images[it]),
                contentDescription = "Tour Images",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) {
                CustomIndicator(selected = pagerState.currentPage == it)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title[pagerState.currentPage],
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Normal,
                    shadow = Shadow(color = Color.Black, offset = Offset.VisibilityThreshold, blurRadius = 10f),
                    color = Color.White
                ),
                modifier = Modifier.padding(start = 15.dp)
            )
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 5.dp)
                    .paint(painter = painterResource(id = R.drawable.ic_tick_button), contentScale = ContentScale.Fit),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {}
        }
        Image(
            painter = painterResource(id = R.drawable.ic_big_logo),
            contentDescription = "Traveally Logo",
            modifier = Modifier
                .padding(50.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CustomIndicator(selected: Boolean) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = if (selected) Color(0xFFB9EB42) else Color.Gray,
                shape = CircleShape
            )
            .size(10.dp)
    )
}