package com.ghn.composelistkit

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.toMutableStateList
import com.ghn.composelistkit.config.ComposeListKitConfig
import com.ghn.composelistkit.listkit.ComposeListKitBuilder
import com.ghn.composelistkit.wrapper.DragReorderWrapper
import com.ghn.composelistkit.wrapper.HeaderFooterWrapper
import com.ghn.composelistkit.wrapper.RefreshWrapper
import com.ghn.composelistkit.wrapper.StateWrapper
import com.ghn.composelistkit.wrapper.StickySectionWrapper

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
fun <T> ComposeListKit(build: ComposeListKitBuilder<T>.() -> Unit) {
    val builder = ComposeListKitBuilder<T>().apply(build)
    InternalComposeListKit(builder.build())
}

@Composable
internal fun <T> InternalComposeListKit(config: ComposeListKitConfig<T>) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        if (config.onLoadMore != null) {
            snapshotFlow {
                val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                lastVisible >= config.items.lastIndex
            }.collect { isBottom ->
                if (isBottom && !config.isLoadingMore) {
                    config.onLoadMore?.invoke()
                }
            }
        }
    }
    StateWrapper(
        isLoadingFirstPage = config.isLoadingFirstPage,
        isError = config.isError,
        itemsEmpty = config.items.isEmpty() && config.groupedItems.isNullOrEmpty(),
        onRetry = config.onRetry,
        loadingContent = config.loadingContent,
        errorContent = config.errorContent,
        emptyContent = config.emptyContent
    ) {
        val listContent = @Composable {
            when {
                //分组吸顶
                config.groupedItems != null &&
                        config.groupTitleSelector != null &&
                        config.groupItemsSelector != null &&
                        config.groupHeaderContent != null -> {

                    StickySectionWrapper(
                        groups = config.groupedItems!!,
                        listState = listState,
                        isSticky = config.isStickyGroup,
                        groupTitleSelector = config.groupTitleSelector!!,
                        groupItemsSelector = config.groupItemsSelector!!,
                        itemKey = config.keySelector,
                        isLoadingMore = config.isLoadingMore,
                        groupHeaderContent = config.groupHeaderContent!!,
                        itemContent = config.itemContent
                    )
                }

                // 拖拽
                config.dragItemContent !== null -> {
                    DragReorderWrapper(
                        items = config.items.toMutableStateList(),
                        listState = listState,
                        modifier = config.modifier,
                        enableHighlight = config.enableHighlight,
                        useLongPress = config.useLongPress,
                        dragHandle = config.dragHandle,
                        itemKey = config.keySelector,
                        itemContent = config.dragItemContent!!
                    )
                }
                // Header + Footer + 普通列表
                else -> {
                    HeaderFooterWrapper(
                        items = config.items,
                        modifier = config.modifier,
                        keySelector = config.keySelector,
                        listState = listState,
                        isLoadingMore = config.isLoadingMore,
                        headerContent = config.headerContent,
                        footerContent = config.footerContent,
                        itemContent = config.itemContent
                    )
                }
            }
        }
        if (config.onRefresh != null) {
            RefreshWrapper(
                isRefreshing = config.isRefreshing,
                onRefresh = config.onRefresh
            ) {
                listContent()
            }
        } else {
            listContent()
        }
    }
}



