#!groovy
package com.demo.test02


//范围用两个点“..”表示范围操作符，用来指定从左边到右边的边界，这个操作符具有较低的优先级，
//因此你常常需要使用圆括号把它们括起来，范围也可以通过相应的构造方法进行声明。 “..<”范围
//操作符号指定了一个半排除范围——也就是说，右边界值不是range的一部分：

//闭区间
assert (0..10).contains(0)
assert (0..10).contains(5)
assert (0..10).contains(10)

assert (0..10).contains(-1) == false
assert (0..10).contains(11) == false

//半闭区间
assert (0..<10).contains(9)
assert (0..<10).contains(10) == false

//区间引用
def a = 0..10
assert a.contains(5)

//隐式构造区间
a = new IntRange(0,10)
assert a.contains(5)

assert (0.0..2.0).contains(1.0)

//日期区间
def today = new Date()
def yesterday = today -1
assert (yesterday..today).size() == 2

assert ('a'..'c').contains('b')

def log=''
for(element in 5..9){
    log+= element
}
assert log == '56789'

log = ''
for(element in 9..5){
    log += element
}
assert  log == '98765'

log = ''
(9..<5).each {element ->
    log += element
}
assert log == '9876'


//range是对象
findResult = ''
(5..9).each {element ->
    findResult += element
}
assert findResult == '56789'

age = 36
switch (age){
    case 16..20:insuranceRate = 0.05;break
    case 21..50:insuranceRate = 0.06;break
    case 51..65:insuranceRate = 0.07;break
    default:throw new IllegalAccessException()
}
assert insuranceRate == 0.06

//是range对象使用grep方法的好例子：midage范围作为一个参数传递给grep方法
ages = [20,36,42,56]
midage = 21..50
assert ages.grep(midage) == [36,42]


//range可以使用任何类型，只要这个类型满足以下两个条件：
// 该类型实现next和previous方法，也就是说，重写++和--操作符；
// 该类型实现java.lang.Comparable接口；也就是说实现compareTo方法，实际上是重写<=>操作符。
//range示例，用类Weekday表示表示一周中的一天，一个Weekday有一个值，这个值在‘sun’到‘sat’之间，
//本质上，它是0到6之间的一个索引，通过list的下标来对应每个weekday的名称。
class Weekday implements Comparable{
    static final DAYS = [
            'Sun','Mon','Tue','Wed','Thu','Fri','Sat'
    ]
    private int index = 0
    Weekday(String day){
        index = DAYS.indexOf(day)
    }
    Weekday next(){
        return new Weekday(DAYS[(index+1) % DAYS.size()])
    }
    Weekday previous(){
        return new Weekday((DAYS[index-1]))
    }
    int compareTo(Object other){
        return this.index <=> other.index
    }
    String toString(){
        DAYS[index]
    }
}

def mon = new Weekday('Mon')
def fri = new Weekday('Fri')

def worklog = ''
for(day in mon..fri){
    worklog += day.toString()+' '
}
assert worklog == 'Mon Tue Wed Thu Fri '

println 'ok'





















































