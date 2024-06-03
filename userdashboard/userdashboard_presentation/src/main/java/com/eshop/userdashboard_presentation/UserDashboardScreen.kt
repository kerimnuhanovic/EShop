package com.eshop.userdashboard_presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.UiEvent
import kotlin.math.roundToInt

@Composable
fun UserDashboardScreen(
    viewModel: UserDashboardViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            if (event is UiEvent.Navigate) {
                onNavigate(event)
            }
        }
    }

    UserDashboardScreenContent(state = state, onEvent = viewModel::onEvent, onNavigate = onNavigate)
}

@Composable
private fun UserDashboardScreenContent(
    state: UserDashboardState,
    onEvent: (UserDashboardEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current

    Scaffold(
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomBarItem(
                        text = "Products",
                        icon = Icons.Rounded.List,
                        route = Route.PRODUCTS_OVERVIEW
                    ),
                    BottomBarItem(
                        text = "Shops",
                        iconId = R.drawable.shopping_basket_24,
                        route = Route.SHOPS_OVERVIEW
                    ),
                    BottomBarItem(
                        text = "Message",
                        iconId = R.drawable.message_24,
                        route = Route.CONVERSATIONS
                    ),
                    BottomBarItem(
                        text = "Basket",
                        icon = Icons.Rounded.ShoppingCart,
                        route = Route.BASKET
                    ),
                    BottomBarItem(
                        text = "Board",
                        icon = Icons.Rounded.Settings,
                        route = Route.DASHBOARD
                    )
                ),
                isBottomBarOverlapped = false,
                onNavigate = onNavigate,
                currentDestination = Route.DASHBOARD
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            state.items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensions.spaceMedium)
                        .clickable {
                            onEvent(UserDashboardEvent.OnItemClick(item.route))
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.iconId != null) {
                        Icon(
                            painter = painterResource(id = item.iconId),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    } else {
                        Icon(
                            imageVector = item.icon!!,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(dimensions.spaceMedium))
                    Text(
                        text = item.label,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_20,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Divider()
            }
        }
    }
}