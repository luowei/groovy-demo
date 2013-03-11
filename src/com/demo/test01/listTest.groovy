#!groovy

//罗马数字列表
def roman = ['', 'I', 'II', 'III', 'IV', 'V', 'VI', 'VII']
//访问列表
assert roman[4] == 'IV'
assert roman.size == 8

//扩张列表
roman[8] = 'VIII'
assert roman.size() == 9

println 'ok'