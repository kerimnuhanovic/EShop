package com.eshop.signup_presentation.signup.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.signup_presentation.signup.SignupEvent
import com.eshop.signup_presentation.signup.util.UserRole

@Composable
fun UserRoleSelector(
    selectedRole: UserRole,
    onEvent: (SignupEvent.OnUserRoleSelect) -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val halfScreenWidth = (LocalConfiguration.current.screenWidthDp / 2).dp - dimensions.size_32
    val animateDividerOffset = animateDpAsState(
        targetValue = if (selectedRole == UserRole.CUSTOMER) dimensions.default else halfScreenWidth,
        animationSpec = tween(1000)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.spaceMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clickable { onEvent(SignupEvent.OnUserRoleSelect(UserRole.CUSTOMER)) },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = UserRole.CUSTOMER.name,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dimensions.font_16,
                color = if (selectedRole == UserRole.CUSTOMER) MaterialTheme.colors.primary else
                    Color.Black
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable { onEvent(SignupEvent.OnUserRoleSelect(UserRole.SELLER)) },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = UserRole.SELLER.name,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dimensions.font_16,
                color = if (selectedRole == UserRole.SELLER) MaterialTheme.colors.primary else
                    Color.Black
            )

        }
    }
    Box(
        modifier = modifier.fillMaxWidth(0.5f)
            .height(dimensions.spaceExtraSmall)
            .padding(start = dimensions.spaceMedium)
            .offset(animateDividerOffset.value)
            .background(MaterialTheme.colors.primary)
    )
}