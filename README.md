# Online-player-check

This is a open-source BungeeCord plugin that out put html when player join or leave.

这是一个用于统计玩家加入和退出的开源蹦极服插件

You need a website to use this plugin

你必须有一个网站以使用本插件

By [sharkmc.cn](http://sharkmc.cn)

作者:[sharkmc.cn](http://sharkmc.cn)



I know. I know. I wrote a piece of crap and tons of useless code with terrible coding habits and something exteremely hard to update.

啊对对对 对对对 我做了一坨一堆冗余代码和难以更新的玩意

Then please make a new one and keep it updated before bully me.

请你做一个更好的再来骂我



## Feature 特色

· Trigger when player connect or disconnect

  在玩家加入或退出时实时响应

· Output UTF-8 html with modern UI that is easy to iframe

  输出标准UTF-8编码的具有现代UI设计且易于iframe的网页

· Show Current,max and average player count on html

  在网页显示当前，最高，平均玩家数

· Display a graph create by ctx in js that show how player count float in 24h

  显示由js中ctx制作的24小时玩家波动情况图像

· Chinese and English support

  支持中英文

· Customize settings

  自定义设置

· High/Low performance mode

  高/低性能模式



## How to config 如何设置

```
OCB config guide Version 1 
    1 "version" : 1 版本：1
    2 "language" : Chinese=1/English=2 语言：中文=1，英文=2
    3 "logmode" : 1/2/3 # mode 1: Basic log output; 输出log频率 基础=1
                        # mode 2: medium log output; 一般=2
                        # mode 3: full log output; 完全=3
    4 "local_path" : # path of ocb config,history hour data,html output and others. 本地路径
                    # MUST USE \\ instead of \ and there must be \\ in end 必须使用 \\ 而非 \ 且末尾必须有 /
                    # DO NOT USE " or ' 不要使用" 或 '
    5 "online_path" : # path for ocb to write links. 网页路径
                    # MUST USE / instead of \ and there must be / in end 必须使用 / 而非 \ 且末尾必须有 /
                    # DO NOT USE " or ' 不要使用" 或 '
    6 "rewrite log" : 1/2 # mode 1: Rewrite online_log.html when restart 是否重启时重写online_log.html 模式1=重写
                        # mode 2: Program will not rewrite online_log.html 模式2=不写
                        # WARNING: Log may stack and cause performance issues 注意：使用模式2可能会带来性能问题
    7 "write online_basic.html" : write=1/skip=2 是否写入online_basic.html 是=1 否=2 
    8 "add online_log.html" : add log to online_log.html when player connect or disconnect add=1/skip=2 是否增加online-log.html 是=1 否=2 
    9 "Count players every hour and write online_graph.html" : write=1/skip=2 是否每小时统计玩家并写入online_graph.html 是=1 否=2 
    10 "write online_pro.html" : write=1/skip=2 # Useless without online_log.html and/or online_graph.html 是否写入online_pro.html 是=1 否=2 
    11 "low performance mode" : on=1/off=2 # Output stats and html every minute instead of every time a player connect or disconnect. Useful 
    for server with lots of gamers or a server with low io performance. 低性能模式：开启后占用更低io
    example config: 示例
    1
    1
    3
    C:\\phpstudy_pro\\WWW\\
    http://sharkmc.cn/
    1
    1
    1
    1
    1
    2
```



## Tip

· I want to show a simple number on my website that shows how many people are there on my server

  我想在我的主网页上嵌套一个显示基础人数的网页

··iframe online_basic.html:

```
<!add this to anywhere you want on your website>
<iframe src="ONLINE_PATH/online/online_basic.html" scrolling="no" width="100%" height="100%" frameborder="0" allowtransparency="true" 
style="text-align: center; width: 200px;height: 30px;margin-left: 50px;"></iframe>
```



· I want to show the details of my server and i don't need index.html!

  我不需要index.html而希望直接显示具有大量细节的online_pro.html

··
redirect to online_pro.html:
```
<!write this on index.html>
<meta http-equiv="refresh" content="0; url=online/online_pro.html" />
```

### Enjoy ~
