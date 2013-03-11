#!groovy

//一个闭包是一个用花括号围起来的语句块，像别的任何代码块，为了传递参
//数给闭包，闭包有一组可选的参数列表，通过“->”表示列表的结束


//使用闭包计算所有在会议室的客人之间的碰杯数

def totalClinks = 0
def partyPeople = 100

//使用类Integer的upto方法，这个方法用来从当前整数值到结束的整数值之间，为每个整数做一些工作
1.upto(partyPeople) { guestNumber ->
    clinksWithGuest = guestNumber-1
    totalClinks += clinksWithGuest
}

assert totalClinks == (partyPeople*(partyPeople-1))/2
assert totalClinks == 4950
println 'ok'


//Java
int totalClinks2 = 0;
for(int guestNumber = 1; guestNumber <= partyPeople; guestNumber++) {
    int clinksWithGuest = guestNumber-1;
    totalClinks2 += clinksWithGuest;
}

assert totalClinks2 == 4950

println 'java ok'

