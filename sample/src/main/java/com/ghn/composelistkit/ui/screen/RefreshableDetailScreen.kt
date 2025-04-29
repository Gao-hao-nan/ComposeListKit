package com.ghn.composelistkit.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghn.composelistkit.ComposeListKit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author 浩楠
 *
 * @date 2025/4/25-22:40
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun RefreshableDetailScreen() {
    val items = remember { mutableStateListOf<String>() }
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        items.addAll((1..20).map { "Item $it" })
    }

    ComposeListKit(
        items = items,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                delay(1000)
                items.clear()
                items.addAll((101..120).map { "Refreshed $it" })
                isRefreshing = false
            }
        },
        isLoadingMore = isLoadingMore,
        onLoadMore = {
            isLoadingMore = true
            coroutineScope.launch {
                delay(1000)
                val more = (items.size + 1)..(items.size + 20)
                items.addAll(more.map { "Loaded $it" })
                isLoadingMore = false
            }
        }
    ) { item ->
        Text(
            text = item,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
