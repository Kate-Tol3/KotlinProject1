package logger



data class FromBankMessage(
    val operation_type: String,
    val currency: String = "None",
    val amount: Double = 0.0,
    val message_text: String = ""
){
    fun messageInfo(){
        println("\n$operation_type, $currency, $amount, $message_text")
    }
}

data class FromCashRegisterMessage(
    val operation_type: String,
    val currency_from: String,
    val currency_to: String,
    val amount: Double = 0.0,
    val message_text: String = ""
){
    fun messageInfo(){
        println("\n$operation_type, $currency_from, $currency_to, $amount, $message_text")
    }
}



