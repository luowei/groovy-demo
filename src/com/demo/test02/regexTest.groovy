#!groovy

// regex查找操作符“=~”;
// regex匹配操作符“==~”;
// regex模式操作符:~String;

//在java中,这通常会引起混淆,为了传递一个反斜杠(\)给java字符串,模式必须使用许多反斜杠,
//必须双倍的输入反斜杠,这导致模式在java中十分难以阅读,更坏的结果是在模式中需要获取一个
//真实的反斜杠的时候,模式语言也转义一个反斜杠,因此,匹配a\b的模式用java字符串表示为“a\\\\b”。

//Groovy的处理方式好得多,就像你先前看到的那样,通过斜杠(/)构成的字符串,这不需要转义反斜杠(\)字符,
//并且仍然像一个一般的Gstring一样工作

assert "abc" == /abc/
assert "\\d" == /\d/

def reference = "hello"
assert reference == /$reference/

assert "\$" == /$/

//模式应用
twister = 'she sells sea shells at the sea shore of seychelles'

//在twister中匹配以s开头a结尾的,长度为3的子串
assert twister =~ /s.a/

finder = (twister =~ /s.a/)
assert finder instanceof java.util.regex.Matcher

//验证twister中只包含以空格间隔的单词
assert twister ==~ /(\w+ \w+)*/

WORD = /\w+/
matches = (twister ==~ /($WORD $WORD)*/)
assert matches instanceof java.lang.Boolean

assert (twister ==~ /s.e/) == false

wordsByX = twister.replaceAll(WORD, 'x')
assert wordsByX == 'x x x x x x x x x x'

words = twister.split(/ /)
assert words.size() == 10
assert words[0] == 'she'

//String有一个名称为eachMatch的方法,这个方法使用一个规则和
//一个闭包作为参数,闭包用来定义每一个符合规则的结果需要做的工作。
//匹配结果将传递给闭包
myFairStringy = 'The rain in Spain stays mainly in the plain!'

//words that end with 'ain':\b\w*ain\b
BOUNDS = /\b/
rhyme = /$BOUNDS\w*ain$BOUNDS/

//使用String.eachMatch(Pattern)
found = ''
myFairStringy.eachMatch(rhyme) { match ->
    found += match + ' '
}
assert found == 'rain Spain plain '

//使用Matcher.each(),Matcher是应用规则到字符串和模式所得到的结果
found = ''
(myFairStringy =~ rhyme).each { match ->
    found += match + ' '
}
assert found == 'rain Spain plain '

//通过闭包替换规则匹配的每一个结果,变量it引用
//匹配的子字符串,使用下划线替换“ain”
cloze = myFairStringy.replaceAll(rhyme) { it - 'ain' + '___' }
assert cloze == 'The r___ in Sp___ stays mainly in the pl___!'

//GDK按数组访问的方式增强了Matcher类,这是下面的例子
//匹配所有的非空白字符的结果
matcher = 'a b c' =~ /\S/
assert matcher[0] == 'a'
assert matcher[1..2] == ['b', 'c']
assert matcher[1] + matcher[2] == 'bc'
assert matcher.count == 3

//在匹配结果中的分组是有趣的,如果模式定义分组时包含圆括号,mattcher返回的不
//是为每一个匹配结果返回一个单字符串,而是返回一个数组,完整的匹配是索引为0并且接
//下来的是分组信息,考虑这个例子,每一个匹配的结果是通过冒号分割的字符串对,为了后
//面的处理,匹配结果将冒号左边的字符串和右边的字符串分隔在两个组:

matcher = 'a:1 b:2 c:3' =~ /(\S+):(\S+)/
assert matcher.hasGroup()
assert matcher[0] == ['a:1', 'a', '1']

//换句话说,matcher[0]的返回结果依赖与模式是否进行了分组。
//这也可以应用到matcher的each方法,每一个组都十分方便,在处理的闭包定义多个
//参数,参数按照分组列表进行定义:

('xy' =~ /(.)(.)/).each { all, x, y ->
    assert all == 'xy'
    assert x == 'x'
    assert y == 'y'
}

//仅仅匹配了一次,但是包括了两个组,每一个组一个字符。




//模式性能,模式的性能和模式操作符 ~String
//模式操作符把模式创建的时间从模式匹配的时间中分离出来,通过重用有限状态机提升了性能,
//以下显示了一个可怜人在这两种方式的性能比较,预编译模式至少快20%
twister = 'she sells sea shells at the sea shore of seychelles'

//匹配开始字母与结束字母相同的单词
regex = /\b(\w)\w*\1\b/

//隐式的构造模式查找
start = System.currentTimeMillis()
100000.times {
    twister =~ regex
}
first = System.currentTimeMillis() - start

//直接应用模式到字符串
start = System.currentTimeMillis()
pattern = ~regex
100000.times {
    pattern.matcher(twister)
}
second = System.currentTimeMillis() - start

assert first > second * 2



//模式分类,整的显示了模式的用法,Pattern对象是通过模式操作符返回的对象,这
//个对象实现了一个isCase(String)方法,该方法用来比较一个字符串是否完全匹配一
//个模式,这个方法是方便使用grep方法和switch语句的前提,将4个字符的单词进行分类,
//模式因此是由四个点构成的,这可不是省略号!!
assert (~/..../).isCase('bear')

switch ('bear'){
    case ~/..../ :
        assert true
        break
    default:
        assert false
}

beasts = ['bear','wolf','tiger','regex']
assert beasts.grep(~/..../) == ['bear','wolf']



println 'ok'
