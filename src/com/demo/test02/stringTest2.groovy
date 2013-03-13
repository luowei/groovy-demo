#!groovy


//简单的$语法
me = 'luowei'
you = 'tom'

line = "me $me-$you you"
assert line == 'me luowei-tom you'

//扩展的简单语法
date = new Date(0)
out = "Year $date.year Month $date.month Day $date.date"

//用花括号表示完整的语法
out = "Date is ${date.toGMTString()}"


//$作为一个字符串的部分
out = "my 0.02\$"
assert out == 'my 0.02$'


//虽然Gstring通常被像java.lang.String那样使用,
//但他们的实现不同于固定
//的字符串,并且动态的部分(也叫values)是独立的,如下代码的显示
me = 'Tarzan'
you = 'Jane'
line = "me $me - you $you"
assert line == 'me Tarzan - you Jane'
assert line instanceof GString
assert line.strings[0] == 'me '
assert line.strings[1] == ' - you '
assert line.values[0] == 'Tarzan'
assert line.values[1] == 'Jane'

//字符串操作
greeting = 'Hello Groovy'
assert greeting.startsWith('Hello')
assert greeting.getAt(0)=='H'
assert greeting[0]=='H'

assert greeting.indexOf('Groovy')>=0
assert greeting.contains('Groovy')

assert greeting[6..11]=='Groovy'
assert 'Hi'+greeting-'Hello' == 'Hi Groovy'

assert greeting.count('o')==3
assert 'x'.padLeft(3)=='  x'
assert 'x'.padRight(3,'_')=='x__'
assert 'x'.center(3)==' x '
assert 'x'*3=='xxx'


//使用<<操作符追加文本和下标操作符进行文本替换,
//在一个字符串上使用<<操作符将返回一个StringBuffer
greeting = 'Hello'
greeting <<= ' Groovy'

//(1)追加文本和赋值一起完成
assert greeting instanceof java.lang.StringBuffer
greeting << '!'

//(2)在StringBuffer上追加文本
assert greeting.toString() == 'Hello Groovy!'
greeting[1..4] = 'i'

//将子串”ello”替换为”i”
assert greeting.toString() == 'Hi Groovy!'




println 'ok'






