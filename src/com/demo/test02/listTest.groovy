#!groovy


//list可以通过range的toList方法进行同时创建和初始化
myList = [1,2,3]

assert myList.size() == 3
assert myList[0] == 1
assert myList instanceof ArrayList
emptyList = []
assert emptyList.size() == 0

longList = (0..1000).toList()
assert longList[555] == 555

explicitList = new ArrayList()
explicitList.addAll(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

explicitList = new LinkedList(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

explicitList = new LinkedList(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10


//下标操作符
myList = ['a','b','c','d','e','f']

assert myList[0..2] == ['a','b','c']
assert myList[0,2,4] == ['a','c','e']

myList[0..2] = ['x','y','z']
assert myList == ['x','y','z','d','e','f']

//当指定的列表值小于给定的范围或者是空的时候，这个列表被收缩
myList[3..5] = []
assert myList == ['x','y','z']

//当指定的列表值更大时，列表进行增长
myList[1..1] = ['y','1','2']
assert myList == ['x','y','1','2','z']

//list也可以通过负数进行索引，通过负数索引是从列表的最后向前走的通过list[-1]来获取非空列表的最后一个元素，
//通过list[-2]来获取到倒数第二个元素，负数索引也可以用作表示范围，因此list[-3..-1]这样的表示结果将得到列表
//的最后三个元素。当使用反向范围的时候，得到的结果列表也是反向的，因此list[4..0]的结果是[4,3,2,1,0]。在这里，
//结果作为一个新的list对象返回，而不是像JDK的sublist那样返回原来的对象，甚至混合使用正数和负数作为索引都是
// 可以的，如list[1..-2]能够用来去掉原来列表的开始的和最后的元素.


//增加和删除列表中的条目
myList = []

//添加元素
myList += 'a'
assert myList == ['a']

//添加集合
myList += ['b','c']
assert myList == ['a','b','c']

//追加元素
myList = []
myList << 'a' << 'b'
assert myList == ['a','b']

//从集合删除子集
assert myList - ['b'] == ['a']
assert myList * 2 == ['a','b','a','b']
assert myList * 2 - ['a'] == ['b','b']

//从集合中删除元素
assert myList * 2 - 'a' == ['b','b']

//控制结构
myList = ['a','b','c']

//实现isCase方法，得到一个grep过滤器并且可以简单的使用switch进行分类
assert  myList.isCase('a')
//与上一行代码等效
switch ('a'){
    case myList : assert true;break
    default:assert false
}

assert ['x','a','z'].grep(['b','a','d'])==['a']
assert ['x','a','z'].grep(myList) == ['a']

myList = []
//内部的boolean值测试，空的list将评估为false
if (myList) assert false

//显示了在列表或者其他集合上的进行的循环，并且也说明了列表可以包含多个不同类型的元素。
log = ''
for (i in [1,'x',5]){
    log += i
}
assert log == '1x5'



//维护列表内容
//平整（flattening）嵌套的列表
assert [1,[2,3]].flatten() == [1,2,3]
//取交集
assert [1,2,3].intersect([4,3,1]) == [3,1]
//验证两个列表不相交
assert [1,2,3].disjoint([4,5,6])

//把列表当作栈来操作
def listTest = [1,2,3]
popped = listTest.pop()
assert popped == 3
assert listTest == [1,2]

assert [1,2].reverse() == [2,1]
assert [3,1,2].sort() == [1,2,3]

//根据列表的第一个元素来比较
def list = [[1,0],[0,1,2]]
list = list.sort {a,b -> a[0] <=> b[0]}
assert list == [[0,1,2],[1,0]]

//根据列表的长度来排序,接受单个参数,这样就在闭包内部完成排序,
//排序根据闭包的返回结果进行（返回的结果必须是可比较的（Comparable））
list = list.sort{item -> item.size()}
assert list == [[1,0],[0,1,2]]

//根据元素的索引删除元素
list = ['a','b','c']
list.remove(2)
assert list == ['a','b']
//根据列表中的元素的值删除元素
list.remove('b')
assert  list == ['a']

list = ['a','b','b','c']
list.removeAll('b','c')
assert list == ['a']

def doubled = [1,2,3].collect{item ->
    item*2
}
assert doubled  == [2,4,6]

def odd = [1,2,3].findAll{item ->
    item % 2 == 1
}
assert odd == [1,3]

//删除list中的重复值
def x = [1,1,1]
assert [1] == new HashSet(x).toList()
assert [1] == x.unique()

//删除list中的null值
x = [1,null,1]
assert [1,1] == x.findAll{it != null}
assert [1,1] == x.grep{it}


//访问列表内容
//ind方法用来查找list第一个符合闭包要求的元素，every和any方法用来确定list中的每一个元素（或者任何一个元素）
//是否符合闭包的要求;，使用each方法进行正向遍历，并且使用eachReverse方法进行反向遍历

//list查询,迭代,累积操作
list = [1,2,3]
assert list.count(2) == 1  //查询1在list中出现在了几次
assert list.max() == 3
assert list.min() == 1

def even = list.find {item ->
    item % 2 == 0
}
assert even == 2

assert list.every{item -> item < 5}
assert list.any{item -> item < 2}

def store = ''
list.each {item ->
    store += item
}
assert store == '123'

store = ''
list.reverseEach {item ->
    store += item
}
assert store == '321'


//join方法将所有元素通过给定的字符串进行连接，然后将结果作为字符串返回，inject方法方法使用闭包注入一个新的函数，
//这个函数用来对一个中间结果和遍历的当前元素进行操作，inject方法的第一个参数是中间结果的初始值
assert list.join('-') == '1-2-3'
//使用inject方法累加所有元素
result = list.inject(0){clinks,guests ->
    clinks += guests
}
assert result == 0+1+2+3
assert list == [1,2,3]
assert list.sum() == 6

//对所有的元素结果进行相乘
factorial = list.inject(1){fac,item ->
    fac *= item
}
assert factorial == 1*1*2*3


//对list的快速排序实现
def quickSort(list){
    if (list.size()<2) return list
    def pivot = list[list.size().intdiv(2)]
    def left = list.findAll{item -> item < pivot}
    def middle = list.findAll{item -> item == pivot}
    def right = list.findAll{item -> item > pivot}
    quickSort(left)+middle+quickSort(right)
}
assert quickSort([])                    == []
assert quickSort([1])                   == [1]
assert quickSort([1,2])                 == [1,2]
assert quickSort([2,1])                 == [1,2]
assert quickSort([3,2,1])               == [1,2,3]
assert quickSort([3,1,2,2])             == [1,2,2,3]
assert quickSort([1.0f,'a',10,null])   == [null,1.0f,10,'a']
assert quickSort('You And Me')          == '  AMYdenou'.toList()


println 'ok'







































