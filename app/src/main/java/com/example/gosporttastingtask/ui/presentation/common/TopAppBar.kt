package com.example.gosporttastingtask.ui.presentation.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gosporttastingtask.R
import com.example.gosporttastingtask.ui.domain.connectivity.ConnectivityObserver

@Composable
fun TopBar(status: ConnectivityObserver.Status) {
    val cities = listOf("Москва", "Минск", "Гродно", "Пружаны", "Колядичи")
    var selectedCities by rememberSaveable { mutableStateOf(cities.first()) }
    var dropdownMenuOpen by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (dropdownMenuOpen) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = selectedCities)
        IconButton(onClick = { dropdownMenuOpen = true }) {
            Icon(
                painter = painterResource(id = R.drawable.keyboard_arrow_down),
                contentDescription = null,
                Modifier.rotate(rotation)
            )
        }
        DropdownMenu(
            expanded = dropdownMenuOpen, onDismissRequest = { dropdownMenuOpen= false },
            modifier = Modifier.fillMaxWidth(0.25f)
        ) {
            cities.forEach { city ->
                DropdownMenuItem(text = { Text(text = city) }, onClick = {
                    selectedCities = city
                    dropdownMenuOpen= false
                })
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (status != ConnectivityObserver.Status.Available) Text(text = "No internet", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        IconButton(onClick = {  }) {
            Icon(painter = painterResource(id = R.drawable.qr_code), contentDescription = null)
        }
    }
}
