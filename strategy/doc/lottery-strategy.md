## 游戏抽奖练习

采用策略模式分别实现以下模块的物品掉落策略
- 日转盘抽奖，必中一项物品
- 关卡模块，掉落0-1项物品（扩展，掉落物品拥有一定数目，例如金币100个）
- 副本模块，掉落2-5项物品，掉落物品不重复（扩展，部分掉落物品拥有一定数目）
- 副本模块2，掉落3项物品，掉落物品可重复（扩展，部分掉落物品拥有一定数目）


数值策划，通过设置每个物品的权重控制物品掉落的概率，权重为整数。
例如，
物品|权重
A：1
B：2
C：3
D：4
如果随机必定掉落一个时，A的概率为1/(1+2+3+4);B,C,D类似
如果随机必定掉落2个，且不重复，A概率为1/(1+2+3+4) + （2+3+4）/(1+2+3+4)* 1/(2+3+4);
如果随机必定掉落2个，允许重复，A概率为1/(1+2+3+4) + （2+3+4）/(1+2+3+4)* 1/(1+2+3+4);

掉落随机多项物品，实现策略有2种
- 设置物品数的掉落权重。（基础实现）
例如，
掉落物品数|权重
0：1
1：3
2：5
3：5
即1/(1+3+5+5)概率掉落0件物品，3/(1+3+5+5)掉落1件物品，2，3类似
- 设置掉落物品的价值，当掉落物品超过价值时，将不掉落新物品（扩展实现，比较复杂）
例如
物品|权重|价值
A：1  10
B：2  20 
C：3  10
D：4  20
当设置当前副本等级价值阈值为15时，如果第一次掉落的是A，10<15；允许掉落第二件物品，剩余物品B,C,D任意物品+10（A物品价值）> 15（价值阈值）
将不在掉落第三件物品。此时掉落A+ ？ 2件物品
再如第一次掉落是B或者D时，此时价值>阈值，不再掉落第二件物品。此时掉落B或者D 1件物品。