# CBRN

## 打包
推荐使用命令行打包

./gradlew assembleCBRNRelease

./gradlew assembleCBRNRelease

./gradlew assembleCBRNRelease


## 收集
需要收集好output.json文件 和 每次打包后mapping目录下产生的所有文件


## 加固
[360命令行加固](https://jiagu.360.cn/#/global/help/243)

#### 360加固登录
java -jar /某个具体路径/jiagu.jar -login xxxx


#### 添加签名文件
java -jar /某个具体路径/jiagu.jar -importsign /某个具体路径/keystore.jks . . .


## 定制非标版本


### 变换资源文件
1. 创建新的Flavor
2. 在`src/<新Flavor>/assets或res`下放置要求的资源文件



### 变更代码
1. 创建新的Flavor
2. 把受影响的标准版本代码**移动**到`src/common/java/`的等同位置
3. 把定制的代码写到`src/<flavor>/java/`的等同位置

## 业务
1. 主要业务在App Moudle
2. 核心开发代码在Core Moudle

## 混淆
1.暂无

