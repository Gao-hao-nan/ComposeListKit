package com.ghn.composelistkit.config

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @author 浩楠
 *
 * @date 2025/5/1-12:55
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
data class ComposeListKitConfig<T>(
    var items: List<T> = emptyList(),
    var keySelector: ((T) -> Any)? = null,
    var isRefreshing: Boolean = false,
    var onRefresh: (() -> Unit)? = null,
    var isLoadingMore: Boolean = false,
    var onLoadMore: (() -> Unit)? = null,
    var isLoadingFirstPage: Boolean = false,
    var isError: Boolean = false,
    var onRetry: (() -> Unit)? = null,
    var emptyContent: (@Composable () -> Unit)? = null,
    var errorContent: (@Composable () -> Unit)? = null,
    var loadingContent: (@Composable () -> Unit)? = null,
    var headerContent: (@Composable () -> Unit)? = null,
    var footerContent: (@Composable () -> Unit)? = null,
    var modifier: Modifier = Modifier,
    var itemContent: @Composable (T) -> Unit = {},
    var groupedItems: List<Any>? = null,
    var groupTitleSelector: ((Any) -> String)? = null,
    var groupItemsSelector: ((Any) -> List<T>)? = null,
    var groupHeaderContent: (@Composable (String) -> Unit)? = null,
    var isStickyGroup: Boolean = true,

)
