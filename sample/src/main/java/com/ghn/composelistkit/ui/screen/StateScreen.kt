package com.ghn.composelistkit.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghn.composelistkit.ComposeListKit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author 浩楠
 *
 * @date 2025/4/26-15:44
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun StateScreen() {
    // 状态控制
    val items = remember { mutableStateListOf<String>() }
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoadingFirstPage by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔥 顶部控制按钮区
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                // 显示首次加载中
                isLoadingFirstPage = true
                isError = false
                items.clear()
                coroutineScope.launch {
                    delay(2000)
                    isLoadingFirstPage = false
                    items.addAll((1..20).map { "Item $it" })
                }
            }) {
                Text(text = "加载中")
            }

            Button(onClick = {
                // 显示错误页
                isError = true
                isLoadingFirstPage = false
                items.clear()
            }) {
                Text(text = "错误页")
            }

            Button(onClick = {
                // 显示空页面
                isError = false
                isLoadingFirstPage = false
                items.clear()
            }) {
                Text(text = "空页面")
            }

            Button(onClick = {
                // 加载正常数据
                isError = false
                isLoadingFirstPage = false
                items.clear()
                items.addAll((1..20).map { "正常 Item $it" })
            }) {
                Text(text = "正常数据")
            }
        }
        ComposeListKit<String> {
            items(items)
            modifier(Modifier.weight(1f))

            isRefreshing(isRefreshing)
            onRefresh {
                isRefreshing = true
                coroutineScope.launch {
                    // 模拟刷新逻辑
                    isRefreshing = false
                }
            }

            isLoadingFirstPage(isLoadingFirstPage)
            isError(isError)
            onRetry {
                isError = false
                isLoadingFirstPage = true
                coroutineScope.launch {
                    // 模拟重试加载
                    isLoadingFirstPage = false
                }
            }

            isLoadingMore(isLoadingMore)
            onLoadMore {
                isLoadingMore = true
                coroutineScope.launch {
                    delay(1500)
                    val next = (items.size + 1)..(items.size + 20)
                    items.addAll(next.map { "More Item $it" })
                    isLoadingMore = false
                }
            }

            emptyContent {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "这里空空如也～")
                }
            }

            errorContent {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "加载失败，点我重试")
                }
            }

            loadingContent {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            itemContent { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

