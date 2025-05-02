package com.ghn.composelistkit.listkit
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

    // 设置列表数据
    fun items(list: List<T>) = apply { config.items = list }

    // 设置列表 item 的 key 提取方式
    fun keySelector(selector: (T) -> Any) = apply { config.keySelector = selector }

    // 设置是否处于刷新中
    fun isRefreshing(value: Boolean) = apply { config.isRefreshing = value }

    // 设置下拉刷新回调
    fun onRefresh(action: () -> Unit) = apply { config.onRefresh = action }

    // 设置是否处于加载更多中
    fun isLoadingMore(value: Boolean) = apply { config.isLoadingMore = value }

    // 设置加载更多回调
    fun onLoadMore(action: () -> Unit) = apply { config.onLoadMore = action }

    // 设置是否在加载第一页（用于展示 Loading 页）
    fun isLoadingFirstPage(value: Boolean) = apply { config.isLoadingFirstPage = value }

    // 设置是否处于错误状态
    fun isError(value: Boolean) = apply { config.isError = value }

    // 设置重试按钮点击回调
    fun onRetry(action: () -> Unit) = apply { config.onRetry = action }

    // 设置空页面内容
    fun emptyContent(content: @Composable () -> Unit) = apply { config.emptyContent = content }

    // 设置错误页面内容
    fun errorContent(content: @Composable () -> Unit) = apply { config.errorContent = content }

    // 设置加载中页面内容
    fun loadingContent(content: @Composable () -> Unit) = apply { config.loadingContent = content }

    // 设置列表头部 UI
    fun header(content: @Composable () -> Unit) = apply { config.headerContent = content }

    // 设置列表尾部 UI
    fun footer(content: @Composable () -> Unit) = apply { config.footerContent = content }

    // 设置外部修饰符
    fun modifier(modifier: Modifier) = apply { config.modifier = modifier }

    // 设置普通 item 渲染方式
    fun itemContent(content: @Composable (T) -> Unit) = apply { config.itemContent = content }

    // 最终构建配置对象
    fun build(): ComposeListKitConfig<T> {
        // 如果未设置拖拽 itemContent，则复用普通 itemContent
        if (config.dragItemContent == null) {
            config.dragItemContent = { item, _ -> config.itemContent(item) }
        }
        return config
    }

    // 设置分组数据和结构
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

    // 设置分组标题 UI
    fun groupHeaderContent(content: @Composable (String) -> Unit) = apply {
        config.groupHeaderContent = content
    }

    // 设置是否吸顶
    fun isStickyGroup(sticky: Boolean) = apply {
        config.isStickyGroup = sticky
    }

    // 设置拖拽手柄组件
    fun dragHandle(content: @Composable () -> Unit) = apply {
        config.dragHandle = content
    }

    // 设置是否使用长按触发拖拽
    fun useLongPress(value: Boolean) = apply {
        config.useLongPress = value
    }

    // 设置是否拖拽高亮当前项
    fun enableHighlight(value: Boolean) = apply {
        config.enableHighlight = value
    }

    // 设置拖拽时 item 的 UI 渲染函数
    fun dragItemContent(content: @Composable (T, Boolean) -> Unit) = apply {
        config.dragItemContent = content
    }
}

