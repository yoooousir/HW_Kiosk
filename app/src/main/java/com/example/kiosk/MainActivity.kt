package com.example.kiosk

import kotlinx.coroutines.*

var currentUserMoney = 0 // 현재 사용자의 금액(글로벌 변수)
var bucketList = mutableListOf<StarbucksMenuItem>() // 장바구니
var sumBucket = 0 // 장바구니 합계

suspend fun main(){
    //메인 메뉴판 화면(대분류)
    val titleArray = arrayOf("콜드 브루", "브루드 커피", "에스프레소", "주스(병음료)")
    println("<메뉴판>")
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
            DripCoffee.createCoffee(),
            DripCoffee.createIceCoffee()
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
        println("세부 정보를 확인할 메뉴의 번호(1~5)를 입력한 뒤 엔터를 누르세요. 0 입력 시 종료:")
        val tmpNum = readln()
        try{
            selectNum=tmpNum.toInt()
        }catch(e: NumberFormatException){
            println("정수가 아닌 입력입니다. 다시 시도해주세요.")
            continue
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
                println("----------------------------------")
                break
            }
            else -> println("1~5의 숫자를 입력해주세요.")
        }
    }

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
    println("----------------------------------")

    coroutineScope{
        //lv4 장바구니 담기 및 구매
        launch {
            //구매를 원하는 메뉴판 번호 입력받기
            var selNum:Int?=null
            var priceSum=0
            while(true){
                println("[메뉴]")
                println("<1. 콜드 브루>, <2. 브루드 커피>, <3. 에스프레소>, <4. 주스(병음료)>")
                println("구매하고 싶으신 음료가 있는 메뉴의 번호(1~4)를 입력해주세요. (종료: 0, 바로 구매: -1, 장바구니 확인: -2)")
//        if(bucketList.isNotEmpty())print(", 바로 구매: -1")
//        println(", 장바구니 확인: -2)")

                //정수입력 예외처리
                val tmpNum = readln()
                try{
                    selNum = tmpNum.toInt()
                }catch(e: NumberFormatException){
                    println("정수가 아닌 입력입니다. 다시 시도해주세요.")
                }

                //결과적으로 종료되는(종료될 수 있는) 상황
                if(selNum==-1 || selNum==0){
                    if (selNum==-1){
                        if(bucketList.isNotEmpty()){
                            //구매결정
                            purchase()
                            println("계속 구매하시겠습니까? (Yes: 1, No: else input)")
                            if(readln()=="1")continue
                        }
                        else{
                            delay(500) //0.5초 지연
                            println("//////////////////////////////////")
                            println("장바구니가 비었습니다. 구매하실 상품을 선택해주세요.")
                            println("//////////////////////////////////")
                            continue
                        }
                    }
                    println("종료되었습니다.")
                    println("<END>.")
                    break
                }
                else if(selNum==-2) {
                    delay(500) //0.5초 지연
                    printBucketList()
                    continue
                }

                when(selNum){
                    1-> {
                        saveToWishList("콜드 브루", coldBrew)
                    }
                    2-> {
                        saveToWishList("브루드 커피", dripCoffee)
                    }
                    3-> {
                        saveToWishList("에스프레소", espresso)
                    }
                    4-> {
                        saveToWishList("주스(병음료)", beverage)
                    }
                    else->{
                        println("잘못된 입력입니다.")
                        continue
                    }
                }
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

// 장바구니 출력 함수
suspend fun printBucketList() {
    println("//////////////////////////////////")
    if (bucketList.isEmpty()) {
        println("장바구니가 비었습니다.")
    } else {
        println("<장바구니 목록>")
        bucketList.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name}: ${item.price} (${item.calories})")
        }
        println("총 합계: $sumBucket 원")
    }
    println("(내 잔액: $currentUserMoney 원)")
    println("//////////////////////////////////")
}

//장바구니 담기 함수
suspend fun saveToWishList(title:String, items: List<StarbucksMenuItem>) {
    var menuNum: Int?

    while (true) {
        println("[장바구니 담기]")
        printMenuPrices(title, items)
        println("장바구니에 담으실 음료(1~${items.size})를 선택해주세요. (메뉴로 돌아가기: 0, 구매하러 가기: -1, 장바구니 확인: -2)")
        menuNum = readln().toIntOrNull()

        //메뉴선택 입력 예외처리
        if (menuNum == null) {
            println("정수가 아닌 입력입니다. 다시 시도해주세요.")
            continue
        }
        //종료
        else if (menuNum == 0) {
            println("장바구니 담기가 종료되었습니다. 메뉴로 돌아갑니다.")
            println("----------------------------------")
            break
        }
        //바로 구매
        else if(menuNum==-1){
            if(bucketList.isNotEmpty()){
                println("----------------------------------")
                purchase()
                break
            }
            else{
                delay(500) //0.5초 지연
                println("//////////////////////////////////")
                println("장바구니가 비었습니다. 구매하실 상품을 선택해주세요.")
                println("//////////////////////////////////")
                continue
            }
        }
        //장바구니 확인
        else if(menuNum==-2){
            delay(500) //0.5초 지연
            printBucketList()
            continue
        }

        //장바구니 담기
        else if (menuNum in 1..items.size) {
            val selectedItem = items[menuNum - 1]
            bucketList.add(items[menuNum - 1])
            sumBucket += selectedItem.price()

            println("${selectedItem.name}을/를 장바구니에 추가했습니다.")
            println("----------------------------------")
            //printBucketList()
        }
        else{
            println("1~${items.size}의 수를 입력해주세요.")
        }
    }
}

suspend fun purchase(){
    println("[구매하기]")
    //현재 장바구니 리스트 출력
    printBucketList()

    //전체 or 선택 상품 구매
    var sumToBuy = 0
    var toBuyList: List<Int> = emptyList()
    var goingToBuy = true
    var buyOption = true //전체구매:true, 선택구매:false

    println("전체 상품을 바로 구매하시려면 1을, 구매하실 상품들을 선택하시려면 else input을 입력해주세요.")
    if(readln()=="1"){
        sumToBuy= sumBucket
        println("결제 진행 중...")
        delay(3000) //3초 지연
    }

    else{
        buyOption=false
        //구매할 목록 입력(올바르게 입력할 때까지 반복)
        var invalidInputCnt=0
        while(invalidInputCnt++<5){
            println("구매하실 상품 번호들을 공백을 사이에 두고 입력해주세요 (1~${bucketList.size}).")
            val userInput = readln()
            toBuyList = userInput.split(' ')
                .mapNotNull { it.trim().toIntOrNull() } // 숫자로 변환 가능한 경우만 처리
                .filter { it in 1..bucketList.size } // 유효한 범위 내의 번호만 선택, 중복 입력은 수량 증가로 처리
                .sorted()
            if(toBuyList.isEmpty()){
                if(invalidInputCnt==5) {
                    println("상품 번호가 올바르지 않습니다. 입력 오류 5회로 구매가 취소되었습니다.")
                    println("----------------------------------")
                    goingToBuy=false
                    break
                }
                println("상품 번호가 올바르지 않습니다. 다시 시도해주세요. (입력오류(${invalidInputCnt} / 5)")
            }
            else{
                //올바른 입력(while문 종료)
                println("아래와 같이 구매를 진행하시려면 1, 취소하시려면 else input을 입력해주세요.")
                var sumTmp = 0
                //val itemCounts = toBuyList.groupingBy { it }.eachCount() //각 요소의 중복 나타나는 횟수
                toBuyList.forEachIndexed { idx, it ->
                    //val count = itemCounts[it] ?: 0 // 해당 항목의 중복 횟수를 가져오거나, 없으면 0
                    println("${idx + 1}. ${bucketList[it - 1].name} : ${bucketList[it - 1].price()}원")
                    sumTmp += bucketList[it - 1].price()
                }
                println("총 구매하실 금액: $sumTmp 원")

                if(readln()=="1"){
                    println("결제 진행 중...")
                    delay(3000) //3초 지연
                    sumToBuy=sumTmp
                }else{
                    println("구매가 취소되었습니다.")
                    println("----------------------------------")
                    goingToBuy=false
                }
                break
            }
        }
    }
    if(goingToBuy){
        //최종 구매 결정 리스트
        if (sumToBuy> currentUserMoney){
            println("구매 합계(${sumToBuy})가 현재 잔액(${currentUserMoney})을 초과합니다. 구매하실 목록을 수정하시려면 1, 취소하시려면 else input을 입력해주세요.")
            if(readln()=="1")
                purchase()
            else{
                println("구매가 취소되었습니다.")
                println("----------------------------------")
            }
        }

        else{
            println("최종 구매가 완료되었습니다.")
            println("최종 구매 합계: $sumToBuy 원")
            currentUserMoney-=sumToBuy
            println("현재 남은 잔액: $currentUserMoney 원")
            println("----------------------------------")

            //장바구니 수정
            if(!buyOption && toBuyList.isNotEmpty()) {
                bucketList = bucketList.filterIndexed { index, _ ->
                    !toBuyList.contains(index + 1) // bucketList는 0부터 시작하므로, 1을 더해준다
                }.toMutableList()
            }
            else if(buyOption) bucketList.clear()

            sumBucket-=sumToBuy
            //printBucketList()
        }
    }
}