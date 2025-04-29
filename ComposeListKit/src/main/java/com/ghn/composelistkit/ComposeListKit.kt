package com.ghn.composelistkit

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.ghn.composelistkit.wrapper.HeaderFooterWrapper
import com.ghn.composelistkit.wrapper.LazyListContent
import com.ghn.composelistkit.wrapper.RefreshWrapper
import com.ghn.composelistkit.wrapper.StateWrapper

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
    isLoadingFirstPage: Boolean = false,
    isError: Boolean = false,
    onRetry: (() -> Unit)? = null,
    emptyContent: (@Composable () -> Unit)? = null,
    errorContent: (@Composable () -> Unit)? = null,
    loadingContent: (@Composable () -> Unit)? = null,
    headerContent: (@Composable () -> Unit)? = null,
    footerContent: (@Composable () -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {
    val listState = rememberLazyListState()

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

    StateWrapper(
        isLoadingFirstPage = isLoadingFirstPage,
        isError = isError,
        itemsEmpty = items.isEmpty(),
        onRetry = onRetry,
        loadingContent = loadingContent,
        errorContent = errorContent,
        emptyContent = emptyContent
    ) {
        val listContent = @Composable {
            HeaderFooterWrapper(
                items = items,
                modifier = modifier,
                keySelector = keySelector,
                listState = listState,
                isLoadingMore = isLoadingMore,
                headerContent = headerContent,
                footerContent = footerContent,
                itemContent = itemContent
            )
        }

        if (onRefresh != null) {
            RefreshWrapper(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            ) {
                listContent()
            }
        } else {
            listContent()
        }
    }
}
