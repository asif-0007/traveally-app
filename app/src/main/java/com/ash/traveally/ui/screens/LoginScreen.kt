package com.ash.traveally.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ash.traveally.R
import com.ash.traveally.ui.components.buttons.FullWidthButton
import com.ash.traveally.ui.components.dialog.FailureDialog
import com.ash.traveally.ui.components.dialog.LoaderDialog
import com.ash.traveally.ui.components.text.AppTextField
import com.ash.traveally.ui.components.text.PasswordTextField
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates
import com.ash.traveally.viewmodel.LoginViewModel
import com.ash.traveally.viewmodel.state.LoginState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onSignupTextClick: () -> Unit
) {
    val state: LoginState = viewModel.loginState

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToMain()
        }
    }

    LoginContent(
        isLoading = state.isLoading,
        email = state.email,
        password = state.password,
        isValidEmail = state.isValidEmail ?: true,
        isValidPassword = state.isValidPassword ?: true,
        onEmailChange = viewModel::setEmail,
        onPasswordChange = viewModel::setPassword,
        onDialogDismiss = viewModel::clearError,
        error = state.error,
        onLoginClick = viewModel::loginUser,
        onSignupTextClick = onSignupTextClick
    )
}

@Composable
fun LoginContent(
    isLoading: Boolean,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    error: String?,
    onDialogDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onSignupTextClick: () -> Unit
) {
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(error, onDialogDismiss = onDialogDismiss)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        TopGreeting()

        LoginForm(
            email = email,
            password = password,
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onLoginClick = onLoginClick
        )

        SignUpLink(Modifier.align(Alignment.CenterHorizontally), onSignupTextClick = onSignupTextClick)
    }
}

@Composable
private fun TopGreeting() {
    Column(Modifier.fillMaxWidth()) {
        Image(
            contentDescription = "App Logo",
            painter = painterResource(id = R.drawable.ic_logo),
            modifier = Modifier
                .padding(top = 60.dp)
                .requiredSize(100.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "Welcome\nback",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp)
        )
    }
}

@Composable
private fun LoginForm(
    email: String,
    password: String,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    onLoginClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    AppTextField(
        value = email,
        label = "Email",
        onValueChange = onEmailChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(MaterialTheme.colorScheme.background),
        leadingIcon = { Icon(Icons.Outlined.Email, "Email") },
        isError = !isValidEmail,
        helperText = stringResource(R.string.message_field_email_invalid)
    )
    PasswordTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        helperText = stringResource(R.string.message_field_password_invalid),
        value = password,
        onValueChange = onPasswordChange,
        isError = !isValidPassword
    )

    FullWidthButton(
        text = "Login",
        onClick = onLoginClick,
        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
    )
}

@Composable
private fun SignUpLink(modifier: Modifier, onSignupTextClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Don't have an account? Signup")
            addStyle(SpanStyle(color = LightGreen), 23, this.length)
            toAnnotatedString()
        },
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = MontserratAlternates,
            fontWeight = FontWeight.W500
        ),
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .clickable(onClick = onSignupTextClick)
    )
}

