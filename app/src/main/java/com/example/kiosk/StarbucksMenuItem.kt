package com.example.kiosk

open class StarbucksMenuItem(val name:String, val calories:String, val price:String) {
    fun price():Int{
        return price.filter { it.isDigit() }.toInt()
    }
    fun calories():Int{
        return calories.filter { it.isDigit() }.toInt()
    }
    fun printName():String{
        return name
    }
    fun purchasable(): Boolean = currentUserMoney >= price()
}