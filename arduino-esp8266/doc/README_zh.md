# arduino esp8266 wifi 插座

基于 esp8266 模组的wifi插座。

## 硬件介绍

#### 实物展示
实物展示图如下：  

![实物图1](img/实物-1.jpg)

![实物图2](img/实物-2.jpg)

#### 结构图

![结构图](img/结构图.png)

说明：
- AC-DC 模块用于提供5V直流电源，给wifi模组和继电器使用。
- ESP8266 的IO口输出驱动能力有限，搭配三极管驱动电路，驱动继电器。
- 继电器选用磁保持继电器，优点是只有在改变继电器状态时才需要驱动电流，常开和常关时均不需要控制电流，降低功耗。
- AC-DC 模块使用接线板自带的开关控制，非必须，这样可以选择关闭控制模块，仍按照普通接线板使用。

三极管驱动电路说明：

#### 硬件列表
 
**esp8266模组：**  
功能说明
模块名称  
购买链接(https://detail.tmall.com/item.htm?id=537012923975&spm=a1z09.2.0.0.146e2e8djPbwfF&_u=djreddb51f4)

**DC-DC模块：**  
功能说明
模块名称
购买链接(https://detail.tmall.com/item.htm?id=526376083444&spm=a1z09.2.0.0.67002e8dQsKVvh&_u=djreddb777b)

**磁保持继电器：**  
功能说明
模块名称
购买链接(https://item.taobao.com/item.htm?spm=a1z09.2.0.0.67002e8dQsKVvh&id=522809751138&_u=djreddb28f8)

## 烧写说明


## 使用说明

#### MAC地址绑定固定IP

#### 使用curl命令测试

#### 使用web页面控制
