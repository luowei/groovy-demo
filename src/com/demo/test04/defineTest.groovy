#!groovy


//变量的声明
/*
1.脚本允许使用没有声明的变量,在这种情况下变量被假定从脚本的 binding 属性获取,如果在 binding 中没有发现相
应的变量,那么把变量增加到 binding 中,binding 是一个数据存储器,它能把变量在脚本调用者和脚本之间进行传递;

2.在没有指定范围修饰符的属性声明的时候,groovy会根据需要生成相应的访问方法(getter 方法和 setter 方法);

3.定义变量的类型是可选的,不管怎样,标示符在声明的时候不必独一无二,当没有类型和修饰符时,必须使用 def 来作为
替换,实际上用 def 来表明属性或者变量是没有类型的(尽管在内部将被声明为 Object 类型);

*/

class SomeClass {
    public fieldWithModifier
    String typedField
    def untypedField
    protected field1,field2,field3
    private assignedField = new Date()

    static classField

    public static final String CONSTA = 'a',CONSTB = 'b'

    def someMethod(){
        def localUntypeMethodVar = 1
        int localTypeMethodVar = 1
        def localVarWithoutAssignment,andAnotherOne
    }
}

def localvar = 1
boundvar1 = 1

def someMethod(){
    localMethodvar = 1
    boundVar2 = 1
}


//引用属性和取消对属性的引用
//除了可以通过 obj.fieldName 来引用属性之外,也可以通过下标操作符来引用属性
class Counter{
    public count = 0
}
def counter = new Counter()

counter.count = 1
assert counter.count == 1

def fieldName = 'count'
counter[fieldName] = 2
assert counter['count'] == 2

//一般来说,重写 get 方法意味着重写了 dot-fieldName 操作符,
//重写 set 方法意味着重写了 field assignment 操作符。


//groovy 支持的位置参数和命名参数的方法定义和调用,可变长度的参数列表,
//可选的参数和其默认值,例子提供了四种组合,每一种都突出了不同的调用方法途径
class Summer{
    def sumWithDefaults(a,b,c = 0){
        return a+b+c
    }
    def sumWithList(List args){
        return args.inject(0){sum,i->sum += i}
    }
    def sumWithOptionals(a,b,Object[] optionals){
        return a+b+sumWithList(optionals.toList())
    }
    def sumNamed(Map args){
        ['a','b','c'].each {args.get(it,0)}
        return args.a + args.b + args.c
    }
}
def summer = new Summer()

assert 2 == summer.sumWithDefaults(1,1)
assert 3 == summer.sumWithDefaults(1,1,1)

assert 2 == summer.sumWithList([1,1])
assert 3 == summer.sumWithList([1,1,1])
assert 2 == summer.sumWithOptionals(1,1)
assert 3 == summer.sumWithOptionals(1,1,1)

assert 2 == summer.sumNamed(a: 1,b: 1)
assert 3 == summer.sumNamed(a: 1,b: 1,c: 1)
assert 1 == summer.sumNamed(c: 1)

//高级命名
//如果你把方法名称放在引号中,那么 groovy 允许你在方法名称中使用
//这样的字符: objectReferenct.’my.method-Name’();
//如果有一个字符串,通常是使用 Gstring,如果这样 obj."${var}"()
//是可以的,并且 Gstring 将被用来确定调用的方法的名称;


//安全的引用符号 (?.)
//当操作符之前是一个 null 引用的时候,当前表达式的评估被终止,并且 null 被返回
def map = [a: [b: [c: 1]]]

assert map.a.b.c == 1

if (map && map.a && map.a.x){
    assert map.a.x.c == null
}

try {
    assert map.a.x.c == null
} catch (NullPointerException npe){
}
assert map?.a?.x?.c == null


//构造器(构造方法)
//groovy能够通过三种途径调用构造方法:常用的java方式,使用as关键字进行强制造型和使用隐式造型
class VendorWithCtor{
    String name,product

    VendorWithCtor(name,product){
        this.name = name
        this.product = product
    }
}

def first = new VendorWithCtor('Canon','ULC')
//使用as关键字进行强制造型
def second = ['Canoo','ULC'] as VendorWithCtor


//命名参数构造
class Vendor{
    String name,product
}
new Vendor()
new Vendor(name: 'Canoo')
new Vendor(product: 'ULC')
new Vendor(name: 'Canoo',product: 'ULC')

def vendor = new Vendor(name: 'Canoo')
assert 'Canoo' == vendor.name


//隐式构造方法
java.awt.Dimension area
area = [200, 100]
assert area.width == 200
assert area.height == 100


//groovy bean
class MyBean implements Serializable{
    def untyped
    String typed
    def item1,item2
    def assigned = 'default value'
}
def bean = new MyBean()
assert 'default value' == bean.getAssigned()
bean.setUntyped(('some value'))
assert 'some value' == bean.getUntyped()


//访问方法,当属性和相应的访问方法对调用者都是可用的时候,属性引用被解析为对访问
//方法的调用,如果只存在一种可用,这是可选的使用.@进行属性访问,这样可以绕开访问
//方法的机制,在需要的时候你能够使用.@操作符直接访问属性;
class DoublerBean{
    public value
    void setValue(value){
        this.value = value
    }
    def getValue(){
        value * 2
    }
}
bean = new DoublerBean(value: 100)
assert 200 == bean.value
assert 100 == bean.@value

//规则:在类内部,引用 fieldName 或者 this.fieldName 将被解释为直接属性访问,而不是bean风格的
//属性访问(通过访问方法进行);在类的外部,可以使用 reference.@fieldName语法直接访问类属性

/*
Groovy 通过一种简单但是强大的方式来支持事件监听器,假设你需要创建一个 Swing
Jbutton,按钮的标签为“Push me!
”
,当按钮被点击的时候将标签的内容打印在控制台,一
个 Java 的实现时使用匿名内部类:
// Java
final JButton button = new JButton("Push me!");
button.addActionListener(new IActionListener(){
public void actionPerformed(ActionEvent event){
System.out.println(button.getText());
}
});
开发人员需要知道各自注册的监听器和事件类型(或者接口)及回调方法。

 一个 groovy 开发人员仅仅附上一个闭包给按钮,就像它是按钮的一个属性,属性名
称为相应的回调方法名称:
button = new JButton('Push me!')
button.actionPerformed = { event ->
println button.text
}
event 参数被增加时为了知道如何在需要的时候获取它,
在这个例子中,
它已经被忽略,
因为在闭包中没有使用它。
注意:
groovy 使用 bean 内省机制来决定是否属性赋值引用为 bean 支持的监听器的回调
方法,如果是这样,在通知的时候调用闭包,一个 ClosureListener 被透明的增加。一个
ClosureListener 是要求的接口的代理实现。

*/


//为任何对象使用 bean 方法
class BeanClass{
    def someProperty
    public someField
    private somePrivateField
}
def obj = new BeanClass()
def store = []
obj.properties.each {property->
    store += property.key
    store += property.value
}
assert store.contains('someProperty')
assert store.contains('someField')          == false
assert store.contains('somePrivateField')   == false
assert store.contains('class')
//assert store.contains('metaClass')

assert obj.properties.size() == 2


//将闭包赋值给一个属性,Expando能够作为bean 的扩展,通过赋值方式扩展属性
/*
def boxer = new Expando()
assert null == boxer.takeThis
boxer.takeThis = 'outh!'
assert 'outh!' == boxer.takeThis
boxer.fightBack = {times -> return this.takeThis * times}
assert 'outh!outh!outh!' == boxer.fightBack(3)

*/

//GPath示例
//定义类为 groovyBean,通过这个结构构建简单的发票
class Invoice{
    List items
    Date date
}
class LineItem{
    Product product
    int count
    int total(){
        return product.dollar * count
    }
}
class Product{
    String name
    def dollar
}
def ulcDate = new Date(107,0,1)
def ulc = new Product(dollar: 1499,name: "ULC")
def ve = new Product(dollar: 499,name: 'Visual Editor')

def invoices = {
    new Invoice(date: ulcDate,items: [
            new LineItem(count: 5,product: ulc),
            new LineItem(count: 1,product: ve)
    ])
}
assert ['ULC'] == invoices.items.grep{it.total() > 7000}.product.name

def searchDates = invoices.grep{
    it.items.any{it.product == ulc}
}.date*.tostring()
assert [ulcDate.toString()] == searchDates



























println 'ok'