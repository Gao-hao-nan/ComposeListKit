# 📄 Commit 提交规范

为了提升协作效率、保障代码质量、支持自动化工具识别（如 changelog、CI/CD），请所有开发者遵循以下 **Git 提交信息规范**。

---

## 📌 格式规范

每次提交必须使用如下格式：


- **type**：必须是下表中定义的类型关键字之一
- **简短描述**：建议使用中文，控制在 50 个字符以内，描述清晰、准确
- **详细描述（可选）**：必要时补充更详细的变更说明

---

## 🧾 类型说明表

| 类型       | Emoji | 描述                                      |
|------------|-------|-------------------------------------------|
| `feat`     | ✨     | 新增功能，如添加新组件、新模块等         |
| `fix`      | 🐛     | 修复 bug，保证现有功能正常运行            |
| `refactor` | 🧹     | 代码结构调整，不涉及功能修改              |
| `perf`     | ⚡     | 性能优化，例如减少计算、减少重组等       |
| `style`    | 🎨     | 代码样式调整（缩进、空格、格式等）        |
| `docs`     | 📚     | 文档修改，例如 README、注释等            |
| `test`     | 🧪     | 添加或修改测试代码                        |
| `chore`    | 🔧     | 构建过程或辅助工具的变动（不影响业务逻辑）|
| `build`    | 📦     | 构建系统修改（如 Gradle、打包脚本）       |
| `ci`       | 🤖     | CI/CD 配置更新（如 GitHub Actions）       |
| `revert`   | ⏪     | 回滚某次提交                             |

---

## ✅ 示例提交信息

```bash
feat: 新增 ComposeListKit 的 DSL 构建器支持

fix: 修复首次加载时状态页无法显示的问题

refactor: 抽离 LazyListContent 为可复用组件

perf: 减少 recomposition，提升滚动性能

style: 调整列表缩进与间距

docs: 更新 README 使用示例

test: 增加空数据场景下的单元测试

chore: 升级 Kotlin 至 2.0.21

build: 配置 JitPack 发布元信息

ci: 新增 GitHub Actions 自动化构建

revert: ⏪ revert: 误删了 headerContent 支持逻辑
