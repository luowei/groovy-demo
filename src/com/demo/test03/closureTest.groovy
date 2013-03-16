#!groovy

//闭包,果闭包仅仅需要一个参数,groovy 提供了一个缺省的名称——it

//声明闭包
//在一个方法调用的后面,放置闭包代码在一对花括号里,闭包的参数和代码通过箭头(->)进行分隔

//简单的声明方式
//当只有一个参数传递给闭包的时候,这个参数的声明是可选的,魔术变量 it 代替了声明
log = ''
(1..10).each { counter -> log += counter }
assert log == '12345678910'

//注意不像 counter,魔术变量 it 不需要进行声明。
log = ''
(1..10).each { log += it }
assert log == '12345678910'

//这种语法是一个缩写形式,因为闭包对象通过花括号声明,该对象作为方法的最后一个
//参数并且通常出现在圆括号内,这个闭包对象就像其他放在圆括号内的参数一样有效,
log = ''
(1..10).each({ log += it })
assert log == '12345678910'

//用赋值的方式声明闭包
//闭包声明在花括号中并且赋给了 printer 变量
def printer = { line -> println line }

//通过方法的返回值也是一种声明闭包的方式:
def Closure getPrinter() {
    return { line -> println line }
}

//引用一个方法作为闭包
//用一个方法作为闭包是使用reference.&操作符,reference是闭包调用时使用的对象实例
class MethodClosureSample {
    int limit

    MethodClosureSample(int limit) {
        this.limit = limit
    }

    boolean validate(String value) {
        return value.length() <= limit
    }
}

MethodClosureSample first = new MethodClosureSample(6)
MethodClosureSample second = new MethodClosureSample(5)

//声明一个方法闭包
Closure firstClosure = first.&validate

def words = ['long string', 'medium', 'short', 'tiny']

//调用那个方法闭包
assert 'medium' == words.find(firstClosure)
//直接传递方法闭包
assert 'short' == words.find(second.&validate)

//运行时重载
class MultiMethodSample {
    int mysteryMethod(String value) {
        return value.length()
    }

    int mysteryMethod(List list) {
        return list.size()
    }

    int mysteryMethod(int x, int y) {
        return x + y
    }
}

MultiMethodSample instance = new MultiMethodSample()
Closure multi = instance.&mysteryMethod

assert 10 == multi('String arg')
assert 3 == multi(['list', 'of', 'values'])
assert 14 == multi(6, 8)

//使用简单声明、赋值给变量和方法闭包示例
map = ['a': 1, 'b': 2]
map.each { key, value -> map[key] = value * 2 }
assert map == ['a': 2, 'b': 4]

doubler = { key, value -> map[key] = value * 2 }
map.each(doubler)
assert map == ['a': 4, 'b': 8]

def doubleMethod(entry) {
    map[entry.key] = entry.value * 2
}

doubler = this.&doubleMethod
map.each(doubler)
assert map == ['a': 8, 'b': 16]

//调用闭包
//假设我们有一个引用 x 指向一个闭包,我们能通过 x.call()来调用闭包,或者简单的 x()
def adder = { x, y -> return x + y }

//直接调用闭包
assert adder(4, 3) == 7
//使用call方法调用闭包
assert adder.call(2, 6) == 8

//从一个方法内部调用闭包
def benchmark(repeat, Closure worker) {
    start = System.currentTimeMillis()
    repeat.times { worker(it) }
    stop = System.currentTimeMillis()
    return stop - start
}

slow = benchmark(10000) { (int) it / 2 }
fast = benchmark(10000) { it.intdiv(2) }
assert fast * 2 < slow


//第二个参数有一个缺省值,这样就可以进行两种调用——一是传递两个参数,
//另外一种是传递一个参数(第二个参数使用缺省值)
adder = { x, y=5 -> return x+y }
assert adder(4, 3) == 7
assert adder.call(7) == 12

//依赖闭包是接受一个参数还是两个参数,通过闭包的 getParameterTypes 方法你
//能了解到你期望的参数数量信息
def caller (Closure closure){
    closure.getParameterTypes().size()
}
assert caller { one -> } == 1
assert caller { one, two -> } == 2


//Closure 的 curry 方法返回当前闭包的一个克隆品,这个克隆品已经绑定
//了一个或者多个给定的参数,参数的绑定是从左向右进行的;下面的示例重用了
//同一个闭包,在调用 curry 方法的时候创建了一个新的闭包,这个闭包实际上像
//一个简单的加法器,但是第一个参数已经被固定为 1 ;
adder = {x,y->return x+y}
def addOne = adder.curry(1)
assert addOne(5) == 6


//实现一个日志记录器,支持行数的过滤,日志的格式化,并它
//们到一个设备上(控制台)
def configurator = {format,filter,line->
    filter(line)?format(line):null
}
def appender = {config,append,line->
    def out = config(line)
    if (out) append(out)
}
def dateFormatter = {line->"${new Date()}:$line"}
def debugFilter = {line->line.contains('debug')}
def consoleAppender = {line->println line}

def myConf = configurator.curry(dateFormatter,debugFilter)
def myLog = appender.curry(myConf,consoleAppender)

myLog('here is some debug message')
myLog('this will not be printed')


//通过 isCase 方法进行分类
//由于闭包实现了 isCase 方法,这样闭包可以在 grep 和 switch 中作为分类器使用;
//这样可以让我们使用任何逻辑进行分类;
assert [1,2,3].grep{ it<3 } == [1,2]
switch(10){
    case {it%2 == 1} : assert false
}



//闭包的范围
//闭包在它的生命周期里以某种方式记住了它的工作上下文环境引用,
//当调用它的时候,闭包可以在它的原始上下文中工作
def x = 0
10.times {
    x++
}
assert x == 10

//通过一个名称为 owner 的变量可以获取到声明闭包的对象
class Mother{
    int field =1
    int foo(){
        return 2
    }
    Closure birth(param){
        def local = 3
        def closure = {caller ->
            [this,field,foo(),local,param,caller]
        }
        return closure
    }
}

Mother julia = new Mother()
closure = julia.birth(4)
context = closure.call(this)
println context[0].class.name

assert context[0] instanceof Mother
assert context[1..4] == [1,2,3,4]
assert context[5] instanceof Script


firstClosure = julia.birth(4)
secondClosure = julia.birth(4)
assert false == firstClosure.is(secondClosure)


//聚积计算示例,定义一个返回接收一个参数的闭包的方法foo
def foo(n){
    return {n += it}
}

def accumulator = foo(1)
assert accumulator(0) == 1
assert accumulator(1) == 2
assert accumulator(2) == 4
assert accumulator(1) == 5


//从闭包返回结果,有2种返回结果的方式:
//1.闭包最后一个表达式执行的结果,并且这个结果被返回,这叫做结束返回(end return),
//  在最后的语句前面的return关键字是可选的。
//2.使用return关键字从闭包的任何地方返回。
assert [1, 2, 3].collect{ it * 2 } == [2,4,6]
assert [1, 2, 3].collect{ return it * 2 } == [2,4,6]

//注意:在闭包体外面,任何出现return的地方都会导致离开当前方法,当在闭包体内出现return
//语句时,这仅仅结束闭包的当前计算;


println 'ok'





