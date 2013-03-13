
//在groovy中，字符串能出现在单引号或者双引号中，
//在双引号的字符串中允许使用占位符，占位符在需要的时候将自动解析

def nick = 'Gina'
def book = 'Groovy in Action'
assert "$nick is $book" == 'Gina is Groovy in Action'