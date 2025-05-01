package com.ghn.composelistkit.wrapper

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.hashCode

/**
 * @author 浩楠
 *
 * @date 2025/4/30-21:34
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T, G> StickySectionWrapper(
    groups: List<G>,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    isSticky: Boolean = true,
    groupTitleSelector: (G) -> String,
    groupItemsSelector: (G) -> List<T>,
    itemKey: ((T) -> Any)? = null,
    isLoadingMore: Boolean = false,
    loadMoreContent: (@Composable () -> Unit)? = null,
    groupHeaderContent: @Composable (String) -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    LazyListContent<G>(
        listState = listState,
        modifier = modifier,
        contentBuilder = {
            groups.forEach { group ->
                val title = groupTitleSelector(group)
                val items = groupItemsSelector(group)

                if (isSticky) {
                    stickyHeader(key = "sticky_$title") {
                        groupHeaderContent(title)
                    }
                } else {
                    item(key = "title_$title") {
                        groupHeaderContent(title)
                    }
                }

                this.items(
                    items = items,
                    key = itemKey ?: { it.hashCode() }
                ) { item ->
                    itemContent(item)
                }
            }

            if (isLoadingMore) {
                item {
                    (loadMoreContent ?: {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    })()
                }
            }
        }
    )
}

