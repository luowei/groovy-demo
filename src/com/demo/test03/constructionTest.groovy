#!groovy

//Boolean测试规则示例

assert true
assert !false

//匹配器匹配时
assert ('a' =~ /./)
assert !('a' =~ /b/)

//集合不为空时
assert [1]
assert ![]

//map不为空时
assert ['a':1]
assert ![:]

//string不为空时
assert 'a'
assert !''

//数字不为0时
assert 1
assert 1.1
assert 1.2f
assert 1.3g
assert 2L
assert 3G
assert !0

//其它类型非空时
assert new Object()
assert !null



def x = 1
if (x == 2){
    assert false
}

//下面这样写,编译不过
/*******************
 if( x = 2) {
    println x
 }
 ******************/

//额外的圆括号将赋值语句包围起来,使得赋值语句成为了一个子表达式;
//值3分配给变量x,然后x将被用来进行测试,由于3被任务为true
if ((x = 3)){
    println x
}
assert x == 3


//赋值表达式被限制不能作为if语句的顶级表达式,但是对于其他的控制结构语句(如
//while)没有这个限制,这是由于赋值和测试在一个表达式经常被用在while中;
def store = []
while (x = x -1){
    store << x
}
assert store == [2,1]

//死循环
while (x = 1){
    println x
    break
}


//if测试
if (true)   assert true
else   assert false

if (1){
    assert true
}else {
    assert false
}

if ('non-empty') assert true
else if (['x'])  assert false
else             assert false

if (0)           assert false
else if ([])     assert false
else             assert true

//三元操作符
def result = (1==1) ? 'ok':'failed'
assert result == 'ok'

result = 'some string'? 10 : ['x']
assert result == 10

//switch 语句
//如果一个类型实现了isCase方法,那么这个类可以作为switch的分类器
//case的顺序在groovy是很重要的,但在java中case的顺序是没有影响的
switch (10) {
    case 0              : assert false ; break
    case 0..9           : assert false ; break
    case [8,9,11]       : assert false ; break
    case Float          : assert false ; break
    case {it%3 == 0}    : assert false ; break
    case ~/../          : assert true  ; break
    default             : assert false ; break
}

//断言的使用
//input = new File('no such file ')
//assert input.exists() , "cannot find '$input.name'"
//assert input.canRead() , "cannot read '$input.canonicalPath'"
//println input.text

//使用断言在代码中内联单元测试
//匹配主机地址规则
def host = /\/\/([a-zA-Z0-9-]+(\.[a-zA-Z0-9])*?)(:|\/)/
assertHost('http://www.rootls.com:8080/bla',    host, 'www.rootls.com')
assertHost('http://www.rootls.com/bla',         host, 'www.rootls.com')
assertHost('http://127.0.0.1:8080/bla',         host, '127.0.0.1')
assertHost('http://rootls.com/bla',             host, 'rootls.com')
assertHost('http://Rootls.com/bla',             host, 'Rootls.com')

def assertHost(candidate,regex,expected){
    candidate.eachMatch(regex){assert it[1] == expected}
}


//while 循环
def list = [1,2,3]
while (list) {
    list.remove(0)
}
assert list == []

//循环体只有一行,并且没有花括号
while (list.size() < 3) list << list.size()+1
assert list == [1,2,3]


//for 循环,如果循环体只有一句,那么花括号是可选的
store = ''
for (String i in 'a'..'c') store += i
assert store == 'abc'

store = ''
for (i in [1,2,3]){
    store += i
}
assert store == '123'

def myString = 'Equivalent to Java'
store = ''
for (i in 0..< myString.size()){
    store += myString[i]
}
assert store == myString

store = ''
for (i in myString){
    store += i
}
assert store == myString

//一行一行的输出一个文件的内容
def file = new File('/home/luowei/my_projects/' +
        'groovy_projects/groovy-demo/README.md')
for (line in file) println line

//打印匹配正则表达式的所有数字
def matcher = '12xy3'=~/\d/
for (match in matcher) println match

//如果容器对象为null,那么将没有结果输出
for (a in null) println 'This will not be printed!'

//如果groovy通过任何方法都不能确定一个容器对象是iterable,
//仅仅使用容器对象本身进行一次遍历
for (b in new Object()) println "Printed once for object $b"

//or循环不是一个闭包,方法体是代码块
for (c in 0..9) { println x }

//这样用的循环体就是一个闭包了
(0..9).each { println it }

//break,continue使用
a = 1
while (true){
    a++
    break
}
assert a == 2

for (i in 0..10){
    if (i == 0) continue
    a++
    if (i > 0) break
}
assert a ==3

//throw,try-catch-finally使用
def myMethod(){
    throw new IllegalAccessException()
}
def log = []
try {
    myMethod()
} catch (Exception e){
    log << e.toString()
} finally {
    log << 'finally'
}
assert log.size() == 2























println 'ok'