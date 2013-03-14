#!groovy



//map的声明
def myMap = [a:1,b:2,c:3]

assert myMap instanceof HashMap
assert myMap.size() == 3
assert myMap['a'] == 1

def emptyMap = [:]
assert emptyMap.size() == 0

def explicitMap = new TreeMap()
explicitMap.putAll(myMap)
assert explicitMap['a'] == 1

//一般情况下key的类型都是字符串,在声明map的时候可以忽略字符串标记(单引号或者双引号)
//通过把符号放在圆括号中,强制让groovy将符号看做为一个表达式
def x = 'a'
assert ['x':1] == [x:1]
assert ['a':1] == [(x):1]

//map操作符使用,从map中获取对象的一种可选方式是使用下标操作符,也许读者已经猜测到了,
//这基于map实现了getAt方法;另外一种可选方式是使用点语法像使用属性那样来获取对象,
//在第七章将学习到属性的相关知识;第三种选择是使用get方法,这个方法允许传递一个缺省值,
//在map没有相应的key的时候允许返回这个值。如果没有指定缺省值,null将为缺省,如果
//get(key,default)被调用时,key没有找到并且缺省值被返回,那么key:default对将被增加到map中。

myMap = [a: 1,b: 2,c: 3]

assert myMap['a']           == 1
assert myMap.a              == 1
assert myMap.get('a')       == 1
assert myMap.get('a',0)     == 1
assert myMap.get('a')       == 1

assert myMap['d']           == null
assert myMap.d              == null
assert myMap.get('d')       == null

myMap['d']  = 1
assert myMap.d == 1

myMap = ['a.b':1]
assert myMap.'a.b' == 1

myMap = [a: 1,b: 2,c: 3]
def other = [b: 2,c: 3,a: 1]

assert myMap == other
assert myMap.isEmpty()              == false
assert myMap.size()                 == 3
assert myMap.containsKey('a')
assert myMap.containsValue(1)
assert myMap.keySet()               == toSet(['a','b','c'])
assert toSet(myMap.values())        == toSet([1,2,3])
assert myMap.entrySet() instanceof Collection

assert myMap.any {entry -> entry.value > 2}
assert myMap.every{entry -> entry.key < 'd'}

def toSet(list){
    new java.util.HashSet(list)
}


//map遍历
myMap = [a: 1,b: 2,c: 3]

def store = ''
myMap.each {entry ->
    store += entry.key
    store += entry.value
}
assert store.contains('a1')
assert store.contains('b2')
assert store.contains('c3')

store = ''
myMap.each {key,value ->
    store += key
    store += value
}
assert store.contains('a1')
assert store.contains('b2')
assert store.contains('c3')

store = ''
for (key in myMap.keySet()){
    store += key
}
assert store.contains('a')
assert store.contains('b')
assert store.contains('c')

store = ''
for (value in myMap.values()){
    store += value
}
assert store.contains('1')
assert store.contains('2')
assert store.contains('3')


//findAll用来查找满足闭包要求的所有map实体;
//find用来查找任意一个满足闭包要求的map实体,这里不像list那样查找的是第一个满足闭包要求的实体,这是因为map是没有顺序的;
//collect为map的每一个实体应用闭包,返回每一个闭包应用的结果组成的list(闭包是否返回结果是可选的)
myMap.clear()
assert myMap.isEmpty()

myMap = [a: 1,b: 2,c: 3]
myMap.remove('a')
assert myMap.size() == 2


//在原来的map上创建一个视图
myMap = [a: 1,b: 2,c: 3]
def abMap = myMap.subMap(['a','b'])
assert abMap.size() == 2

abMap = myMap.findAll{entry ->entry.value < 3}
assert abMap.size() == 2
assert abMap.a == 1

def found =myMap.find {entry->entry.value < 2}
assert found.key == 'a'
assert found.value == 1

def doubled = myMap.collect{entry-> entry.value *= 2}
assert doubled instanceof List
assert doubled.every{item-> item % 2 == 0}

def addTo = []
myMap.collect(addTo){entry -> entry.value *= 2}
assert doubled instanceof List
assert addTo.every{item -> item % 2 == 0}


//统计单词频率
def textCorpus =
"""
Look for the bare necessities
The simple bare necessities
Forget about your worries and your strife
I mean the bare necessities
Old Mother Nature's recipes
That bring the bare necessities of life
"""

def words = textCorpus.tokenize()
def wordFrequency = [:]
words.each {word->
    wordFrequency[word] = wordFrequency.get(word,0)+1
}
def wordList = wordFrequency.keySet().toList()
wordList.sort{wordFrequency[it]}

def statistic = "\n"
wordList[-1..-6].each {word->
    statistic += word.padLeft(12)+': '
    statistic += wordFrequency[word]+"\n"
}
println statistic


println 'ok'

























