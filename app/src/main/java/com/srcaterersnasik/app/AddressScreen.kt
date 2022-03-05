package com.srcaterersnasik.app

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.srcaterersnasik.R
import com.srcaterersnasik.app.order.OrderScreenState

@Composable
fun AddressScreen(orderScreenState: OrderScreenState){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.address1),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )
        OutlinedTextField(
            value = orderScreenState.address.address1?:"", onValueChange = orderScreenState.onAddress1ChangeListener,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f)
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(text = stringResource(id = R.string.address2),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start))
        OutlinedTextField(value = orderScreenState.address.address2?:"", onValueChange = orderScreenState.onAddress2Change, modifier = Modifier
            .wrapContentWidth(Alignment.Start)
            .weight(1f))
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.address3),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )
        OutlinedTextField(
            value = orderScreenState.address.address3?:"", onValueChange = orderScreenState.onAddress3Change,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f)
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(text = stringResource(id = R.string.city),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start))
        OutlinedTextField(value = orderScreenState.address.city?:"", onValueChange = orderScreenState.onCityChange, modifier = Modifier
            .wrapContentWidth(Alignment.Start)
            .weight(1f))
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.state),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )
        OutlinedTextField(
            value = orderScreenState.address.state?:"", onValueChange = orderScreenState.onStateChange,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f)
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(text = stringResource(id = R.string.country),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start))
        OutlinedTextField(value = orderScreenState.address.country?:"", onValueChange = orderScreenState.onCountryChange, modifier = Modifier
            .wrapContentWidth(Alignment.Start)
            .weight(1f))
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(text = stringResource(id = R.string.postal_code),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start))
        OutlinedTextField(value = orderScreenState.address.postalCode?:"", onValueChange = orderScreenState.onPostalCodeChange, modifier = Modifier
            .wrapContentWidth(Alignment.Start)
            .weight(1f))
    }
}