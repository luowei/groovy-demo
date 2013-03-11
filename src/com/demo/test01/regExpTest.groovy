#!groovy
//把groovy-2.x/bin添加到path下，就可以直接使用,[#!groovy]


//使用/…/语法声明了一个模式并且使用“=~”来根据模式匹配给定字符串

//保证字符串包含一个数字的系列
assert '12345'=~/\d+/


//使用“x”来替换每一个数字
assert 'xxxxx'=='12345'.replaceAll(/\d/,'x')