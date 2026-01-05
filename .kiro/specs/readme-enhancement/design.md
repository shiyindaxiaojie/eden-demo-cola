# Design Document

## Overview

本设计文档描述如何完善 eden-demo-cola 项目的英文版 README.md 文件，使其内容与中文版 README-zh-CN.md 保持一致。设计目标是在保持英文版现有的现代化 Markdown 格式（emoji、表格等）的基础上，补充所有缺失的章节和内容。

### Design Goals

1. 保持英文版的现代化格式风格（使用 emoji 图标、表格等）
2. 补充所有缺失的章节和详细说明
3. 确保内容的准确翻译和本地化
4. 保持文档结构的一致性和可读性
5. 使英文版行数接近中文版（目标：110-130 行）

### Design Principles

1. **内容完整性**：确保所有重要信息都被翻译和包含
2. **格式一致性**：在两个版本中使用相似的结构和组织方式
3. **本地化适配**：根据英文读者习惯调整表达方式
4. **可维护性**：使用清晰的章节划分，便于后续维护

## Architecture

### Document Structure

英文版 README.md 将采用以下结构：

```
1. Header (Logo, Title, Badges)
2. Introduction (with WIKI reference)
3. Architecture
   - Component Diagram
   - Component Overview Table
4. Execution Flow (Sequence Diagram)
5. Getting Started
   - Prerequisites
   - Installation
   - Usage
     - Quick Start
     - Configuration (with detailed YAML examples)
6. Deployment
   - FatJar
   - Assembly
   - Jib
   - Docker
   - Helm
7. Versioning
8. Continuous Integration
   - CODING CI
   - Codeup CI
9. Best Practices
   - DDD (Domain-Driven Design)
   - Git Workflow
   - CAT (Observability)
   - Sentinel (Traffic Management)
   - Arthas (Diagnostics)
10. Changelog
11. License
```

### Content Mapping

| 中文章节 | 英文章节 | 状态 |
|---------|---------|------|
| WIKI 引用 | WIKI Reference | 需要添加 |
| 组件构成 | Architecture | 已存在（需要调整） |
| 运行流程 | Execution Flow | 需要添加 |
| 如何构建 | Getting Started | 已存在 |
| 如何启动 | Getting Started | 已存在 |
| 微调配置 | Configuration | 已存在（需要补充详细 YAML） |
| FatJar 部署 | Deployment - FatJar | 已存在 |
| Assembly 部署 | Deployment - Assembly | 需要添加 |
| Jib 部署 | Deployment - Jib | 需要添加 |
| Docker 部署 | Deployment - Docker | 已存在 |
| Helm 部署 | Deployment - Helm | 已存在 |
| 版本规范 | Versioning | 需要添加 |
| 持续集成 | Continuous Integration | 需要添加 |
| 最佳实践 | Best Practices | 需要添加 |
| 变更日志 | Changelog | 已存在 |

## Components and Interfaces

### Content Components

#### 1. WIKI Reference Component
- **位置**：Introduction 章节末尾
- **格式**：引用块（blockquote）或提示框
- **内容**：链接到 GitHub WIKI

#### 2. Execution Flow Component
- **位置**：Architecture 章节之后
- **格式**：独立章节，包含图片
- **内容**：时序图展示系统运行流程

#### 3. Detailed Configuration Component
- **位置**：Getting Started - Configuration 子章节
- **格式**：代码块（YAML）
- **内容**：
  - Nacos 配置完整示例
  - H2 数据库配置
  - MySQL 数据源配置

#### 4. Extended Deployment Component
- **位置**：Deployment 章节
- **格式**：子章节，包含代码块
- **内容**：
  - Assembly 打包和启动说明
  - Jib 构建命令

#### 5. Versioning Component
- **位置**：独立章节
- **格式**：列表和表格
- **内容**：
  - 版本号格式说明
  - 版本类型示例
  - 兼容性规则

#### 6. CI/CD Component
- **位置**：独立章节
- **格式**：子章节，包含图片和链接
- **内容**：
  - 工具选型说明
  - CODING 集成示例
  - Codeup 占位符

#### 7. Best Practices Component
- **位置**：独立章节
- **格式**：多个子章节，包含图片和链接
- **内容**：
  - DDD 说明
  - Git 工作流
  - CAT 监控
  - Sentinel 流控
  - Arthas 诊断

## Data Models

### Translation Mapping

```typescript
interface TranslationMapping {
  chinese: string;
  english: string;
  context?: string;
}

const keyTerms: TranslationMapping[] = [
  { chinese: "组件构成", english: "Architecture" },
  { chinese: "运行流程", english: "Execution Flow" },
  { chinese: "如何构建", english: "Getting Started" },
  { chinese: "如何启动", english: "Usage" },
  { chinese: "微调配置", english: "Configuration" },
  { chinese: "如何部署", english: "Deployment" },
  { chinese: "版本规范", english: "Versioning" },
  { chinese: "持续集成", english: "Continuous Integration" },
  { chinese: "最佳实践", english: "Best Practices" },
  { chinese: "适配层", english: "Adapter Layer" },
  { chinese: "应用层", english: "Application Layer" },
  { chinese: "领域层", english: "Domain Layer" },
  { chinese: "基础设施层", english: "Infrastructure Layer" },
  { chinese: "六边形架构", english: "Hexagonal Architecture" },
  { chinese: "入站适配器", english: "Inbound Adapter" },
  { chinese: "出站适配器", english: "Outbound Adapter" },
  { chinese: "防腐层", english: "Anti-Corruption Layer (ACL)" },
  { chinese: "依赖倒置", english: "Dependency Inversion" },
];
```

### Document Sections

```typescript
interface DocumentSection {
  id: string;
  title: string;
  level: number; // 1 = ##, 2 = ###
  content: string;
  subsections?: DocumentSection[];
  images?: string[];
  codeBlocks?: CodeBlock[];
}

interface CodeBlock {
  language: string;
  content: string;
  caption?: string;
}
```

## 
Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system-essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

基于需求文档中的验收标准，我们定义以下正确性属性来验证文档的完整性和一致性：

### Property 1: Image URL Consistency
*For any* image reference that appears in both README files (such as architecture diagrams, flow charts, or tool screenshots), the image URL should be identical in both versions.
**Validates: Requirements 2.2**

### Property 2: Section Structure Consistency
*For any* major section (Architecture, Getting Started, Deployment, etc.), if it exists in the Chinese version, it should also exist in the English version with corresponding content.
**Validates: Requirements 8.1**

### Property 3: Section Order Consistency
*For any* pair of consecutive sections in the Chinese version, the same sections should appear in the same order in the English version.
**Validates: Requirements 8.2**

### Property 4: Line Count Proximity
*For any* completed documentation pair (README.md and README-zh-CN.md), the difference in line count should be within 20 lines (accounting for language differences).
**Validates: Requirements 8.4**

## Error Handling

### Missing Content Detection

在文档更新过程中，需要处理以下潜在问题：

1. **图片链接失效**
   - 检测：验证所有图片 URL 的可访问性
   - 处理：使用备用 CDN 链接或本地图片

2. **代码块格式错误**
   - 检测：验证 YAML 代码块的语法正确性
   - 处理：修正缩进和格式问题

3. **链接失效**
   - 检测：验证所有外部链接的有效性
   - 处理：更新或移除失效链接

4. **翻译不一致**
   - 检测：对比关键术语的翻译
   - 处理：使用统一的术语表

### Validation Rules

1. **章节完整性验证**
   - 所有中文版的主要章节必须在英文版中有对应
   - 子章节的层级结构应保持一致

2. **内容完整性验证**
   - 所有代码示例必须完整且可执行
   - 所有图片链接必须有效
   - 所有外部链接必须可访问

3. **格式一致性验证**
   - Markdown 语法正确
   - 代码块语言标记正确
   - 列表和表格格式规范

## Testing Strategy

### Unit Testing

由于这是文档更新任务，传统的单元测试不太适用。但我们可以进行以下验证：

1. **内容存在性测试**
   - 测试每个必需章节是否存在
   - 测试关键内容（如 WIKI 链接、部署命令等）是否存在
   - 测试代码块是否包含必要的配置项

2. **格式验证测试**
   - 测试 Markdown 语法是否正确
   - 测试代码块语言标记是否正确
   - 测试链接格式是否正确

3. **内容匹配测试**
   - 测试图片 URL 是否与中文版一致
   - 测试关键术语的翻译是否正确
   - 测试章节顺序是否一致

### Property-Based Testing

使用 Node.js 的 `fast-check` 库进行属性测试：

1. **Property Test 1: Image URL Consistency**
   - 生成：读取两个 README 文件，提取所有图片 URL
   - 验证：对于相同位置的图片，URL 应该相同
   - 标记：**Feature: readme-enhancement, Property 1: Image URL Consistency**

2. **Property Test 2: Section Structure Consistency**
   - 生成：提取两个文件的章节标题列表
   - 验证：中文版的所有主要章节在英文版中都有对应
   - 标记：**Feature: readme-enhancement, Property 2: Section Structure Consistency**

3. **Property Test 3: Section Order Consistency**
   - 生成：提取两个文件的章节顺序
   - 验证：章节的相对顺序应该一致
   - 标记：**Feature: readme-enhancement, Property 3: Section Order Consistency**

4. **Property Test 4: Line Count Proximity**
   - 生成：统计两个文件的行数
   - 验证：行数差距应在 20 行以内
   - 标记：**Feature: readme-enhancement, Property 4: Line Count Proximity**

### Manual Testing

1. **可读性测试**
   - 人工阅读英文版，确保语句通顺
   - 验证技术术语的准确性
   - 检查格式的美观性

2. **链接测试**
   - 点击所有外部链接，确保可访问
   - 验证图片是否正常显示
   - 测试锚点链接是否正确跳转

3. **对比测试**
   - 并排对比两个版本，确保内容对应
   - 验证代码示例的一致性
   - 检查图片和截图的对应关系

## Implementation Approach

### Phase 1: Content Analysis
1. 详细对比两个 README 文件
2. 列出所有缺失的章节和内容
3. 准备翻译术语表

### Phase 2: Content Translation
1. 翻译缺失的章节标题
2. 翻译章节内容
3. 调整代码示例和命令

### Phase 3: Content Integration
1. 在英文版中插入新章节
2. 补充详细的配置示例
3. 添加图片和链接

### Phase 4: Format Optimization
1. 统一使用 emoji 图标
2. 优化表格和列表格式
3. 调整代码块的语言标记

### Phase 5: Validation
1. 运行属性测试
2. 执行内容验证
3. 进行人工审查

## Technical Considerations

### Markdown Best Practices

1. **标题层级**
   - 使用 `##` 作为主要章节标题
   - 使用 `###` 作为子章节标题
   - 避免跳级使用标题

2. **代码块**
   - 始终指定语言标记（bash, yaml, etc.）
   - 保持适当的缩进
   - 添加必要的注释

3. **链接**
   - 使用相对路径引用项目内文件
   - 使用 HTTPS 协议的外部链接
   - 为图片添加 alt 文本

4. **列表**
   - 使用 `-` 作为无序列表标记
   - 使用 `1.` 作为有序列表标记
   - 保持一致的缩进

### Localization Guidelines

1. **术语翻译**
   - 保持技术术语的准确性
   - 使用行业标准翻译
   - 必要时保留英文原文

2. **语言风格**
   - 使用简洁明了的英文
   - 避免过于复杂的句式
   - 保持专业和友好的语气

3. **文化适配**
   - 调整示例以适应国际读者
   - 考虑不同地区的技术栈偏好
   - 保持中立和包容的表达

## Dependencies

### Tools Required

1. **Markdown 编辑器**
   - VS Code with Markdown extensions
   - 或其他支持 Markdown 预览的编辑器

2. **验证工具**
   - markdownlint: Markdown 语法检查
   - markdown-link-check: 链接有效性检查
   - 自定义脚本: 内容一致性检查

3. **测试框架**
   - Node.js: 运行测试脚本
   - fast-check: 属性测试库（如果需要）
   - jest: 单元测试框架（如果需要）

### External Resources

1. **图片资源**
   - CDN: `cdn.jsdelivr.net/gh/shiyindaxiaojie/images/`
   - 备用 CDN: `cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/`

2. **参考文档**
   - GitHub WIKI: 项目详细文档
   - 各工具官方文档: Nacos, CAT, Sentinel, Arthas

## Success Criteria

文档更新成功的标准：

1. ✅ 英文版包含所有中文版的主要章节
2. ✅ 所有代码示例完整且格式正确
3. ✅ 所有图片链接有效且显示正常
4. ✅ 所有外部链接可访问
5. ✅ 章节顺序与中文版一致
6. ✅ 行数差距在 20 行以内
7. ✅ 通过所有属性测试
8. ✅ Markdown 语法检查通过
9. ✅ 人工审查通过

## Future Enhancements

1. **自动化同步**
   - 开发脚本自动检测两个版本的差异
   - 自动生成翻译建议

2. **持续验证**
   - 在 CI/CD 中集成文档验证
   - 自动检查链接有效性

3. **多语言支持**
   - 考虑添加其他语言版本
   - 建立统一的翻译流程

4. **文档生成**
   - 从单一源生成多语言版本
   - 使用模板系统管理内容
