package converter

fun main() {
    println("Enter a number and a measure of length: ")
    val (inputMeasure, inputUnit) = readln().lowercase().split(" ")
    val normalizedUnit: String = normalizeInput(inputUnit)
    if (normalizedUnit != "") {
        val conversionRate: Double = getConversionRate(normalizedUnit)
        val convertedValue: Double = inputMeasure.toDouble() * conversionRate
        println("${inputMeasure.toDouble()} ${getUnitName(normalizedUnit,inputMeasure.toDouble().toInt())} is $convertedValue ${getUnitName("m", convertedValue.toInt())}")

    } else {
        println("Wrong input. Unknown unit $inputUnit")
    }
    /*
    if(inputUnit == "km" || inputUnit == "kilometer" || inputUnit == "kilometers") {
        val pluralEnding: String = if (inputMeasure.toInt() == 1) "" else "s"
        println("$inputMeasure kilometer$pluralEnding is ${inputMeasure.toInt() * 1000} meters")
    } else {
        println("Wrong input")
    }*/
    /*
    println("145 centimeters is 1.45 meters")
    println("2 miles is 3.2187 kilometers")
    println("5.5 inches is 139.7 millimeters")
    println("12 degrees Celsius is 53.6 degrees Fahrenheit")
    println("3 pounds is 1.360776 kilograms")
     */
}

fun getConversionRate(normalizedUnit: String): Double {
    return when (normalizedUnit) {
        "km" -> 1000.0 //kilometer
        "cm" -> 0.01 //centimeter
        "mm" -> 0.001 //millimeter
        "mi" -> 1609.35 //mile
        "yd" -> 0.9144 //yard
        "ft" -> 0.3048 //foot
        "in" -> 0.0254 //inch
        else -> 1.0 //meter
    }
}

fun getUnitName(normalizedUnit: String, unitValue: Int): String {
    return when (normalizedUnit) {
        "km" -> if (unitValue != 1) {
            "kilometers"
        } else {
            "kilometer"
        }
        "cm" -> if (unitValue != 1) {
            "centimeters"
        } else {
            "centimeter"
        }
        "mm" -> if (unitValue != 1) {
            "millimeters"
        } else {
            "millimeter"
        }
        "mi" -> if (unitValue != 1) {
            "miles"
        } else {
            "mile"
        }
        "yd" -> if (unitValue != 1) {
            "yards"
        } else {
            "yard"
        }
        "ft" -> if (unitValue != 1) {
            "feet"
        } else {
            "foot"
        }
        "in" -> if (unitValue != 1) {
            "inches"
        } else {
            "inch"
        }
        else -> {
            if (unitValue != 1) {
                "meters"
            } else {
                "meter"
            }
        }
    }
}

fun normalizeInput(inputUnit: String): String {
    return when {
        inputUnit == "m" || inputUnit == "meter" || inputUnit == "meters" -> "m"
        inputUnit == "km" || inputUnit == "kilometer" || inputUnit == "kilometers" -> "km"
        inputUnit == "cm" || inputUnit == "centimeter" || inputUnit == "centimeters" -> "cm"
        inputUnit == "mm" || inputUnit == "millimeter" || inputUnit == "millimeters" -> "mm"
        inputUnit == "mi" || inputUnit == "mile" || inputUnit == "miles" -> "mi"
        inputUnit == "yd" || inputUnit == "yard" || inputUnit == "yards" -> "yd"
        inputUnit == "ft" || inputUnit == "foot" || inputUnit == "feet" -> "ft"
        inputUnit == "in" || inputUnit == "inch" || inputUnit == "inches" -> "in"
        else -> ""
    }
}
