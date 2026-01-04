UI 设计系统提示（严格遵守）

技术栈：Vue 3 + Element Plus（以下简称 ElPlus）

总体风格目标：现代、优雅、极简主义，具备高级艺术审美。界面应给人平静、专业、精致的感觉，避免杂乱与视觉噪音。参考设计灵感：Apple 人机界面指南 + Linear App + Vercel 官网的极简美学。

核心原则：
1. 颜色系统
   - 主色调：深灰蓝基调（Primary #24292e 或 #1e40af），辅助色使用柔和的蓝灰（如 #6366f1 的低饱和变体）。
   - 背景：浅色模式 #ffffff，深色模式 #0d1117。
   - 文本：主文字 #24292e（浅）/#c9d1d9（深），次级文字 opacity 0.65。
   - 成功/警告/错误使用 ElPlus 默认色，但降低饱和度 10-20% 以保持优雅。
   - 禁止使用纯红、纯绿、纯黄等高饱和警示色，除非必要状态提示。

2. 排版与文字
   - 字体：系统默认 sans-serif（-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantonese, "Fira Sans", "Droid Sans", "Helvetica Neue", sans-serif）
   - 标题：font-weight 600，字号层次严格（2xl 24px, xl 20px, lg 18px, base 16px, sm 14px, xs 13px）
   - 正文：line-height 1.65，段落间距 1.5em。
   - 所有文字禁止全大写，除非必要标签。

3. 间距与布局
   - 统一使用 8px 网格系统，所有 padding/margin 必须为 8 的倍数（8, 16, 24, 32, 40...）。
   - 卡片、表单区域内外边距统一 24px。
   - 页面级容器最大宽度 1280px，水平居中。
   - 垂直节奏：组件间垂直间距优先 16px 或 24px，避免奇数或随意值。

4. 组件使用规范
   - 按钮：优先使用 <el-button type="primary" plain> 或 text 类型，避免大面积实心按钮。圆角 6px。
   - 输入框、下拉框：统一使用 medium size，圆角 6px，禁用边框 hover 时的粗边框效果（如需可自定义 :border-hover）。
   - 表格：斑马纹浅色（background #f9fafb / #161b22），hover 行高亮柔和，表头背景透明或极浅色。
   - 卡片：轻微阴影（0 1px 3px rgba(0,0,0,0.08)），圆角 12px，内边距 24px。
   - 导航栏/侧边栏：背景使用模糊磨砂效果（backdrop-filter: blur(12px)），半透明或纯色但 opacity 0.8。
   - 图标：统一使用 Element Plus 自带图标或 remixicon，尺寸 16-20px，颜色继承文字色。

5. 交互与动画
   - 所有过渡动画时长 200-300ms，easing ease-out。
   - 禁止过度动画、弹跳、放大等夸张效果。
   - 加载状态使用淡入淡出或 skeleton 而非旋转 spinner（除非全局加载）。

6. 整体审美要求
   - 留白充足：界面元素密度不超过 40%，确保“呼吸感”。
   - 对齐严格：所有元素必须网格对齐，无视觉偏移。
   - 层次清晰：通过微弱阴影、间距、分隔线（1px #e5e7eb / #30363d）建立 Z 轴层次。
   - 禁止使用渐变背景、花哨边框、3D 效果、粒子动画等低俗装饰。

在生成或修改任何页面、组件时，必须严格复读并遵守以上所有规则，确保输出的 UI 代码在视觉上统一、优雅、专业，具有高级艺术审美感。