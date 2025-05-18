package com.ghn.composelistkit.wrapper

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.roundToInt


/**
 * @author 浩楠
 *
 * @date 2025/5/4-12:26
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
@Composable
fun <T> SimpleSwipeToDeleteWrapper(
    items: SnapshotStateList<T>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    keySelector: ((T) -> Any)? = null,
    onDelete: (T) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    backgroundContent: @Composable (T) -> Unit = { item ->
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
                    .clickable { onDelete(item) }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    },
    itemContent: @Composable (T) -> Unit
) {
    val deleteWidth = with(LocalDensity.current) { 100.dp.toPx() }
    LazyListContent<T>(
        modifier = modifier,
        listState = listState,
        contentBuilder = {
            itemsIndexed(
                items = items,
                key = { _, item -> keySelector?.invoke(item) ?: item.hashCode() }
            ) { _, item ->
                val offsetX = remember { Animatable(0f, Float.VectorConverter) }
                val scope = rememberCoroutineScope()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(contentPadding)
                    ) {
                        backgroundContent(item)
                    }

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                            .fillMaxWidth()
                            .padding(contentPadding)
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures(
                                    onHorizontalDrag = { _, delta ->
                                        scope.launch {
                                            val newOffset = (offsetX.value + delta).coerceIn(-deleteWidth, 0f)
                                            offsetX.snapTo(newOffset)
                                        }
                                    },
                                    onDragEnd = {
                                        scope.launch {
                                            if (offsetX.value < -deleteWidth / 2) {
                                                offsetX.animateTo(-deleteWidth)
                                            } else {
                                                offsetX.animateTo(0f)
                                            }
                                        }
                                    }
                                )
                            }
                            .background(Color.White)
                    ) {
                        itemContent(item)
                    }
                }
            }
        }
    )
}








