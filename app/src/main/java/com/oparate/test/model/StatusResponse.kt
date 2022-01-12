package com.oparate.test.model

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val timestamp: String,
    val errorCode: Int,
    val errorMessage: String,
    val elapsed: Int
)