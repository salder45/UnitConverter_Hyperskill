package converter

enum class UnitTypes {
    LENGTH, WEIGHT, TEMPERATURE, NULL
}

enum class ConversionUnits(val type: UnitTypes,val singular: String, val plural: String, val conversionRate: Double, val inputs: List<String>){
    METER(UnitTypes.LENGTH, "meter", "meters", 1.0, listOf("m","meter","meters")),
    KILOMETER(UnitTypes.LENGTH, "kilometer","kilometers", 1000.0, listOf("km","kilometer","kilometers")),
    CENTIMETER(UnitTypes.LENGTH, "centimeter","centimeters", 0.01, listOf("cm","centimeter","centimeters")),
    MILLIMETER(UnitTypes.LENGTH,"millimeter","millimeters", 0.001, listOf("mm","millimeter","millimeters")),
    MILE(UnitTypes.LENGTH,"mile","miles", 1609.35, listOf("mi","mile","miles")),
    YARD(UnitTypes.LENGTH,"yard","yards", 0.9144, listOf("yd","yard","yards")),
    FOOT(UnitTypes.LENGTH,"foot","feet", 0.3048, listOf("ft","foot","feet")),
    INCH(UnitTypes.LENGTH,"inch","inches", 0.0254, listOf("in","inch","inches")),
    GRAM(UnitTypes.WEIGHT,"gram" , "grams", 1.0, listOf("g" ,"gram" , "grams")),
    KILOGRAM(UnitTypes.WEIGHT, "kilogram","kilograms", 1000.0, listOf("kg", "kilogram","kilograms")),
    MILLIGRAM(UnitTypes.WEIGHT, "milligram", "milligrams", 0.001, listOf("mg", "milligram", "milligrams")),
    POUND(UnitTypes.WEIGHT,"pound", "pounds", 453.592, listOf("lb", "pound", "pounds")),
    OUNCE(UnitTypes.WEIGHT, "ounce", "ounces", 28.3495, listOf("oz", "ounce", "ounces")),
    CELSIUS(UnitTypes.TEMPERATURE,"degree celsius" ,"degrees celsius" ,0.0 ,listOf("c","dc", "celsius","degree celsius", "degrees celsius")),
    FAHRENHEIT(UnitTypes.TEMPERATURE,"degree fahrenheit", "degrees fahrenheit", 0.0, listOf("f", "df", "fahrenheit", "degree fahrenheit", "degrees fahrenheit")),
    KELVINS(UnitTypes.TEMPERATURE, "kelvin", "kelvins",0.0, listOf("k", "k", "kelvin", "kelvin", "kelvins")),
    NULL(UnitTypes.NULL, "", "???",0.0, listOf(""))
}

enum class Length(val symbol: String, val singular: String, val plural: String, val conversionRate: Double){
    METER("m","meter","meters",1.0),
    KILOMETER("km","kilometer","kilometers", 1000.0),
    CENTIMETER("cm","centimeter","centimeters", 0.01),
    MILLIMETER("mm","millimeter","millimeters", 0.001),
    MILE("mi","mile","miles", 1609.35),
    YARD("yd","yard","yards", 0.9144),
    FOOT("ft","foot","feet", 0.3048),
    INCH("in","inch","inches", 0.0254),
    NULL("","", "", 0.0)
}
enum class Weight(val symbol: String, val singular: String, val plural: String, val conversionRate: Double){
    GRAM("g" ,"gram" , "grams", 1.0),
    KILOGRAM("kg", "kilogram","kilograms", 1000.0),
    MILLIGRAM("mg", "milligram", "milligrams", 0.001),
    POUND("lb", "pound", "pounds", 453.592),
    OUNCE("oz", "ounce", "ounces", 28.3495),
    NULL("","", "", 0.0)
}
enum class Temperature(val symbol: String, val short: String, val unitName: String, val singular: String, val plural: String){
    CELSIUS("c","dc", "celsius","degree celsius", "degrees celsius"),
    FAHRENHEIT("f", "df", "fahrenheit", "degree fahrenheit", "degrees fahrenheit"),
    KELVINS("k", "k", "kelvin", "kelvin", "kelvins"),
    NULL("","", "", "", "")
}

fun main() {
    var keepRunning: Boolean = false
    do {
        println("Enter what you want to convert (or exit): ")
        val userInput: String = readln()
        if (userInput == "exit") {
            keepRunning = true
        } else {
            val normalizedInput = normalizeInput(userInput)
            if (normalizedInput.count() != 4){
                println("Parse error")
                continue
            }
            var inputMeasure: Double = 0.0
            if (normalizedInput[0].toDoubleOrNull() == null) {
                println("Parse error")
                continue
            } else {
                inputMeasure = normalizedInput[0].toDouble()
            }
            val inputUnit: String = normalizedInput[1]
            val outputUnit: String = normalizedInput[3]
            val inputConversion = findUnit(inputUnit)
            val outputConversion = findUnit(outputUnit)
            if (inputConversion == ConversionUnits.NULL || outputConversion == ConversionUnits.NULL || inputConversion.type != outputConversion.type) {
                println("Conversion from ${inputConversion.plural} to ${outputConversion.plural} is impossible")
                continue
            }

            if(inputConversion.type != UnitTypes.TEMPERATURE && inputMeasure < 0) {
                val unitType: String = if (inputConversion.type == UnitTypes.LENGTH) "Length" else "Weight"
                println("$unitType shouldn't be negative")
                continue
            }
            val convertedValue: Double =
            if (inputConversion.type != UnitTypes.TEMPERATURE) {
                inputMeasure * inputConversion.conversionRate / outputConversion.conversionRate
            } else {
                convertTemperature(inputConversion, outputConversion, inputMeasure)
            }
            println("$inputMeasure ${getUnitName(inputConversion, inputMeasure)} is $convertedValue ${getUnitName(outputConversion, convertedValue)}")
        }
    } while (!keepRunning)
}

fun normalizeInput(input: String): List<String> {
    val inputList = input.lowercase().replace("degrees","").replace("degree","").split(" ")
    val normalizedInput: MutableList<String> = mutableListOf()
    for (index in inputList.indices) {
        if (inputList[index] != "") normalizedInput.add(inputList[index])
    }
    return normalizedInput
}

fun findUnit(unit: String): ConversionUnits {
    for (enum in ConversionUnits.values()) {
        for (input in enum.inputs) {
            if(unit == input) return enum
        }
    }
    return ConversionUnits.NULL
}

fun convertTemperature(inputUnit: ConversionUnits, outputUnit: ConversionUnits, inputValue: Double): Double {
    return when {
        inputUnit == ConversionUnits.CELSIUS && outputUnit == ConversionUnits.FAHRENHEIT -> (inputValue * (9.0 / 5.0) + 32.0)
        inputUnit == ConversionUnits.CELSIUS && outputUnit == ConversionUnits.KELVINS -> inputValue + 273.15
        inputUnit == ConversionUnits.FAHRENHEIT && outputUnit == ConversionUnits.CELSIUS -> (inputValue - 32.0) * (5.0 / 9.0)
        inputUnit == ConversionUnits.FAHRENHEIT && outputUnit == ConversionUnits.KELVINS -> (inputValue + 459.67) * (5.0 / 9.0)
        inputUnit == ConversionUnits.KELVINS && outputUnit == ConversionUnits.CELSIUS -> (inputValue - 273.15)
        inputUnit == ConversionUnits.KELVINS && outputUnit == ConversionUnits.FAHRENHEIT -> (inputValue * (9.0 / 5.0)) - 459.67
        else -> inputValue
    }
}

fun getUnitName(unit: ConversionUnits, valueUnit: Double): String {
    return if (valueUnit == 1.0) unit.singular else unit.plural
}
