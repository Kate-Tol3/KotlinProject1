import test.*
import bank.*

fun main() {
    TestBank().test()
    println("\n~~~All bank tests have successfully passed!~~~")
    TestCashRegister().test()
    println("\n~~~All bank cash register tests have successfully passed!~~~")

    //смотрим как работает логирование
    val bank: Bank = Bank(1000.0, 1000.0, 70.0)
    bank.sendRub(100.0)
    bank.setExchangeRate(40.0)

    val cashRegister = bank.createCashRegister()
    cashRegister.checkIntegrity()
    cashRegister.exchangeRubToUsd(100.0)

    bank.showLog()
    cashRegister.showLog()

}
