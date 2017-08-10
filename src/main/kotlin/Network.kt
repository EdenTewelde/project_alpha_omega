import java.util.*

class Network(val capacity: Int, val listOfCars: MutableList<Car>) {

    val listOfCarsDriving: MutableList<Car> = mutableListOf()

    fun overCapacity(): Boolean {
        // tallies up the number of cars on the road
        val numberOfCarsDriving = listOfCarsDriving.size
        // compares it to its own capacity
        if (this.capacity >= numberOfCarsDriving) {
            return false
        } else {
            return true
        }
    }

    fun analyzeNetwork() {
        for (car in this.listOfCars) {
            if (car.wantsToDrive == true) {
                this.listOfCarsDriving.add(car)
            }
        }
        val overCapacity = this.overCapacity()
        for (car in listOfCars) {
            car.isDelayed = overCapacity
        }
    }

    fun randomize() : Boolean {
        var isDelayed = false
        val randomNumber = Random()
        val random = randomNumber.nextInt(101)
        val utilization = (this.listOfCarsDriving.size/this.capacity)
        if (utilization < 50) {
            when (random) {
                in 0..5 -> isDelayed = true
                in 6..100 -> isDelayed = false
            }
        } else if (utilization < 70) {
            when (random) {
                in 0..10 -> isDelayed = true
                in 11..100 -> isDelayed = false
            }
        } else if (utilization < 90) {
            when (random) {
                in 0..20 -> isDelayed = true
                in 21..100 -> isDelayed = false
            }
        } else if (utilization < 110) {
            when (random) {
                in 0..30 -> isDelayed = true
                in 31..100 -> isDelayed = false
            }
        } else if (utilization < 125) {
            when (random) {
                in 0..50 -> isDelayed = true
                in 51..100 -> isDelayed = false
            }
        } else if (utilization < 150) {
            when (random) {
                in 0..70 -> isDelayed = true
                in 71..100 -> isDelayed = false
            }
        } else if (utilization > 150) {
            when (random) {
                in 0..90 -> isDelayed = true
                in 91..100 -> isDelayed = false
            }
        }
        return isDelayed
    }
}
