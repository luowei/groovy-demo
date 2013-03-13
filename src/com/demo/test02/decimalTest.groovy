#!groovy


//对于+,-,*运算来说:
//如果有一个数为Float或者Double,那么结果是Double(在java中,只有
//操作数都是Float的时候,结果也是Float)。
// 否则,如果一个操作数为BigDecimal,结果为BigDecimal。
// 否则,如果一个操作数为BigInteger,结果为BigInteger。
// 否则,如果一个操作数为Long,那么结果为Long。
// 否则,结果为一个Integer。


//GDK也定义了times、upto、downto和step方法,这些方法接受一个闭
//包参数,列表3.9显示了这些方法的应用,times方法仅仅用于做重复的动作,upto方法
//是递增一个数字,downto是递减一个数字,step是按一个步进从一个数递增到另外一个数一般形式

def store = ''
10.times {
    store += 'x'
}
assert store == 'xxxxxxxxxx'

store = ''
1.upto(5){number ->
    store += number
}
assert store == '12345'


store = ''
2.downto(-2){number ->
    store += number + ' '
}
assert store == '2 1 0 -1 -2 '

store = ''
0.step(0.5,0.1){number ->
    store += number + ' '
}
assert store == '0 0.1 0.2 0.3 0.4'

println 'ok'

