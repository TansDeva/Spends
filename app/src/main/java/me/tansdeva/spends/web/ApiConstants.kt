package me.tansdeva.spends.web

class ApiConstants {

    companion object {
        private const val PROTOCOL = "https"
        private const val BASE_URL = "$PROTOCOL://interviewer-api.herokuapp.com"
        const val WELCOME_API = BASE_URL
        const val USER_LOGIN = "$BASE_URL/login"
        const val GET_BALANCE = "$BASE_URL/balance"
        const val GET_TRANSACTIONS = "$BASE_URL/transactions"
        const val ADD_SPEND = "$BASE_URL/spend"
    }
}