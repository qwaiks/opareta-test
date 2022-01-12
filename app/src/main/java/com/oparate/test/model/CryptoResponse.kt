package com.oparate.test.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponse(val data: List<DataResponse>, val statusResponse: StatusResponse)
