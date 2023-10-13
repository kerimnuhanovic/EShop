package com.eshop.signup_presentation.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.components.ErrorBox
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.components.InputField
import com.eshop.signup_presentation.signup.components.ShopCategoriesDropdownMenu
import com.eshop.signup_presentation.signup.components.PageIndicator
import com.eshop.signup_presentation.signup.components.ShopDataBox
import com.eshop.signup_presentation.signup.components.UserRoleSelector
import com.eshop.signup_presentation.signup.util.FIRST_PAGE
import com.eshop.signup_presentation.signup.util.PAGE_COUNT
import com.eshop.signup_presentation.signup.util.SECOND_PAGE
import com.eshop.signup_presentation.signup.util.ShopCategory
import com.eshop.signup_presentation.signup.util.ShopLocation
import com.eshop.signup_presentation.signup.util.THIRD_PAGE
import com.eshop.signup_presentation.signup.util.UserRole

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> onNavigate(uiEvent)
                is UiEvent.ScrollPage -> pagerState.animateScrollToPage(uiEvent.page)
            }
        }
    }
    SignupScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        pagerState = pagerState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreenContent(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit,
    pagerState: PagerState
) {
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
                CompleteRegistrationScreen(
                    state = state,
                    onEvent = onEvent
                )
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun EnterDataScreen(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val keyboardController = LocalSoftwareKeyboardController.current
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
        if (state.errorMessageId != null) {
            ErrorBox(
                errorMessageId = state.errorMessageId,
                modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceLarge))
        }
        UserRoleSelector(
            selectedRole = state.userRole, onEvent = onEvent,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
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
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        AnimatedVisibility(visible = state.userRole == UserRole.SELLER) {
            Column {
                ShopCategoriesDropdownMenu(
                    expanded = state.isCategoryDropdownMenuExpanded,
                    placeholder = stringResource(id = com.eshop.signup_presentation.R.string.select_your_shop_categories),
                    items = ShopCategory.listAllCategories(),
                    selectedItems = state.listOfShopCategories,
                    onExpandChange = {
                        onEvent(SignupEvent.OnExpandChange)
                    },
                    onSelectItem = {
                        onEvent(SignupEvent.OnShopCategoryClick(it))
                    },
                    modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
                )
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
                    state.listOfShopCategories.forEach {
                        ShopDataBox(shopData = it, onExitClick = { shopCategory ->
                            onEvent(SignupEvent.OnShopCategoryClick(shopCategory as ShopCategory))
                        })
                    }
                }
                if (state.listOfShopCategories.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                }
                InputField(
                    inputText = state.shopLocation,
                    onTextChange = {
                        onEvent(SignupEvent.OnShopLocationEnter(it))
                    },
                    placeholderId = com.eshop.signup_presentation.R.string.enter_your_shop_locations,
                    modifier = Modifier.padding(horizontal = dimensions.spaceMedium),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        onEvent(SignupEvent.OnShopLocationAdd(ShopLocation(state.shopLocation)))
                        keyboardController?.hide()
                    })
                )
                if (state.listOfShopLocations.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                }
                FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
                    state.listOfShopLocations.forEach {
                        ShopDataBox(shopData = it, onExitClick = { shopLocation ->
                            onEvent(SignupEvent.OnShopLocationRemove(shopLocation as ShopLocation))
                        })
                    }
                }
                if (state.listOfShopLocations.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                }
            }
        }
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
    val stroke = Stroke(
        width = 16f,
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
            .padding(bottom = dimensions.spaceMedium)
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
private fun CompleteRegistrationScreen(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.eshop.signup_presentation.R.raw.e_commerce_animation))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensions.spaceMedium),
        horizontalAlignment = CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        EShopButton(
            content = {
                if (!state.isLoading) {
                    Text(
                        text = stringResource(id = com.eshop.signup_presentation.R.string.sign_up),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensions.font_16
                    )
                } else {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.size(dimensions.size_32)
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            onButtonClick = {
                onEvent(SignupEvent.OnRegisterClick)
            },modifier = Modifier
                    .fillMaxWidth(0.6f),
            enabled = !state.isLoading)
        Spacer(modifier = Modifier.weight(1f))
        PageIndicator(THIRD_PAGE)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun SignupScreenPreview() {
    EShopTheme {
        SignupScreenContent(
            state = SignupState(),
            onEvent = {},
            pagerState = rememberPagerState()
        )
    }
}

@Composable
@Preview
private fun UploadImageScreenPreview() {
    EShopTheme {
        UploadImageScreen(state = SignupState(), onEvent = {})
    }
}

@Composable
@Preview(showBackground = true)
private fun CompleteRegistrationScreenPreview() {
    EShopTheme {
        CompleteRegistrationScreen(
            state = SignupState(),
            onEvent = {}
        )
    }
}
