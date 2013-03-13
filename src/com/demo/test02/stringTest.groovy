#!groovy

//字符串处理

// 单引号所表示的字符串不会按照GString的类型来处理内容，这等价于java的字符串；
println 'hello world'

// 双引号表示的意思与单引号表示的意思是等价的。如果字符串内容中包括没有被转义的$符号，
//那么它被加工成GString实例，GString更详细的信息将在下节介绍；
def name = 'world'
println "hello $name"

// 三组引号（或者是多行字符串）允许字符串的内容在多行出现，新的行总是被转换为“\n”，
//其他所有的空白字符都被完整的按照文本原样保留，多行字符串也许是一个GString实例，这根
//据是使用单引号或者多双引号而定，多行字符串事实上像Ruby或者Perl中的HERE-document。
println '''
    aaaaaaaaaaaaaaa
    bbbbbbbbbbbbbbb
    ccccccccccccccc
'''

println """
    hello $name
    nice to meet you,groovy!
    how do you do
"""

// “/”表示的字符串，指明字符串内容不转义反斜杠“\”，这在正则表达式的使用中特别有用，
//就象后面看到的那样，只有在一个反斜杠接下来是一个字符u的时候才需要进行转义——这稍微有
//点麻烦，因为“\u”用来表示一个unicode转义。

//在双引号字符串中，单引号不需要进行转义，反过来也是一样
println 'I said,"hi."'
println "you don't say that"

//java.lang.String对象‟x‟将被强制转换为一个java.lang.Character
char a = 'x'
Character b = 'x'
Character c = "x"

//如果在别的时候想将一个字符串转换为一个字符，可以采用下面的两种方式
'x' as char
'x' as Character





