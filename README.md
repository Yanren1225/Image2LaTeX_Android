# Image2LaTeX

Image To LaTeX 是使用 [Mathpix](https://mathpix.com/) API 编写的 Android 应用，使用了 Kotlin 和 JetPack 技术。

## 构建

1. 安装 [Android SDK](#AndroidSDK)、[JDK](#JDK) 以及 Kotlin 插件
2. Clone 本项目并定位，运行

```shell
./gradlew assembleDebug
```

如果你是 Windows 用户请运行

```shell
gradlew assembleDebug
```

## TODO：

- [x] 基本的图像上传处理
- [x] LaTeX 预览
- [ ] 更多请求时选项
- [ ] 多个 API Key 切换
- [ ] 图像裁剪处理
- [ ] UI 动画优化
- [ ] more

### AndroidSDK

安装 [Android SDK 和 Android Studio](https://developer.android.com/studio/)。目前不支持使用任何其他IDE。

### JDK

[OpenJDK](https://jdk.java.net/jmc/8/)
