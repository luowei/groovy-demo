#!groovy


/*
loop over the arrays (a,b,c), (A,B,C) and (1,2,3) to produce the output :

aA1
bB2
cC3

 */

def synchedConcat = { a1, a2, a3 ->
    assert a1 && a2 && a3
    assert a1.size() == a2.size()
    assert a2.size() == a3.size()
    [a1, a2, a3].transpose().collect { "${it[0]}${it[1]}${it[2]}" }
}

def x = ['a', 'b', 'c']
def y = ['A', 'B', 'C']
def z = [1, 2, 3]

synchedConcat(x, y, z).each { println it }
