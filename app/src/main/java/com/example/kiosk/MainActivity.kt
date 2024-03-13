package com.example.kiosk

var currentUserMoney = 0 // 현재 사용자의 금액(글로벌 변수)

fun main(){
    //메인 메뉴판 화면(대분류)
    val titleArray = arrayOf("콜드 브루", "브루드 커피", "에스프레소", "주스(병음료)")
    println("<메뉴판(대분류)>")
    println("1. 전체 보기")
    for(i in titleArray.indices){
        println("${i+2}. ${titleArray[i]}")
    }
    val coldBrew = ArrayList<ColdBrew>().apply {
        addAll(listOf(
            ColdBrew.oatColdBrew,
            ColdBrew.coldBrew,
            ColdBrew.vanillaCreamColdBrew,
            ColdBrew.signatureTheBlackColdBrew
        ))
    }
    val dripCoffee = ArrayList<DripCoffee>().apply {
        addAll(listOf(
            DripCoffee.coffee,
            DripCoffee.iceCoffee
        ))
    }

    val espresso = ArrayList<Espresso>().apply{
        addAll(listOf(
            Espresso.espressoMacchiato,
            Espresso.espressoConPanna,
            Espresso.caffeAmericano,
            Espresso.cappuccino,
            Espresso.caramelMacchiato,
            Espresso.chouxCreamLatte
        ))
    }
    val beverage = ArrayList<Beverage>().apply{
        addAll(listOf(
            Beverage.fillTheGreen,
            Beverage.fillTheRed,
            Beverage.fillTheYellow,
            Beverage.mangoJuice,
            Beverage.strawberryJuice,
            Beverage.hallabongJuice
        ))
    }
    val allMenuItems = ArrayList<StarbucksMenuItem>().apply{
        addAll(coldBrew)
        addAll(dripCoffee)
        addAll(espresso)
        addAll(beverage)
    }

        //대분류 메뉴에 해당하는 숫자를 입력하면 세부 메뉴들을 보여줌
        //0 입력 시 종료

    var selectNum: Int?=null
    while (true) {
        println("세부 매뉴를 확인할 대분류 번호(1~5)를 입력한 뒤 엔터를 누르세요. 0 입력 시 종료:")
        val tmpNum = readln()
        try{
            selectNum=tmpNum.toInt()
        }catch(e: NumberFormatException){
            println("잘못된 입력입니다. 다시 시도해주세요.")
        }
        when (selectNum) {
            1 -> {
                //printMenuItems("전체 메뉴", allMenuItems)
                println("[전체 메뉴]")
                printMenuItems(titleArray[0], coldBrew)
                printMenuItems(titleArray[1], dripCoffee)
                printMenuItems(titleArray[2], espresso)
                printMenuItems(titleArray[3], beverage)
            }
            2 -> {
                printMenuItems(titleArray[0], coldBrew)
            }
            3 -> {
                printMenuItems(titleArray[1], dripCoffee)
            }
            4 -> {
                printMenuItems(titleArray[2], espresso)
            }
            5 -> {
                printMenuItems(titleArray[3], beverage)
            }
            0 -> {
                println("종료되었습니다.")
                break
            }
            else -> println("잘못된 입력입니다.")
        }
    }


    //lv4
    var money:Int? = null
    println("현재 소지하고 계신 금액을 입력해주세요. (숫자만 입력)")
    while (money == null) {
        val userInput = readln()
        try {
            money = userInput.toInt()
        } catch (e: NumberFormatException) {
            println("정수가 아닌 입력입니다. 다시 시도해주세요.")
        }
    }
    println("현재 잔액: ${money}원")
    currentUserMoney=money

    //구매를 원하는 메뉴판 번호 입력받기
    var selNum:Int?=null
    var priceSum=0
    while(true){
        println("<1. 콜드 브루>, <2. 브루드 커피>, <3. 에스프레소>, <4. 주스(병음료)>")
        println("구매하고 싶으신 음료가 있는 메뉴의 번호(1~4)를 입력해주세요. 0 입력 시 종료.")
        val tmpNum = readln()
        try{
            selNum = tmpNum.toInt()
        }catch(e: NumberFormatException){
            println("정수가 아닌 입력입니다. 다시 시도해주세요.")
        }
        if(selNum==0){
            println("종료되었습니다.")
            break
        }

        when(selNum){
            1-> {
                printMenuPrices("콜드 브루", coldBrew)
                purchase("콜드 브루", coldBrew)
            }
            2-> {
                printMenuPrices("브루드 커피", dripCoffee)
                purchase("브루드 커피", dripCoffee)
            }
            3-> {
                printMenuPrices("에스프레소", espresso)
                purchase("에스프레소", espresso)
            }
            4-> {
                printMenuPrices("주스(병음료)", beverage)
                purchase("주스(병음료)", beverage)
            }
            else->{
                println("잘못된 입력입니다.")
                continue
            }
        }

    }
}

fun printMenuItems(title: String, items: List<StarbucksMenuItem>) {
    println("<$title>")
    items.forEachIndexed { index, item ->
        print(item.printName())
        if (index != items.lastIndex) {
            print(", ")
        }
    }
    println()
}
fun printMenuPrices(title:String, items: List<StarbucksMenuItem>){
    println("<$title>")
    var indexNum=1
    items.forEachIndexed{index, item->
        println("${indexNum++}. ${item.printName()}: ${item.price} (${item.calories}) - 구매 ${if (item.purchasable()) "가능" else "불가"}")
    }
}
fun purchase(title:String, items: List<StarbucksMenuItem>) {
    var sumBucket = 0 // 장바구니 합계
    var bucketList = mutableListOf<Int>() // 장바구니
    var menuNum: Int?

    while (true) {
        print("장바구니에 담으실 음료(1~${items.size})를 선택해주세요. (")
        if(!(bucketList.isEmpty())){
            print("상품 구매: -1, ")
        }
        println("메뉴 화면으로 돌아가기: 0)")
        menuNum = readln().toIntOrNull()

        if (menuNum == null) {
            println("정수가 아닌 입력입니다. 다시 시도해주세요.")
            continue
        } else if (menuNum == 0) {
            println("상품 담기가 종료되었습니다.")
            break
        }
        else if(menuNum==-1){
            println("<장바구니 목록>")
            bucketList.forEach { index ->
                val item = items[index - 1]
                println("${item.name}: ${item.price}")
            }
            println("전체 구매를 원하시면 1, 장바구니 목록 중 선택하시려면 그 외 키를 눌러주세요.")
            if(readln()=="1"){
                println("전체 상품 구매가 완료되었습니다.")
                println("총 구매 금액: $sumBucket")
                currentUserMoney-=sumBucket
                bucketList.clear()
                sumBucket=0
            }
            else{
                println("구매하실 상품 번호를 공백을 사이에 두고 입력한 뒤 <enter> 키를 눌러주세요.")
                val toBuyList = readln().split(' ').mapNotNull { it.trim().toIntOrNull() }
                toBuyList.sorted()
                bucketList.removeAll(toBuyList)
                var buySum = 0
                println("선택하신 상품 구매가 완료되었습니다.")
                println("선택하신 상품 목록:")
                if(toBuyList.size<=0){
                    println("구매하실 상품을 선택하지 않으셨습니다.")
                    continue
                }
                else{
                    toBuyList.forEachIndexed() { index, item ->
                        println("${index+1}. ${items[item-1].name}: ${items[item-1].price}")
                        buySum+=items[item-1].price()
                    }
                    println("총 구매 금액: $buySum")
                    currentUserMoney-=buySum
                    sumBucket-=buySum
                }
            }
            println("현재 잔액: $currentUserMoney")
            println("현재 장바구니 목록:")
            if(bucketList.isEmpty()) println("장바구니가 비었습니다.")
            else{
                bucketList.forEach { index ->
                    val item = items[index - 1]
                    println("${item.name}: ${item.price}")
                }
            }
            println("상품을 더 구매하시려면 1을 입력해주세요. (그 외 입력 시 메뉴 선택 화면으로 돌아갑니다.)")
            if(readln()!="1"){
                println("$title 메뉴 구매가 종료되었습니다. 메뉴 선택 화면으로 돌아갑니다.")
                break
            }
        }
        else if (menuNum in 1..items.size) {
            val selectedItem = items[menuNum - 1]
            if (sumBucket + selectedItem.price() > currentUserMoney) {
                println("장바구니 합계(${sumBucket + selectedItem.price()})가 잔액($currentUserMoney)을 초과합니다. 다른 상품을 선택해주세요.")
                continue
            } else {
                bucketList.add(menuNum)
                sumBucket += selectedItem.price()
                println("${selectedItem.name}을/를 장바구니에 추가했습니다. (장바구니 합계: $sumBucket)")

                //구매 진행
                println("구매를 진행하시려면 1을 입력해주세요. (그 외 입력: 상품 선택으로 돌아가기)")
                if(readln()=="1") {
                    println("<장바구니 목록>")
                    bucketList.forEach { index ->
                        val item = items[index - 1]
                        println("${item.name}: ${item.price}")
                    }
                    println("전체 구매를 원하시면 1, 장바구니 목록 중 선택하시려면 그 외 키를 눌러주세요.")
                    if(readln()=="1"){
                        println("전체 상품 구매가 완료되었습니다.")
                        println("총 구매 금액: $sumBucket")
                        currentUserMoney-=sumBucket
                        sumBucket=0
                        bucketList.clear()
                    }
                    else{
                        println("구매하실 상품 번호를 공백을 사이에 두고 입력한 뒤 <enter> 키를 눌러주세요.")
                        val toBuyList = readln().split(' ').mapNotNull { it.trim().toIntOrNull() }
                        toBuyList.sorted()
                        bucketList.removeAll(toBuyList)
                        var buySum = 0
                        println("선택하신 상품 구매가 완료되었습니다.")
                        println("선택하신 상품 목록:")
                        if(toBuyList.size<=0){
                            println("구매하실 상품을 선택하지 않으셨습니다.")
                            continue
                        }
                        else{
                            toBuyList.forEachIndexed() { index, item ->
                                println("${index+1}. ${items[item-1].name}: ${items[item-1].price}")
                                buySum+=items[item-1].price()
                            }
                            println("총 구매 금액: $buySum")
                            currentUserMoney-=buySum
                            sumBucket-=buySum
                        }
                    }
                    println("현재 잔액: $currentUserMoney")
                    println("현재 장바구니 목록:")
                    if(bucketList.isEmpty()) println("장바구니가 비었습니다.")
                    else{
                        bucketList.forEach { index ->
                            val item = items[index - 1]
                            println("${item.name}: ${item.price}")
                        }
                    }
                    println("상품을 더 구매하시려면 1을 입력해주세요. (그 외 입력 시 메뉴 선택 화면으로 돌아갑니다.)")
                    if(readln()!="1"){
                        println("$title 메뉴 구매가 종료되었습니다. 메뉴 선택 화면으로 돌아갑니다.")
                        break
                    }
                }
                else{
                    continue //else문은 주석처리로 바꾸기
                }
            }
        }
        else {
            println("잘못된 입력입니다.")
        }
    }
}
