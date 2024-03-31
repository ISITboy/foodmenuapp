package com.example.gosporttastingtask.ui.presentation.navgraph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gosporttastingtask.R

interface Destination {
    val route: String
    val point: BottomNavigationPoint?
}

object MenuDestination : Destination {
    override val route: String
        get() = "MenuScreen"
    override val point: BottomNavigationPoint
        get() = BottomNavigationPoint(
            icon = R.drawable.go_sport_menu_icon,
            label = R.string.menu_label
        )

}


object ProfileDestination : Destination {
    override val route: String
        get() = "ProfileScreen"
    override val point: BottomNavigationPoint
        get() = BottomNavigationPoint(
            icon = R.drawable.go_sport_profile_icon,
            label = R.string.profile_label
        )
}

object BasketDestination : Destination {
    override val route: String
        get() = "BasketScreen"
    override val point: BottomNavigationPoint
        get() = BottomNavigationPoint(
            icon = R.drawable.go_sport_basket_icon,
            label = R.string.basket_label
        )
}


val tabRowScreens = listOf(
    MenuDestination,
    ProfileDestination,
    BasketDestination
)

data class BottomNavigationPoint(
    @DrawableRes val icon: Int,
    @StringRes val label: Int
)