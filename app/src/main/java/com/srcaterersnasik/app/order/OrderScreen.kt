package com.srcaterersnasik.app.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.srcaterersnasik.R
import com.srcaterersnasik.app.ContactScreen

@Composable
fun OrderScreen(
    orderScreenState: OrderScreenState,
    navController: NavHostController,
    onBackPressed: () -> Unit,

    ) {
    Scaffold(topBar = { OrderScreenToolbar("Order List", onBackPressed) }) {
        OrderScreenContent(orderScreenState = orderScreenState, navController)
    }
}

@Composable

fun OrderScreenContent(
    orderScreenState: OrderScreenState,
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.End) {
            OutlinedButton(
                onClick = {
                    navController.navigate(OrderActivityScreen.EventScreen.route)
                }
            ) {
                Text(text = stringResource(id = R.string.date))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(68.dp)
                .padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.function_name),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
            OutlinedTextField(
                value = orderScreenState.order.functionName ?: "",
                onValueChange = orderScreenState.onFunctionNameChange,
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .weight(1f)
            )
        }
        ContactScreen(orderScreenState = orderScreenState)
        Row(modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
            Text(text = stringResource(id = R.string.nop),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start))
            OutlinedTextField(
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .weight(1f),
                value = "",
                onValueChange = {}
            )
        }
        Row(modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
            Text(text = stringResource(id = R.string.venue),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start))
            OutlinedTextField(modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f), value = "", onValueChange = {})
        }
        Row(modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
            Text(text = stringResource(id = R.string.category),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start))
            OutlinedTextField(modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f), value = "", onValueChange = {})
        }
    }
}

private fun getRowMargin(): Modifier {
    return Modifier
        .height(68.dp)
        .padding(8.dp)
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null,
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            TextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValueState = it
                    if (value != it.text) {
                        onValueChange(it.text)
                    }
                },

                //value = state.value,
                //onValueChange = { state.value = it }
            )
            if (textFieldValue.text.isEmpty()) {
                Text(
                    text = "Placeholder"
                )
            }
        }
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}

@Composable
fun OrderScreenToolbar(title:String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(
                content = {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(
                        id = R.string.back))
                },
                onClick = { onBackPressed() }
            )
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}
