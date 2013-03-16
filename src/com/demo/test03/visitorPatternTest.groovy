#!groovy

//观察者模式的实现
class Drawing {
    List shapes

    def accept(Closure yield) { shapes.each { it.accept(yield) } }
}
class Shape {
    def accept(Closure yield) { yield(this) }
}
class Square extends Shape {
    def width

    def area() { width ** 2 }
}
class Circle extends Shape {
    def radius

    def area() { Math.PI * radius ** 2 }
}

//添加观察对象
def pictrue = new Drawing(shapes:
        [new Square(width: 1), new Circle(radius: 1)])

//输出观察的信息
def total = 0
pictrue.accept { total += it.area() }
println "The shapes in this drawing cover an area of $total units"
println 'The individual contributions are: '
pictrue.accept { println it.class.name + ":" + it.area() }






































println 'ok'