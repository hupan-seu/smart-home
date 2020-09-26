# arduino esp8266 wifi 插座

基于 esp8266 模组的wifi插座。

## 硬件介绍

硬件介绍包含如下内容：
- 实物展示
- 结构图
- 硬件列表

详细内容：[硬件介绍](doc/hardware.md)

## 烧写说明

1. 下载和安装Arduino IDE。从[官网](https://www.arduino.cc/en/main/software?setlang=cn)上直接下载安装即可，国内速度很慢，有条件的自己找代理。

2. 添加开发板管理器地址。打开IDE，文件 -> 首选项，找到“附加开发板管理器地址”，添加http://arduino.esp8266.com/stable/package_esp8266com_index.json

3. 下载8266开发板sdk。工具 -> 开发板 -> 开发板管理器，搜索8266，选择安装。

4. 选择开发板。工具 -> 开发板，选择 “NodeMCU1.0(ESP-12E Module)”。

5. 文件 -> 打开，打开arduino-esp8266.ino文件，验证和上传，上传时注意插上板子，工具里串口号选择正确，上传成功后复位开发板。

## 使用说明

#### MAC地址绑定固定IP
为了方便调用接口，开发板下载成功后，在路由器里查看开发板的ip和mac地址，为开发板绑定一个固定的ip地址。

#### 使用curl命令测试

#### 使用web页面控制