package com.oparate.test.model

data class DataResponse(val id: Int, val name: String, val symbol: String, val slug: String, val last_updated: String, val date_added: String, val qoute :Map<String, Qoute>)