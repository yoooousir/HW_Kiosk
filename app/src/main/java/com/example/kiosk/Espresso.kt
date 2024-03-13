package com.example.kiosk

class Espresso(name:String, calories: String, price: String, purchasable: Boolean):StarbucksMenuItem(name, calories, price) {
    companion object{
        val chouxCreamLatte = Espresso("슈크림 라떼", "297kcal", "6,300원", currentUserMoney>=6300)
        val espressoConPanna = Espresso("에스프레소 콘 파나", "30kcal", "5,700원", currentUserMoney>=5700)
        val espressoMacchiato = Espresso("에스프레소 마키아또", "10kcal", "5,500원", currentUserMoney>=5500)
        val caffeAmericano = Espresso("카페 아메리카노", "10kcal", "4,500원", currentUserMoney>=4500)
        val caramelMacchiato = Espresso("카라멜 마키아또", "200kcal", "5,900원", currentUserMoney>=5900)
        val cappuccino = Espresso("카푸치노", "110kcal", "6,500원", currentUserMoney>=6500)
    }
}