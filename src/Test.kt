package test

import bank.*

class TestBank{

    private fun testSendRub(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val rubles: Double = 100.0
        val initialBalance: Double = bank.getRubBalance()
        bank.sendRub(rubles)
        assert(bank.getRubBalance() == (initialBalance - rubles))
        println("\nПеревод составил $rubles рублей")
        println("Остаток рублей в банке: ${bank.getRubBalance()}")
    }
    private fun testReceiveRub(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val rubles: Double = 100.0
        val initialBalance: Double = bank.getRubBalance()
        bank.receiveRub(rubles)
        assert(bank.getRubBalance() == (initialBalance + rubles))
        println("\nПолучено $rubles рублей")
        println("Остаток рублей в банке: ${bank.getRubBalance()}")

    }
    private fun testSendUsd(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val dollars: Double = 100.0
        val initialBalance: Double = bank.getUsdBalance()
        bank.sendUsd(dollars)
        assert(bank.getUsdBalance() == (initialBalance - dollars))
        println("\nПеревод составил $dollars долларов")
        println("Остаток долларов в банке: ${bank.getUsdBalance()}")
    }
    private fun testReceiveUsd(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val dollars: Double = 100.0
        val initialBalance: Double = bank.getUsdBalance()
        bank.sendUsd(dollars)
        assert(bank.getUsdBalance() == (initialBalance + dollars))
        println("\nПолучено $dollars долларов")
        println("Остаток долларов в банке: ${bank.getUsdBalance()}")
    }

    private fun testSetExchangeRate(){
        val bank: Bank = Bank(100000.0, 1000.0)
        bank.setExchangeRate(1.0)
        assert(bank.getExchangeRate() == 1.0)
        print("\nКурс успешно изменен")
    }

     fun test(){
        testSendRub()
        testReceiveRub()
        testSendUsd()
        testReceiveUsd()
        testSetExchangeRate()
    }
}

class TestCashRegister{

    private fun testExchangeToRub(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val cashRegister = bank.createCashRegister()
        val initialRubBalance: Double = bank.getRubBalance()
        val initialUsdBalance: Double = bank.getUsdBalance()
        val amount = 100.0
        val rubles = cashRegister.exchangeUsdToRub(amount)
        assert(bank.getRubBalance() == initialRubBalance + rubles)
        assert(bank.getUsdBalance() == initialUsdBalance - amount)
        println("\nПолучено рублей: $rubles")
        println("Остаток долларов в банке: ${bank.getUsdBalance()}")
        try{
            cashRegister.exchangeUsdToRub(bank.getUsdBalance() + 1000)
        }
        catch (e: Exception){
            println()
            println(e.message)
            println("Неверно введена сумма, транзакция не произведена")
        }
    }

    private fun testExchangeToUsd(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val cashRegister = bank.createCashRegister()
        val initialRubBalance: Double = bank.getRubBalance()
        val initialUsdBalance: Double = bank.getUsdBalance()
        val amount = 8000.0
        val dollars = cashRegister.exchangeRubToUsd(amount)
        assert(bank.getRubBalance() == initialRubBalance - amount)
        assert(bank.getUsdBalance() == initialUsdBalance + dollars)
        println("\nПолучено долларов: $dollars")
        println("Остаток рублей в банке: ${bank.getRubBalance()}")
        try{
            cashRegister.exchangeRubToUsd(bank.getRubBalance() + 1000)
        }
        catch (e: Exception){
            println()
            println(e.message)
            println("Неверно введена сумма, транзакция не произведена")
        }
    }

    private fun testCheckIntegrity(){
        val bank: Bank = Bank(100000.0, 1000.0)
        val cashRegister = bank.createCashRegister()
        assert(cashRegister.checkIntegrity())
        println("\nКасса десйствительно создана методом класса Bank")
    }

    fun test(){
        testExchangeToRub()
        testExchangeToUsd()
        testCheckIntegrity()
    }

}
