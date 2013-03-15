#!groovy
package com.demo.test02

//允许相同货币构成的Money可以相加，而不同货币构成的Money不能相加

class Money {
    private int amount
    private String currency

    Money(amountValue, currentValue) {
        amount = amountValue
        currency = amountValue
    }

    //重写 == 操作符
    boolean equals(Object other) {
        if (null == other) return false
        if (!(other instanceof Money)) return false
        if (currency !=Money.cast(other).currency) return false
        if (amount !=Money.cast(other).amount) return false
        return true
    }

    int hashCode(){
        amount.hashCode()+currency.hashCode()
    }

    Money plus(Money other){
        if (null == other) return null
        if (other.currency !=currency){
            throw new IllegalAccessException("cannot add $other.currency to $currency")
        }
        return new Money(amount+other.amount,currency)
    }

    Money plus(Integer more){
        new Money(amount+more,currency)
    }

}

def buck=new Money(1,"USD")
assert buck
assert buck == new Money(1,"USD")
assert buck+buck == new Money(2,"USD")
assert buck+1 == new Money(2,"USD")

println 'ok'