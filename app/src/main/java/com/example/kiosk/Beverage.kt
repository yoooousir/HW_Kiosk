package com.example.kiosk

class Beverage(name:String, calories: String, price: String, purchasable:Boolean):StarbucksMenuItem(name, calories, price) {
    companion object{
        val fillTheGreen = Beverage("필 더 그린", "90kcal", "4,500원", currentUserMoney>=4500)
        val fillTheRed = Beverage("필 더 레드", "90kcal", "4,500원", currentUserMoney>=4500)
        val fillTheYellow = Beverage("필 더 옐로우", "100kcal", "4,500원", currentUserMoney>=4500)
        val strawberryJuice = Beverage("딸기주스", "110kcal", "3,800원", currentUserMoney>=3800)
        val mangoJuice = Beverage("망고주스", "117kcal", "4,500원", currentUserMoney>=4500)
        val hallabongJuice = Beverage("한라봉주스", "78kcal", "4,300원", currentUserMoney>=4300)
    }
}