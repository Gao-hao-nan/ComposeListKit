package com.ghn.composelistkit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ghn.composelistkit.config.ComposeListKitConfig
import com.ghn.composelistkit.listkit.ComposeListKitBuilder
import com.ghn.composelistkit.wrapper.DragReorderWrapper
import com.ghn.composelistkit.wrapper.HeaderFooterWrapper
import com.ghn.composelistkit.wrapper.RefreshWrapper
import com.ghn.composelistkit.wrapper.SimpleSwipeToDeleteWrapper
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
                // 侧滑删除
                config.onSwipeDelete != null -> {
                    SimpleSwipeToDeleteWrapper(
                        items = config.items.toMutableStateList(),
                        listState = listState,
                        keySelector = config.keySelector,
                        onDelete = config.onSwipeDelete!!,
                        contentPadding = config.swipeContentPadding,
                        backgroundContent = config.swipeBackground ?: { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Red),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "删除",
                                    color = Color.White,
                                    modifier = Modifier
                                        .clickable { config.onSwipeDelete?.invoke(item) }
                                        .padding(horizontal = 20.dp, vertical = 12.dp)
                                )
                            }
                        },
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
                isRefreshing = config.isRefreshing, onRefresh = config.onRefresh
            ) {
                listContent()
            }
        } else {
            listContent()
        }
    }
}



