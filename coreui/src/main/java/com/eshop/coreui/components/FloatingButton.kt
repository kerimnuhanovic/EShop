package com.eshop.coreui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.theme.EShopTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingButton(
    modalBottomSheetState: SheetState,
    isBottomBarOverlapped: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()
    if (!isBottomBarOverlapped.value) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FloatingButtonPreview() {
    EShopTheme {
        FloatingButton(
            modalBottomSheetState = rememberModalBottomSheetState(),
            isBottomBarOverlapped = remember { mutableStateOf(false) }
        )
    }
}