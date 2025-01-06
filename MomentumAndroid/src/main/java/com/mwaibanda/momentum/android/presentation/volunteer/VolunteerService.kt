package com.mwaibanda.momentum.android.presentation.volunteer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.LoadingSpinner
import com.mwaibanda.momentum.android.presentation.volunteer.meal.DescriptionCard
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.models.VolunteerService
import com.mwaibanda.momentum.utils.MultiplatformConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VolunteerService(
    type: Tab.Type,
    servicesViewModel: ServicesViewModel,
    onServiceTapped: (VolunteerService) -> Unit
) {
    val service by servicesViewModel.service.collectAsState()
    var volunteerServices by remember {
        mutableStateOf(emptyList<VolunteerService>())
    }
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    fun onRefresh() = refreshScope.launch {
        isRefreshing = true
        volunteerServices = emptyList()
        delay(1500)
        servicesViewModel.getServiceByType(type)
    }
    val refreshState = rememberPullRefreshState(isRefreshing, ::onRefresh)
    val scrollState = rememberScrollState()

    LaunchedEffect(service) {
        volunteerServices = service?.services ?: emptyList()
    }
    LaunchedEffect(Unit) {
        servicesViewModel.getServiceByType(type)
    }
    Box(modifier = Modifier.pullRefresh(refreshState)) {

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column {
                Column {
                    Spacer(modifier = Modifier.height(90.dp))
                    Text(
                        text = MultiplatformConstants.VOLUNTEER_SERVICE_SUBHEADING.uppercase(),
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = MaterialTheme.typography.caption,
                        color = Color(C.MOMENTUM_ORANGE)
                    )
                    Box(contentAlignment = Alignment.TopCenter) {

                        Column(
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            if (volunteerServices.isEmpty()) {
                                repeat(7) {
                                    DescriptionCard(
                                        isRedacted = true,
                                        title = "placeholder",
                                        description = "placeholder"
                                    ) {}
                                }
                            } else {
                                volunteerServices.forEach {
                                    DescriptionCard(title = it.description, description = it.organizer) {
                                        onServiceTapped(it)
                                    }
                                }
                            }
                        }
                        LoadingSpinner(isVisible = volunteerServices.isEmpty() && isRefreshing.not())
                    }

                }
            }
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}