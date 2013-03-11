#!/cygdrive/d/my_tool/groovy-2.0.5/bin/groovy

import com.demo.test01.Book

Book gina = new Book('Groovy in Action')
assert gina.getTitle () == 'Groovy in Action'
assert getTitleBackwards (gina) == 'noitcA ni yvoorG'
println "hello world !"
String getTitleBackwards (book) {
    title = book.getTitle()
    return title.reverse()
}

println gina.title