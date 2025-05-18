package com.ghn.composelistkit.wrapper

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author 浩楠
 *
 * @date 2025/4/27-17:29
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun <T> HeaderFooterWrapper(
    items: List<T>,
    modifier: Modifier = Modifier,
    keySelector: ((T) -> Any)? = null,
    listState: LazyListState,
    isLoadingMore: Boolean = false,
    headerContent: (@Composable () -> Unit)? = null,
    footerContent: (@Composable () -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {
    LazyListContent<T>(
        modifier = modifier,
        listState = listState,
        isLoadingMore = isLoadingMore,
        contentBuilder = {
            headerContent?.let {
                item("HeaderSlot") { it() }
            }
            items(
                items = items,
                key = keySelector ?: { it.hashCode() }
            ) { item ->
                itemContent(item)
            }
            footerContent?.let {
                item("FooterSlot") { it() }
            }
        }
    )
}

