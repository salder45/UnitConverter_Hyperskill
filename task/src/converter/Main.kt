package converter

fun main() {
    println("Enter a number and a measure: ")
    val (inputMeasure, inputUnit) = readln().lowercase().split(" ")
    if(inputUnit == "km" || inputUnit == "kilometer" || inputUnit == "kilometers") {
        val pluralEnding: String = if (inputMeasure.toInt() == 1) "" else "s"
        println("$inputMeasure kilometer$pluralEnding is ${inputMeasure.toInt() * 1000} meters")
    } else {
        println("Wrong input")
    }
    /*
    println("145 centimeters is 1.45 meters")
    println("2 miles is 3.2187 kilometers")
    println("5.5 inches is 139.7 millimeters")
    println("12 degrees Celsius is 53.6 degrees Fahrenheit")
    println("3 pounds is 1.360776 kilograms")
     */
}
