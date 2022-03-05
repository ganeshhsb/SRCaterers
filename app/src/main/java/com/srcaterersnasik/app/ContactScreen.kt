package com.srcaterersnasik.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.srcaterersnasik.R
import com.srcaterersnasik.app.order.OrderScreenState
import com.srcaterersnasik.model.Person

@Preview
@Composable
fun PersonScreenPreview() {
    ContactScreen(orderScreenState = remember {
        OrderScreenState()
    })
}

@Composable
fun ContactScreen(
    orderScreenState: OrderScreenState,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        PersonDetailScreen(orderScreenState = orderScreenState)
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp))
        AddressScreen(orderScreenState = orderScreenState)
    }

}