package com.example.kiosk

class DripCoffee(name:String, calories: String, price: String, purchasable:Boolean):StarbucksMenuItem(name, calories, price) {
    companion object {
        fun createIceCoffee(): DripCoffee {
            return DripCoffee("아이스 커피", "5kcal", "4,500원", currentUserMoney >= 4500)
        }

        fun createCoffee(): DripCoffee {
            return DripCoffee("오늘의 커피", "5kcal", "4,200원", currentUserMoney >= 4200)
        }
    }
    private var isSeasonal = false
    fun isLimitedSeason(){
        isSeasonal=true
    }
    override fun printName(): String {
        return if(isSeasonal) "$name(Seasonal Limited)" else name
    }
}