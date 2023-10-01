package com.eshop.signup_presentation.signup

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray
import com.eshop.coreui.util.UiEvent
import com.eshop.login_presentation.login.components.InputField
import com.eshop.signup_presentation.signup.components.PageIndicator
import com.eshop.signup_presentation.signup.util.FIRST_PAGE
import com.eshop.signup_presentation.signup.util.PAGE_COUNT
import com.eshop.signup_presentation.signup.util.SECOND_PAGE
import com.eshop.signup_presentation.signup.util.THIRD_PAGE

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    SignupScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreenContent(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit
) {
    val pagerState = rememberPagerState()
    val dimensions = LocalDimensions.current
    HorizontalPager(state = pagerState, pageCount = PAGE_COUNT) { page ->
        when (page) {
            FIRST_PAGE -> {
                EnterDataScreen(
                    state = state,
                    onEvent = onEvent
                )
            }
            SECOND_PAGE -> {
                UploadImageScreen(
                    state = state,
                    onEvent = onEvent
                )
            }
            THIRD_PAGE -> {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensions.spaceMedium)) {
                    Spacer(modifier = Modifier.weight(1f))
                    PageIndicator(pagerState.currentPage)
                }
            }
        }
    }

}

@Composable
private fun EnterDataScreen(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit
) {
    val dimensions = LocalDimensions.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(dimensions.spaceMedium)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eshoplogo),
            contentDescription = "something",
            modifier = Modifier.size(dimensions.logoSize)
        )
        Text(
            text = stringResource(id = R.string.create_an_account),
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensions.font_20,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensions.spaceLarge))
        InputField(
            inputText = state.name,
            onTextChange = {
                onEvent(SignupEvent.OnNameEnter(it))
            },
            placeholderId = com.eshop.signup_presentation.R.string.enter_your_name,
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        InputField(
            inputText = state.surname,
            onTextChange = {
                onEvent(SignupEvent.OnSurnameEnter(it))
            },
            placeholderId = com.eshop.signup_presentation.R.string.enter_your_surname,
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        InputField(
            inputText = state.username,
            onTextChange = {
                onEvent(SignupEvent.OnUsernameEnter(it))
            },
            placeholderId = com.eshop.signup_presentation.R.string.enter_your_username,
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        InputField(
            inputText = state.email,
            onTextChange = {
                onEvent(SignupEvent.OnEmailEnter(it))
            },
            placeholderId = com.eshop.signup_presentation.R.string.enter_your_email,
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        InputField(
            inputText = state.password,
            onTextChange = {
                onEvent(SignupEvent.OnPasswordEnter(it))
            },
            placeholderId = com.eshop.signup_presentation.R.string.enter_your_password,
            trailingIconId = if (!state.isPasswordVisible) R.drawable.visibility_off_24 else R.drawable.visibility_24,
            onTrailingIconClick = {
                onEvent(SignupEvent.OnPasswordVisibilityIconClick)
            },
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        InputField(
            inputText = state.confirmPassword,
            onTextChange = {
                onEvent(SignupEvent.OnConfirmPasswordEnter(it))
            },
            placeholderId = com.eshop.signup_presentation.R.string.confirm_your_password,
            trailingIconId = if (!state.isConfirmPasswordVisible) R.drawable.visibility_off_24 else R.drawable.visibility_24,
            onTrailingIconClick = {
                onEvent(SignupEvent.OnConfirmPasswordVisibilityIconClick)
            },
            modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (!state.isConfirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None
        )
        Spacer(modifier = Modifier.weight(1f))
        PageIndicator(FIRST_PAGE, modifier = Modifier.padding(top = dimensions.spaceSmall))
    }
}

@Composable
private fun UploadImageScreen(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val borderColor = MaterialTheme.colors.primary
    val stroke = Stroke(width = 16f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
    )
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            uriList.map {
                onEvent(SignupEvent.OnImageSelect(uriList.first()))
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .height(dimensions.uploadImageSurfaceSize)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .height(dimensions.uploadImageSurfaceSize)
                    .fillMaxWidth(),
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(
                    bottomStart = dimensions.largeCornerRadius,
                    bottomEnd = dimensions.largeCornerRadius
                )
            ) {}
        }
        Surface(
            modifier = Modifier
                .height(dimensions.uploadImagePlaceholderHeight)
                .width(dimensions.uploadImagePlaceholderWidth)
                .align(CenterHorizontally)
                .offset(y = dimensions.offset_100),
            color = MediumGray,
            shape = RoundedCornerShape(CornerSize(dimensions.spaceExtraSmall))
        ) {
            Surface(
                modifier = Modifier
                    .padding(dimensions.spaceMedium)
                    .drawBehind {
                        drawRoundRect(
                            color = borderColor,
                            style = stroke,
                            cornerRadius = CornerRadius(dimensions.spaceExtraSmall.toPx())
                        )
                    },
                color = MediumGray,
            ) {
                Image(
                    modifier = Modifier.clickable {
                        galleryLauncher.launch("image/*")
                    },
                    painter = if (state.profileImage == Uri.EMPTY) painterResource(id = R.drawable.add_photo) else
                        rememberAsyncImagePainter(model = state.profileImage),
                    contentDescription = null,
                    contentScale = if (state.profileImage == Uri.EMPTY) ContentScale.None else ContentScale.Crop
                )
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        PageIndicator(SECOND_PAGE)
    }
}

@Composable
@Preview
private fun SignupScreenPreview() {
    EShopTheme {
        SignupScreenContent(state = SignupState(), onEvent = {})
    }
}

@Composable
@Preview
private fun UploadImageScreenPreview() {
    EShopTheme {
        UploadImageScreen(state = SignupState(), onEvent = {})
    }
}