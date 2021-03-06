## Manacher 马拉车算法
- 问题描述：<br>
求str中最长的那个回文子串
- 问题背景知识
   - 回文串定义：<br>
   “回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
   
   - 回文子串的定义：<br>
   给定字符串str，若s同时满足以下条件, 则s是str的回文子串。
      - s是str的子串
      - s是回文串
****
使用Manacher方法实现了线性的求解时间复杂度达到O(N)
   - 思想：<br>
   回文串的特性为：以中心为轴左右对称，中心轴（包含中心轴）左侧使用枚举中心轴方法，求出以该位置为中心轴对应的最大回文串长度，中心轴右侧的元素应与对称的左侧最大回文串长度或者该位置到此回文串末尾的距离（取最小值），然后以此位置为中心轴依次向两侧延伸求回文串长度，这样大程度的减少了比较次数。
   - 思路：<br>
   回文串长度存在单数（奇数）和双数（偶数）的区别, 若在每个字符中间插入一位填充位，即（bob --> #b#o#b#   
   noon --> #n#o#o#n#）,使回文串都为奇数<br> 
