package com.example.gosporttastingtask.ui.presentation.screens.menu.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gosporttastingtask.ui.domain.models.ModelProduct


@Composable
fun ProductCardsLayout(
    products:List<ModelProduct>,
    modifier: Modifier=Modifier,
    lazyListState: LazyListState
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState,
        modifier = modifier,
    ){
        items(products){product->
            ProductCardItem(product)
        }
    }
}

@Composable
fun ProductCardItem(
    product: ModelProduct,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 151.dp)
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(product.image).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(135.dp)
                .clip(MaterialTheme.shapes.medium),
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = product.ingredients.toString(), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.defaultMinSize(minHeight = 50.dp)
                .padding(3.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .clickable { expanded = !expanded },
                maxLines = if (expanded) 3 else 6

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(6.dp),
                    colors=ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = Color.Red
                    ),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 105.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .border(width = 1.dp, shape = RoundedCornerShape(6.dp), color = Color.Red)
                ) {
                    Text(text = "от 375 р")
                }
            }

        }
    }
}