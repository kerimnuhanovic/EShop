package com.eshop.orders_presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.core.util.UserType
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
import com.eshop.orders_presentation.components.OrderItem
import com.eshop.orders_presentation.components.OrdersScreenLoadingSkeleton

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            } else if (uiEvent is UiEvent.NavigateBack) {
                onNavigateBack()
            } else if (uiEvent is UiEvent.DisplayToast) {
                Toast.makeText(context, uiEvent.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    OrdersScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate
    )
}

@Composable
private fun OrdersScreenContent(
    state: OrdersState,
    onEvent: (OrdersEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current

    Scaffold(
        bottomBar = {
            if (state.userType is UserType.Shop) {
                BottomBar(
                    items = state.bottomBarItems,
                    isBottomBarOverlapped = false,
                    onNavigate = onNavigate,
                    currentDestination = Route.ORDERS
                )
            }
        },
        topBar = {
            if (state.userType is UserType.Customer) {
                TopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.my_orders),
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_16,
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                    navigationIcon = {
                        IconButton(onClick = { onEvent(OrdersEvent.OnNavigateBack) }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    })
            }
        }
    ) {
        if (state.isLoading) {
            OrdersScreenLoadingSkeleton()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(it)
            ) {
                item {
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                }
                state.orders.mapIndexed { index, order ->
                    itemsIndexed(order.orderDetails) { orderDetailsIndex, orderDetails ->
                        OrderItem(
                            order = orderDetails,
                            dateCreated = order.dateCreated,
                            isExpanded = state.expandedItems[index + orderDetailsIndex],
                            onOrderClick = {
                                onEvent(OrdersEvent.OnOrderClick(index + orderDetailsIndex))
                            },
                            userType = state.userType!!,
                            customerUsername = order.customer,
                            onAcceptClick = {
                                onEvent(OrdersEvent.OnAcceptClick(order.id, orderDetails.id!!))
                            },
                            onDeclineClick = {
                                onEvent(OrdersEvent.OnDeclineClick(order.id, orderDetails.id!!))
                            },
                            isAcceptStatusSubmitting = state.isAcceptStatusSubmitting,
                            isDeclineStatusSubmitting = state.isDeclineStatusSubmitting
                        )
                        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    }
                }
            }
        }
    }
}