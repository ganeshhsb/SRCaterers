package com.srcaterersnasik.app

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.srcaterersnasik.R
import com.srcaterersnasik.app.order.OrderScreenState

@Composable
fun PersonDetailScreen(orderScreenState: OrderScreenState) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.person_name),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )
       // val personaName = remember { mutableStateOf("Test")}
        OutlinedTextField(
            value =  orderScreenState.person.name ?:"",
            onValueChange = orderScreenState.onPersonNameChange,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f)
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(68.dp)
            .padding(8.dp)) {
        Text(text = stringResource(id = R.string.phone),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start))
        OutlinedTextField(value = orderScreenState.person.phoneNumber ?: "",
            onValueChange = orderScreenState.onPersonPhoneChange,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .weight(1f))
    }
}