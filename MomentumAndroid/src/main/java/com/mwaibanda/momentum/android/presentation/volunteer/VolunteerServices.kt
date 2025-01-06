package com.mwaibanda.momentum.android.presentation.volunteer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.utils.MultiplatformConstants
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun VolunteerServices(
    navController: NavController,
    servicesViewModel: ServicesViewModel,
    onServiceTapped: (Tab) -> Unit
) {
    val services by servicesViewModel.services.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        servicesViewModel.getServices()
    })
    Column (
        Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Volunteer",
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(10.dp)
            )


        }
        Divider()
        Text(
            text = MultiplatformConstants.VOLUNTEER_SUBHEADING.uppercase(),
            modifier = Modifier.padding(horizontal = 10.dp),
            style = MaterialTheme.typography.caption,
            color = Color(C.MOMENTUM_ORANGE)
        )

        LazyColumn {
            gridItems(data = services, columns = 2) {
                Card(Modifier.padding(5.dp)) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                when (it.type) {
                                    Tab.Type.MEALS -> coroutineScope.launch {
                                        onServiceTapped(it)
                                        navController.navigate(NavigationRoutes.MealScreen.route)
                                    }
                                    else -> coroutineScope.launch {
                                        onServiceTapped(it)
                                        navController.navigate("volunteer/${Json.encodeToString(it.type)}")
                                    }
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(it.image).allowHardware(false).build(),
                            contentDescription = "Translated description of what the image contains",
                            modifier = Modifier
                                .size(95.dp)
                                .padding(10.dp)
                        )
                        Text(text = it.name, modifier = Modifier.padding(bottom = 10.dp))
                    }
                }
            }
        }
    }
}

fun <T> LazyListScope.gridItems(
    modifier: Modifier = Modifier,
    data: List<T>,
    columns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / columns
    items(rows) { rowIndex ->
        Row(horizontalArrangement = horizontalArrangement, modifier = modifier) {
            for (columnIndex in 0 until columns) {
                val itemIndex = rowIndex * columns + columnIndex
                if (itemIndex < data.count()) {
                    val item = data[itemIndex]
                    androidx.compose.runtime.key(key?.invoke(item)) {
                        Box(
                            modifier = Modifier.weight(1f, fill = true),
                            propagateMinConstraints = true
                        ) {
                            itemContent.invoke(this, item)
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}