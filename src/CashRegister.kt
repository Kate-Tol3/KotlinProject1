package CashRegister

import bank.Bank
import logger.*


interface CashRegisterInterface {
    fun exchangeRubToUsd(amount: Double): Double
    fun exchangeUsdToRub(amount: Double): Double
    fun checkIntegrity(): Boolean
}

class CashRegister(private val bank: Bank, private val bank_id:String) : CashRegisterInterface {
    private var exchangeRate: Double = bank.getExchangeRate()
    private var logs = mutableListOf<FromCashRegisterMessage>()


    override fun exchangeRubToUsd(amount: Double): Double {
        checkIntegrity()
        if (amount < 0 || amount > bank.getRubBalance()) {
            logs.add(FromCashRegisterMessage("exchangeError", "RUB", "USD", amount, "Invalid amount has been submitted"))
            throw Exception("Invalid amount has been submitted!")
        }
        val dollars = amount/exchangeRate
        bank.sendRub(amount)
        bank.receiveUsd(dollars)
        logs.add(FromCashRegisterMessage("exchange", "RUB", "USD", amount))
        return dollars
    }

    override fun exchangeUsdToRub(amount: Double): Double {
        checkIntegrity()
        if (amount > bank.getUsdBalance() || amount < 0) {
            logs.add(FromCashRegisterMessage("exchangeError", "USD", "RUB", amount, "Invalid amount has been submitted"))
            throw Exception("Invalid amount has been submitted!")
        }
        val rubbles = amount * exchangeRate
        bank.sendUsd(amount)
        bank.receiveRub(rubbles)
        logs.add(FromCashRegisterMessage("exchange", "USD", "RUB", amount))
        return rubbles
    }

    override fun checkIntegrity():Boolean{
        return bank.checkId(bank_id)
    }

    fun showLog(){
        for (log in logs){
            log.messageInfo()
        }
    }
}

