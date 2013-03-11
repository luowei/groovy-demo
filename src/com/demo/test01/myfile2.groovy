#!groovy
//把groovy-2.x/bin添加到path下，就可以直接使用,[#!groovy]

class Book {
    String title
    //声明一个属性
}

def groovyBook = new Book()

//通过显示的方法调用来使用属性
groovyBook.setTitle('Groovy conquers the world')
assert groovyBook.getTitle() == 'Groovy conquers the world'

//通过groovy的快捷方式来使用属性
groovyBook.title = 'Groovy in Action'
assert groovyBook.title == 'Groovy in Action'