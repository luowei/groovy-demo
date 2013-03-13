#!groovy


def listOne = [1,2,3,4,5,7,8,9,10]
def listTwo = [11,12,13,14,15,16,17,18,19,20]

// Java code!
ArrayList results = new ArrayList();
for (int i=0; i < listOne.size(); i++){
    Integer first = (Integer)listOne.get(i);
    Integer second = (Integer)listTwo.get(i);
    int sum = first.intValue()+second.intValue();
    results.add (new Integer(sum));
}

println results


//groovy code
def result2 = []
for (int i=0; i < listOne.size(); i++){
    Integer first = (Integer)listOne.get(i);
    Integer second = (Integer)listTwo.get(i);
    result2.add (first.plus(second))
//    result2.add (first + second)
}
println result2