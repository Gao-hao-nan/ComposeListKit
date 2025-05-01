package com.ghn.composelistkit.listkit

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ghn.composelistkit.config.ComposeListKitConfig

/**
 * @author 浩楠
 *
 * @date 2025/5/1-12:56
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
class ComposeListKitBuilder<T> {
    private val config = ComposeListKitConfig<T>()

    fun items(list: List<T>) = apply { config.items = list }
    fun keySelector(selector: (T) -> Any) = apply { config.keySelector = selector }

    fun isRefreshing(value: Boolean) = apply { config.isRefreshing = value }
    fun onRefresh(action: () -> Unit) = apply { config.onRefresh = action }

    fun isLoadingMore(value: Boolean) = apply { config.isLoadingMore = value }
    fun onLoadMore(action: () -> Unit) = apply { config.onLoadMore = action }

    fun isLoadingFirstPage(value: Boolean) = apply { config.isLoadingFirstPage = value }
    fun isError(value: Boolean) = apply { config.isError = value }
    fun onRetry(action: () -> Unit) = apply { config.onRetry = action }

    fun emptyContent(content: @Composable () -> Unit) = apply { config.emptyContent = content }
    fun errorContent(content: @Composable () -> Unit) = apply { config.errorContent = content }
    fun loadingContent(content: @Composable () -> Unit) = apply { config.loadingContent = content }

    fun header(content: @Composable () -> Unit) = apply { config.headerContent = content }
    fun footer(content: @Composable () -> Unit) = apply { config.footerContent = content }

    fun modifier(modifier: Modifier) = apply { config.modifier = modifier }

    fun itemContent(content: @Composable (T) -> Unit) = apply { config.itemContent = content }

    fun build(): ComposeListKitConfig<T> = config

    fun <G> groupedItems(
        groups: List<G>,
        groupTitleSelector: (G) -> String,
        groupItemsSelector: (G) -> List<T>
    ) = apply {
        @Suppress("UNCHECKED_CAST")
        config.groupedItems = groups as List<Any>
        config.groupTitleSelector = groupTitleSelector as (Any) -> String
        config.groupItemsSelector = groupItemsSelector as (Any) -> List<T>
    }

    fun groupHeaderContent(content: @Composable (String) -> Unit) = apply {
        config.groupHeaderContent = content
    }

    fun isStickyGroup(sticky: Boolean) = apply {
        config.isStickyGroup = sticky
    }


}
