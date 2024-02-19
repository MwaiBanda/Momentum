package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C

@Composable
fun NavigationToolBar(
    title: String,
    subTitle: String,
    searchTerm: String = "",
    searchTag: String = "",
    onSearchTermChanged: (String) -> Unit = {},
    filterHeight: Int = 145,
    isOptionEnabled: Pair<Boolean, Boolean> = Pair(true, true),
    filterContent: @Composable ColumnScope.() -> Unit = {}
) {
    var showSearchBar by remember { mutableStateOf(false) }
    val searchOptionsHeight by animateDpAsState(targetValue = if (showSearchBar) 57.dp else 0.dp,
        label = "searchOptionsHeight"
    )
    var showFilterBar by remember { mutableStateOf(false) }
    val filterOptionsHeight by animateDpAsState(targetValue = if (showFilterBar) filterHeight.dp else 0.dp,
        label = "filterOptionsHeight"
    )
    val optionsHeight by animateDpAsState(targetValue = if (showFilterBar) filterOptionsHeight else searchOptionsHeight,
        label = "optionsHeight"
    )
    val focusManager = LocalFocusManager.current


    Column {
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(10.dp)
            )
            Row {
                if (isOptionEnabled.first) {
                    IconButton(
                        onClick = {
                            showSearchBar = showSearchBar.not()
                            showFilterBar = false
                        },
                        modifier = Modifier.offset(x = if (isOptionEnabled.second) 16.dp else 0.dp)
                    ) {
                        if (showSearchBar) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Search Close"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        }
                    }
                }
                if (isOptionEnabled.second) {
                    IconButton(onClick = {
                        showFilterBar = showFilterBar.not()
                        showSearchBar = false
                    }) {
                        if (showFilterBar) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Filter Close"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Filter Icon"
                            )
                        }
                    }
                }
            }
        }
        Divider()
        if (showSearchBar || showFilterBar) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(optionsHeight)
            ) {
                if (showSearchBar) {

                        TextField(
                            value = searchTerm,
                            onValueChange = onSearchTermChanged,
                            label = {
                                Column {
                                    AnimatedVisibility(visible = searchTag.isNotEmpty()) {
                                        Text(text = "Search $searchTag")
                                    }
                                }},
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color(C.MOMENTUM_ORANGE),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                placeholderColor = Color(C.MOMENTUM_ORANGE),
                                backgroundColor = Color.White,
                                focusedLabelColor = Color(C.MOMENTUM_ORANGE),

                                ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            })
                        )


                } else {
                    filterContent()
                }
                Divider()
            }
        } else {
            Text(
                text = subTitle.uppercase(),
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.caption,
                color = Color(C.MOMENTUM_ORANGE)
            )
        }
    }
}