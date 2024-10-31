package bank


import CashRegister.*
import logger.*
import java.util.UUID


interface BankInterface {

    fun sendRub(amount: Double): Double
    fun sendUsd(amount: Double): Double
    fun receiveRub(amount: Double)
    fun receiveUsd(amount: Double)
    fun createCashRegister(): CashRegister

}

class Bank(private var rubBalance: Double = 0.0, private var usdBalance: Double  = 0.0, private var exchangeRate: Double = 80.0) : BankInterface {

    private val id: String = UUID.randomUUID().toString()
    private var logs = mutableListOf<FromBankMessage>()

    override fun sendRub(amount: Double): Double {
        if (amount > rubBalance) {
            logs.add(FromBankMessage("transactionError", "RUB", amount - rubBalance, "lack of funds"))
            throw Exception("Not enough money on balance!")
        } else {
            rubBalance -= amount
            logs.add(FromBankMessage("sent", "RUB", amount))
            return amount
        }

    }

    override fun sendUsd(amount: Double): Double {

        if (amount > usdBalance) {
            logs.add(FromBankMessage("transactionError", "USD", amount - usdBalance, "lack of funds"))
            throw Exception("Not enough money on balance!")
        } else {
            usdBalance -= amount
            logs.add(FromBankMessage("sent", "USD", amount))
            return amount
        }
    }

    override fun receiveRub(amount: Double) {
        rubBalance += amount
        logs.add(FromBankMessage("received", "RUB", amount))
    }

    override fun receiveUsd(amount: Double) {
        usdBalance += amount
        logs.add(FromBankMessage("received", "USD", amount))
    }

    override fun createCashRegister(): CashRegister {
        val new_cash_register = CashRegister(this, id)
        logs.add(FromBankMessage("creadted", "None", 0.0, "new_cash_register has been created"))
        return new_cash_register
    }

    fun getRubBalance(): Double {
        logs.add(FromBankMessage("balance_check", "RUB", rubBalance))
        return rubBalance
    }

    fun getUsdBalance(): Double {
        logs.add(FromBankMessage("balance_check", "USD", rubBalance))
        return usdBalance
    }

    fun getExchangeRate(): Double {
        logs.add(FromBankMessage("exchange_rate_check", "RUB", exchangeRate))
        return exchangeRate
    }

    fun setExchangeRate(newRate: Double): Unit {
        exchangeRate = newRate
        logs.add(FromBankMessage("exchange_rate_change", "RUB", exchangeRate))
    }

    fun checkId(cassId: String): Boolean {
        return id == cassId
    }
    
    fun showLog(){
        for (log in logs){
            log.messageInfo()
        }
    }

}