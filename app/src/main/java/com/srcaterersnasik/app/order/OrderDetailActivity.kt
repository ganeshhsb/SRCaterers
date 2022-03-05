package com.srcaterersnasik.app.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.srcaterersnasik.app.theme.SRCaterersTheme
import com.srcaterersnasik.model.Address
import com.srcaterersnasik.model.Order
import com.srcaterersnasik.model.Person
import java.util.Date

class OrderDetailActivity : ComponentActivity() {
    private val orderDetailViewModel: OrderDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val state = OrderScreenState()
        setContent {
            SRCaterersTheme {
                // A surface container using the 'background' color from the theme

                Surface(modifier = Modifier
                    .fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    val (orderScreenStateValue, orderScreenStateValueSetter) = remember {
                        mutableStateOf(state)
                    }
                    state.setValueSetter(orderScreenStateValueSetter)
                    orderActivityNavigation(orderScreenStateValue, ::onBackPressed)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

@Composable
fun orderActivityNavigation(
    orderScreenState: OrderScreenState,
    onBackPressed: () -> Unit,
) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = OrderActivityScreen.OrderScreen.route) {
        composable(OrderActivityScreen.OrderScreen.route) {
            OrderScreen(orderScreenState = orderScreenState, navController, onBackPressed)
        }
        composable(OrderActivityScreen.EventScreen.route) {
            EventScreen()
        }
    }
}

sealed class OrderActivityScreen(val route: String) {
    object OrderScreen : OrderActivityScreen("OrderScreen")
    object EventScreen : OrderActivityScreen("EventScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}


data class OrderScreenState(
    val person: Person = Person(),
    var order: Order = Order(),
    var address: Address = Address(),
) {
    var orderScreenStateValueSetter: ((OrderScreenState) -> Unit)? = null
    val onOrderDateChange = fun(date: Date) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        order.orderDate = date
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onFunctionNameChange = fun(name: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        order.functionName = name
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onPersonNameChange = fun(name: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        person.name = name
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onPersonPhoneChange = fun(phoneNumber: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        person.phoneNumber = phoneNumber

        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onAddress1ChangeListener = fun(address1: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.address1 = address1
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onAddress2Change = fun(address2: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.address2 = address2
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onAddress3Change = fun(address3: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.address3 = address3
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onCityChange = fun(city: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.city = city
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onStateChange = fun(state: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.state = state
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onCountryChange = fun(country: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.country = country
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    val onPostalCodeChange = fun(postalCode: String) {
        val stateCopy = this.copy()
        stateCopy.orderScreenStateValueSetter = orderScreenStateValueSetter
        address.postalCode = postalCode
        orderScreenStateValueSetter?.let { it(stateCopy) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderScreenState

        if (person.phoneNumber != other.person.phoneNumber) return false

        return false
    }

    override fun hashCode(): Int {
        return person.phoneNumber.hashCode()
    }

    fun setValueSetter(orderScreenStateValueSetter: (OrderScreenState) -> Unit) {
        this.orderScreenStateValueSetter = orderScreenStateValueSetter
    }

}
