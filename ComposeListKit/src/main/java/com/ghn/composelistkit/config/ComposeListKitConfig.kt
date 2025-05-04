package com.ghn.composelistkit.config

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    // 列表数据
    var items: List<T> = emptyList(),

    // item 唯一 key，用于 Compose 列表 diff（如 LazyColumn 的 key）
    var keySelector: ((T) -> Any)? = null,

    // 是否正在刷新
    var isRefreshing: Boolean = false,

    // 下拉刷新回调
    var onRefresh: (() -> Unit)? = null,

    // 是否正在加载更多
    var isLoadingMore: Boolean = false,

    // 上拉加载更多回调
    var onLoadMore: (() -> Unit)? = null,

    // 是否正在加载第一页（用于展示 loading 状态页）
    var isLoadingFirstPage: Boolean = false,

    // 是否处于错误状态（用于展示错误状态页）
    var isError: Boolean = false,

    // 点击错误页重试的回调
    var onRetry: (() -> Unit)? = null,

    // 数据为空时的 UI（Empty Page）
    var emptyContent: (@Composable () -> Unit)? = null,

    // 加载失败时的 UI（Error Page）
    var errorContent: (@Composable () -> Unit)? = null,

    // 正在加载时的 UI（Loading Page）
    var loadingContent: (@Composable () -> Unit)? = null,

    // 列表头部 UI
    var headerContent: (@Composable () -> Unit)? = null,

    // 列表尾部 UI
    var footerContent: (@Composable () -> Unit)? = null,

    // Compose Modifier（列表外层修饰符）
    var modifier: Modifier = Modifier,

    // 单个 item 的内容渲染函数
    var itemContent: @Composable (T) -> Unit = {},

    // 分组列表的数据源（每个分组是一个 group item）
    var groupedItems: List<Any>? = null,

    // 从 group item 中提取分组标题
    var groupTitleSelector: ((Any) -> String)? = null,

    // 从 group item 中提取子项列表
    var groupItemsSelector: ((Any) -> List<T>)? = null,

    // 分组 header 的渲染 UI
    var groupHeaderContent: (@Composable (String) -> Unit)? = null,

    // 是否启用分组吸顶效果
    var isStickyGroup: Boolean = true,

    // 拖拽是否使用长按触发
    var useLongPress: Boolean = false,

    // 拖拽时是否高亮当前 item
    var enableHighlight: Boolean = true,

    // 拖拽手柄组件（显示在 item 中）
    var dragHandle: (@Composable () -> Unit)? = null,

    // 拖拽时 item 的渲染 UI（可带 isDragging 状态）
    var dragItemContent: (@Composable (item: T, isDragging: Boolean) -> Unit)? = null,

    // 是否启用侧滑删除
    var onSwipeDelete: ((T) -> Unit)? = null,

    // 侧滑背景自定义内容（默认删除按钮）
    var swipeBackground: (@Composable (T) -> Unit)? = null,

    // 内容边距（用于背景与 itemContent 同步）
    var swipeContentPadding: PaddingValues = PaddingValues(0.dp)

)

