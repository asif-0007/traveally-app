package com.ash.traveally.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.ash.traveally.viewmodel.RegisterViewModel
import com.ash.traveally.viewmodel.state.RegisterState

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    var state: RegisterState = viewModel.registerState
    val context = LocalContext.current

    SignUpContent(
        isLoading = state.isLoading,
        name = state.name,
        email = state.email,
        username = state.username,
        phoneNumber = state.phoneNumber,
        city = state.city,
        country = state.country,
        bio = state.bio,
        password = state.password,
        confirmPassword = state.confirmPassword,
        isValidName = state.isValidName ?: true,
        isValidEmail = state.isValidEmail ?: true,
        isValidUsername = state.isValidUsername ?: true,
        isValidPhoneNumber = state.isValidPhoneNumber ?: true,
        isValidPassword = state.isValidPassword ?: true,
        isValidConfirmPassword = state.isValidConfirmPassword ?: true,
        onNameChange = viewModel::setName,
        onEmailChange = viewModel::setEmail,
        onUsernameChange = viewModel::setUsername,
        onPhoneNumberChange = viewModel::setPhoneNumber,
        onCityChange = viewModel::setCity,
        onCountryChange = viewModel::setCountry,
        onBioChange = viewModel::setBio,
        onPasswordChange = viewModel::setPassword,
        onConfirmPasswordChange = viewModel::setConfirmPassword,
        onDialogDismiss = viewModel::clearError,
        error = state.error,
        onRegisterClick = viewModel::registerUser,
        onSigninTextClick = onNavigateToLogin
    )

    LaunchedEffect(state.isRegisteredIn) {
        if (state.isRegisteredIn) {
            Toast.makeText(context, state.response?.message, Toast.LENGTH_LONG).show()
            onNavigateToLogin()
        }
    }
}

@Composable
fun SignUpContent(
    isLoading: Boolean,
    name: String,
    email: String,
    username: String,
    phoneNumber: String,
    city: String,
    country: String,
    bio: String,
    password: String,
    confirmPassword: String,
    isValidName: Boolean,
    isValidEmail: Boolean,
    isValidUsername: Boolean,
    isValidPhoneNumber: Boolean,
    isValidPassword: Boolean,
    isValidConfirmPassword: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onDialogDismiss: () -> Unit,
    error: String?,
    onRegisterClick: () -> Unit,
    onSigninTextClick: () -> Unit
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
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Create\nAccount",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp)
        )

        SignUpForm(
            name = name,
            email = email,
            username = username,
            phoneNumber = phoneNumber,
            city = city,
            country = country,
            bio = bio,
            password = password,
            confirmPassword = confirmPassword,
            onNameChange = onNameChange,
            onEmailChange = onEmailChange,
            onUsernameChange = onUsernameChange,
            onPhoneNumberChange = onPhoneNumberChange,
            onCityChange = onCityChange,
            onCountryChange = onCountryChange,
            onBioChange = onBioChange,
            onPasswordChange = onPasswordChange,
            onConfirmPasswordChange = onConfirmPasswordChange,
            isValidName = isValidName,
            isValidEmail = isValidEmail,
            isValidUsername = isValidUsername,
            isValidPhoneNumber = isValidPhoneNumber,
            isValidPassword = isValidPassword,
            isValidConfirmPassword = isValidConfirmPassword,
            onRegisterClick = onRegisterClick
        )

        LoginLink(Modifier.align(Alignment.CenterHorizontally), onSigninTextClick = onSigninTextClick)
    }
}

@Composable
private fun SignUpForm(
    name: String,
    email: String,
    username: String,
    phoneNumber: String,
    city: String,
    country: String,
    bio: String,
    password: String,
    confirmPassword: String,
    isValidName: Boolean,
    isValidEmail: Boolean,
    isValidUsername: Boolean,
    isValidPhoneNumber: Boolean,
    isValidPassword: Boolean,
    isValidConfirmPassword: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppTextField(
            value = name,
            label = "Name",
            onValueChange = onNameChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            leadingIcon = { Icon(Icons.Outlined.Person, "Name") },
            isError = !isValidName,
            helperText = stringResource(R.string.message_field_name_invalid)
        )

        AppTextField(
            value = username,
            label = "Username",
            onValueChange = onUsernameChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            leadingIcon = { Icon(Icons.Outlined.AlternateEmail, "Username") },
            isError = !isValidUsername,
            helperText = stringResource(R.string.message_field_username_invalid)
        )

        AppTextField(
            value = email,
            label = "Email",
            onValueChange = onEmailChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            leadingIcon = { Icon(Icons.Outlined.Email, "Email") },
            isError = !isValidEmail,
            helperText = stringResource(R.string.message_field_email_invalid)
        )

        AppTextField(
            value = phoneNumber,
            label = "Phone Number",
            onValueChange = onPhoneNumberChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            leadingIcon = { Icon(Icons.Outlined.Phone, "Phone") },
            isError = !isValidPhoneNumber,
            helperText = stringResource(R.string.message_field_phone_number_invalid)
        )

        Row {
            AppTextField(
                value = city,
                label = "City",
                onValueChange = onCityChange,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.background),
                leadingIcon = { Icon(Icons.Outlined.LocationCity, "City") }
            )

            AppTextField(
                value = country,
                label = "Country",
                onValueChange = onCountryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.background),
                leadingIcon = { Icon(Icons.Outlined.Flag, "Country") }
            )
        }

        AppTextField(
            value = bio,
            label = "Bio",
            onValueChange = onBioChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            leadingIcon = { Icon(Icons.Outlined.Description, "Bio") },
            helperText = stringResource(R.string.message_field_bio)
        )

        PasswordTextField(
            value = password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            helperText = stringResource(R.string.message_field_password_invalid),
            onValueChange = onPasswordChange,
            isError = !isValidPassword
        )

        PasswordTextField(
            value = confirmPassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            label = "Confirm Password",
            helperText = stringResource(R.string.message_field_confirm_password_invalid),
            onValueChange = onConfirmPasswordChange,
            isError = !isValidConfirmPassword
        )

        FullWidthButton(
            text = "Register",
            onClick = onRegisterClick,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun LoginLink(modifier: Modifier, onSigninTextClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Already have an account? Login")
            addStyle(SpanStyle(color = LightGreen), 24, this.length)
        },
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = MontserratAlternates,
            fontWeight = FontWeight.W500
        ),
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .clickable(onClick = onSigninTextClick)
    )
}