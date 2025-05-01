package com.ghn.composelistkit.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghn.composelistkit.ComposeListKit
import com.ghn.composelistkit.wrapper.StickySectionWrapper

/**
 * @author 浩楠
 *
 * @date 2025/4/30-21:57
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
data class Category(
    val groupName: String,
    val items: List<Item>
)

data class Item(
    val id: Int,
    val name: String
)
@Composable
fun rememberSampleGroups(): List<Category> {
    return remember {
        listOf(
            Category(
                groupName = "📚 编程类",
                items = listOf(
                    Item(1, "Kotlin实战"),
                    Item(2, "Compose揭秘")
                )
            ),
            Category(
                groupName = "🎮 游戏类",
                items = listOf(
                    Item(3, "原神"),
                    Item(4, "塞尔达传说")
                )
            ),
            Category(
                groupName = "🎵 音乐类",
                items = listOf(
                    Item(5, "周杰伦精选"),
                    Item(6, "LoFi Chill")
                )
            ),
            Category(
                groupName = "🎬 电影类",
                items = listOf(
                    Item(7, "盗梦空间"),
                    Item(8, "星际穿越")
                )
            ),
            Category(
                groupName = "📖 小说类",
                items = listOf(
                    Item(9, "三体"),
                    Item(10, "哈利波特")
                )
            ),
            Category(
                groupName = "🧪 科学类",
                items = listOf(
                    Item(11, "时间简史"),
                    Item(12, "量子力学入门")
                )
            ),
            Category(
                groupName = "🧘 健康类",
                items = listOf(
                    Item(13, "冥想练习"),
                    Item(14, "健身打卡")
                )
            ),
            Category(
                groupName = "✈️ 旅行类",
                items = listOf(
                    Item(15, "中国攻略"),
                    Item(16, "世界环游")
                )
            ),
            Category(
                groupName = "📷 摄影类",
                items = listOf(
                    Item(17, "手机摄影技巧"),
                    Item(18, "构图训练")
                )
            ),
            Category(
                groupName = "💼 职场类",
                items = listOf(
                    Item(19, "简历优化"),
                    Item(20, "面试技巧")
                )
            )
        )

    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickySectionScreen() {
    val groups = rememberSampleGroups()
    ComposeListKit<Item> {
        groupedItems(
            groups = groups,
            groupTitleSelector = { it.groupName },
            groupItemsSelector = { it.items }
        )
        groupHeaderContent { title ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }
        itemContent { item ->
            Text(
                text = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                fontSize = 15.sp
            )
        }
    }
}

