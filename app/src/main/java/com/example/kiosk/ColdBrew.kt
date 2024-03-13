package com.example.kiosk

class ColdBrew(name:String, calories: String, price: String, purchasable:Boolean):StarbucksMenuItem(name, calories, price) {
    companion object {
        val vanillaCreamColdBrew = ColdBrew("바닐라 크림 콜드 브루", "125kcal", "5,800원", currentUserMoney>=5800)
        val signatureTheBlackColdBrew = ColdBrew("시그니처 더 블랙 콜드 브루", "25kcl", "19,600원", currentUserMoney>=19600)
        val oatColdBrew = ColdBrew("오트 콜드 브루", "120kcl", "5,800원", currentUserMoney>=5800)
        val coldBrew = ColdBrew("콜드 브루", "5kcl", "4,900원", currentUserMoney>=4900)
    }
}
