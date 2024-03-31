package com.example.gosporttastingtask.ui.presentation.screens.menu.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gosporttastingtask.ui.presentation.screens.menu.isScrolled

@Composable
fun BannerLayout(images: List<Int>, modifier: Modifier = Modifier,lazyListState : LazyListState) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = rememberLazyListState(),
        modifier = modifier.padding(horizontal = 16.dp).animateContentSize(animationSpec = tween(durationMillis = 300)).height(
            height = if (lazyListState.isScrolled) 0.dp else 112.dp
        )
    ) {
        items(images) { image ->
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 300.dp, height = 112.dp)
            )
        }
    }
}
