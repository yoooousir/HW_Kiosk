package com.example.kiosk

class DripCoffee(name:String, calories: String, price: String, purchasable:Boolean):StarbucksMenuItem(name, calories, price) {
    companion object{
        val iceCoffee = DripCoffee("아이스 커피", "5kcal", "4,500원", currentUserMoney>=4500)
        val coffee = DripCoffee("오늘의 커피", "5kcal", "4,200원", currentUserMoney>=4200)
    }
}