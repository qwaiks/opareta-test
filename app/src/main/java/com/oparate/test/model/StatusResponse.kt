package com.oparate.test.model

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val timestamp: String,
)