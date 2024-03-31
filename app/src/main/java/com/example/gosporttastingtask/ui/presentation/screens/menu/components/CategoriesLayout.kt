package com.example.gosporttastingtask.ui.presentation.screens.menu.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gosporttastingtask.ui.domain.models.ModelCategory
import com.example.gosporttastingtask.ui.presentation.theme.BackgroundColorCategoryItemCard
import com.example.gosporttastingtask.ui.presentation.theme.ColorTextCategorySelectedItemCard
import com.example.gosporttastingtask.ui.presentation.theme.ColorTextCategoryUnSelectedItemCard

@Composable
fun CategoriesLayout(
    categories: List<ModelCategory>,
    selectedCategory: MutableState<ModelCategory?>,
    modifier: Modifier = Modifier
) {


    LazyRow(
        state = rememberLazyListState(),
        modifier = modifier.padding(horizontal = 16.dp)
    ){
        items(categories){category->
            CategoriesItem(
                category = category,
                isSelected = category == selectedCategory.value,
                onCategorySelected = {
                    if(selectedCategory.value == category) selectedCategory.value = ModelCategory(name = "%")
                    else selectedCategory.value = category
                }
            )
        }
    }
}

@Composable
fun CategoriesItem(
    category: ModelCategory,
    isSelected: Boolean,
    onCategorySelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) BackgroundColorCategoryItemCard else Color.White
    val textColor = if (isSelected) ColorTextCategorySelectedItemCard else ColorTextCategoryUnSelectedItemCard



    Card(
        modifier = modifier
            .height(35.dp)
            .padding(horizontal = 10.dp)
            .clickable { onCategorySelected() },
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = backgroundColor,
            contentColor =  textColor,
        )
    ){
            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().defaultMinSize(minWidth = 88.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    modifier=Modifier.padding(5.dp),
                    text = category.name,
                    color = textColor,
                )
            }
    }

}