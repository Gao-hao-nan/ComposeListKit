package com.ghn.composelistkit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.lazy.*

/**
 * @author 浩楠
 *
 * @date 2025/4/25-15:23
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun <T> ComposeListKit(
    items: List<T>,
    modifier: Modifier = Modifier,
    keySelector: ((T) -> Any)? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    isLoadingMore: Boolean = false,
    onLoadMore: (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {
    val listState = rememberLazyListState()

    // 加载更多监听
    LaunchedEffect(listState) {
        if (onLoadMore != null) {
            snapshotFlow {
                val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                lastVisible >= items.lastIndex
            }.collect { isBottom ->
                if (isBottom && !isLoadingMore) {
                    onLoadMore()
                }
            }
        }
    }

    val content = @Composable {
        LazyColumn(state = listState, modifier = modifier) {
            items(
                items = items,
                key = keySelector ?: { it.hashCode() }
            ) { item ->
                itemContent(item)
            }

            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    if (onRefresh != null) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    } else {
        content()
    }
}


