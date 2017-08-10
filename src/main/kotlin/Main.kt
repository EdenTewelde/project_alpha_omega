import com.univocity.parsers.common.record.Record
import com.univocity.parsers.csv.CsvParser
import com.univocity.parsers.csv.CsvParserSettings
import com.univocity.parsers.csv.CsvWriter
import com.univocity.parsers.csv.CsvWriterSettings
import java.util.*

fun main(args: Array<String>) {
    val roadNetwork = readNetworkFromCsv("/cars.csv")
    roadNetwork.analyzeNetwork()
    writeNetworkToCsv(roadNetwork, "ResultingData.csv")
}

private fun readNetworkFromCsv(fileName: String): Network {
    //Changed the return "Car" into "Network", because there is the "listOfCars"
    val capacity = 20
    val settings = CsvParserSettings()
    settings.format.setLineSeparator("\n")
    settings.isHeaderExtractionEnabled = true
    val csvParser = CsvParser(settings)
    val reader = FileAccess().getReader(fileName)
    val carRows: MutableList<Record> = csvParser.parseAllRecords(reader)

    val listOfCars: MutableList<Car> = mutableListOf()
    for (car in carRows) {
        val carID = car.values.get(0)
        val id = carID.toInt()

        val carStatus = car.values.get(1)
        val status = carStatus.toBoolean()

        val newCar = Car(id, status)
        listOfCars.add(newCar)
    }

    return Network(capacity, listOfCars)
}

private fun writeNetworkToCsv(network: Network, fileName: String) {
    val settings = CsvWriterSettings()
    settings.format.setLineSeparator("\n")

    val writer = FileAccess().getWriter(fileName)
    val csvWriter = CsvWriter(writer, settings)
    csvWriter.writeHeaders("Car-ID", "status", "delayed")

    val carRows: MutableList<Array<Any>> = mutableListOf()
    for (car in network.listOfCars) {
        val id = car.id
        val status = car.wantsToDrive
        val delayed = car.isDelayed
        val row: Array<Any> = arrayOf(id, status, delayed)
        carRows.add(row)
    }

    csvWriter.writeRowsAndClose(carRows)
}

fun scenario(numberOfCars: Int, capacity: Int) {
    /*
    * Creates a given a number of cars, every other car wants to drive
    * A road network is created with the given capacity
    * The road network is analyzed and it is printed whether a car is delayed or not
    */
    val listOfCars: MutableList<Car> = mutableListOf()
    val road = Network(capacity, listOfCars)
    val hours = 24
    var wantsToDrive = Random().nextBoolean()
    for (hour in 1..hours) {
        for (i in 1..numberOfCars) {
            val newCar = Car(i, wantsToDrive)
            listOfCars.add(newCar)
        }
        road.analyzeNetwork()
        for (car in road.listOfCars) {
            if (car.wantsToDrive && car.isDelayed == true) {
                wantsToDrive = true
            } else wantsToDrive = Random().nextBoolean()
        }
    }

    for (car in road.listOfCars) {
        println("Does car #" + car.id + " want to drive? " + car.wantsToDrive)
        if (car.wantsToDrive == true) {
            println("Is car #" + car.id + " delayed? " + car.isDelayed)
        }
    }
}
