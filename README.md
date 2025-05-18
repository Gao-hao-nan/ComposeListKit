# 🧰 ComposeListKit 示例教程项目

ComposeListKit 是一个基于 Jetpack Compose 构建的高扩展性通用列表组件，内置刷新加载、状态页、分组、拖拽、滑动删除、Header/Footer 等功能。

本项目为 ComposeListKit 的官方示例项目，提供完整的功能演示与示例源码。

---

## 📚 功能总览

| 模块名称     | 功能描述                          |
|--------------|-----------------------------------|
| 普通列表     | 最基础的 items + itemContent 列表 |
| 刷新加载更多 | 下拉刷新 + 分页加载               |
| 状态页面     | 支持加载中、空、错误、重试等状态  |
| 添加头和尾   | 添加 header/footer 组件            |
| 吸顶分组     | 群组列表，支持 Sticky 吸顶分组标题 |
| 拖拽排序     | 长按拖拽列表项调整顺序            |
| 侧滑删除     | 左滑滑动删除                      |

---

## 🚀 快速开始

1. 克隆项目

```bash
git clone https://github.com/Gao-hao-nan/ComposeListKit.git
cd ComposeListKit/sample
````

2. Android Studio 打开项目，直接运行 `sample` 模块即可。

---

## 🧪 示例详解（含源码）

### ✅ 普通列表（SimpleDetailScreen）

```kotlin
@Composable
fun SimpleDetailScreen() {
    val items = remember { mutableStateListOf<Int>() }
    LaunchedEffect(Unit) {
        items.addAll(1..20)
    }
    ComposeListKit<Int> {
        items(items)
        itemContent { item ->
            Text(
                text = "Item $item",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
```

用于展示最基础的 `items` + `itemContent` 列表形式。

---

### 🔄 刷新加载更多（RefreshableDetailScreen）

```kotlin
@Composable
fun RefreshableDetailScreen() {
    val items = remember { mutableStateListOf<String>() }
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        items.addAll((1..20).map { "Item $it" })
    }

    ComposeListKit<String> {
        items(items)
        isRefreshing(isRefreshing)
        onRefresh {
            isRefreshing = true
            coroutineScope.launch {
                delay(1000)
                items.clear()
                items.addAll((101..120).map { "Refreshed $it" })
                isRefreshing = false
            }
        }
        isLoadingMore(isLoadingMore)
        onLoadMore {
            isLoadingMore = true
            coroutineScope.launch {
                delay(1000)
                val more = (items.size + 1)..(items.size + 20)
                items.addAll(more.map { "Loaded $it" })
                isLoadingMore = false
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
```

---

### 📦 状态页面（StateScreen）

支持首次加载中、空数据、错误状态、手动控制状态变更。

```kotlin
@Composable
fun StateScreen() {
    val items = remember { mutableStateListOf<String>() }
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoadingFirstPage by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                isLoadingFirstPage = true
                isError = false
                items.clear()
                coroutineScope.launch {
                    delay(2000)
                    isLoadingFirstPage = false
                    items.addAll((1..20).map { "Item $it" })
                }
            }) { Text("加载中") }

            Button(onClick = {
                isError = true
                isLoadingFirstPage = false
                items.clear()
            }) { Text("错误页") }

            Button(onClick = {
                isError = false
                isLoadingFirstPage = false
                items.clear()
            }) { Text("空页面") }

            Button(onClick = {
                isError = false
                isLoadingFirstPage = false
                items.clear()
                items.addAll((1..20).map { "正常 Item $it" })
            }) { Text("正常数据") }
        }

        ComposeListKit<String> {
            items(items)
            modifier(Modifier.weight(1f))
            isRefreshing(isRefreshing)
            onRefresh { isRefreshing = false }
            isLoadingFirstPage(isLoadingFirstPage)
            isError(isError)
            onRetry {
                isError = false
                isLoadingFirstPage = true
                coroutineScope.launch {
                    delay(1000)
                    isLoadingFirstPage = false
                }
            }
            isLoadingMore(isLoadingMore)
            onLoadMore {
                isLoadingMore = true
                coroutineScope.launch {
                    delay(1500)
                    items.addAll((items.size + 1..items.size + 20).map { "More Item $it" })
                    isLoadingMore = false
                }
            }
            emptyContent {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("这里空空如也～")
                }
            }
            errorContent {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("加载失败，点我重试")
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
```

---

### 🧱 添加头和尾（SampleHeaderFooterScreen）

```kotlin
@Composable
fun SampleHeaderFooterScreen() {
    val items = remember { mutableStateListOf<String>().apply { addAll((1..5).map { "Item $it" }) } }
    var index by remember { mutableStateOf(items.size) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                index += 1
                items.add(0, "Item $index (头部添加)")
            }) { Text("添加到头部") }

            Button(onClick = {
                index += 1
                items.add("Item $index (尾部添加)")
            }) { Text("添加到尾部") }
        }

        ComposeListKit<String> {
            items(items)
            modifier(Modifier.weight(1f))
            header {
                Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text("我是 Header 区域", style = MaterialTheme.typography.titleMedium)
                }
            }
            footer {
                Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text("我是 Footer 区域", style = MaterialTheme.typography.titleMedium)
                }
            }
            itemContent { item ->
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
    }
}
```

---

### 📂 吸顶分组（StickySectionScreen）

```kotlin
data class Category(val groupName: String, val items: List<Item>)
data class Item(val id: Int, val name: String)

@Composable
fun rememberSampleGroups(): List<Category> {
    return remember {
        listOf(
            Category("编程类", listOf(Item(1, "Kotlin实战"), Item(2, "Compose揭秘"))),
            Category("游戏类", listOf(Item(3, "原神"), Item(4, "塞尔达")))
            // 省略部分，可自行添加更多分类
        )
    }
}

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
                Text(text = title, fontWeight = FontWeight.Bold)
            }
        }
        itemContent { item ->
            Text(
                text = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}
```

---

### ✋ 拖拽排序（DragReorderScreen）

```kotlin
@Composable
fun DragReorderScreen() {
    val list = remember { mutableStateListOf("Kotlin", "Java", "Goland", "C", "C++") }
    ComposeListKit<String> {
        items(list)
        useLongPress(true)
        dragHandle {
            Icon(imageVector = Icons.Default.Menu, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
        }
        dragItemContent { item, _ ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Black, shape = RoundedCornerShape(18.dp))
                    .padding(horizontal = 5.dp, vertical = 4.dp)
            ) {
                Text(text = item, color = Color.White)
            }
        }
    }
}
```

---

### 🧹 侧滑删除（SimpleSwipeToDeleteScreen）

```kotlin
@Composable
fun SimpleSwipeToDeleteScreen() {
    val items = remember { mutableStateListOf("A", "B", "C", "D") }
    val itemPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

    ComposeListKit<String> {
        items(items)
        onSwipeDelete { item -> items.remove(item) }
        swipeContentPadding(itemPadding)
        itemContent { item ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 2.dp
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
```

---

## 📦 JitPack 引入方式

1. 添加 JitPack 到 `settings.gradle.kts`：

```kotlin
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

2. 添加依赖到 `build.gradle.kts`：

```kotlin
dependencies {
    implementation("com.github.Gao-hao-nan:ComposeListKit:1.0.0-beta01")
}
```

---

## ❤️ 贡献与支持

如果你觉得这个项目对你有帮助：

* ⭐ Star 一下
* 📬 提 Issue 或 PR 来贡献你的想法
* 📣 推荐给其他 Compose 开发者！

---

本教程由 [ComposeListKit](https://github.com/yourname/ComposeListKit) 团队维护。
