package com.eshop.coreui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eshop.core.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloatingButton(
    modalBottomSheetState: ModalBottomSheetState,
    navController: NavController,
    isBottomBarOverlapped: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    if (currentDestination == Route.PRODUCTS && !isBottomBarOverlapped.value) {
        FloatingActionButton(onClick = {
            scope.launch {
                isBottomBarOverlapped.value = true
                delay(200L)
                modalBottomSheetState.show()
            }
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "AddIcon")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun FloatingButtonPreview() {
    EShopTheme {
        FloatingButton(
            modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
            navController = rememberNavController(),
            isBottomBarOverlapped = remember { mutableStateOf(false) }
        )
    }
}