package com.samkruglov.bank.ui.error

class GraphQLException(message: String?, val extensions: Map<String, Any>?) : RuntimeException(message) {
}