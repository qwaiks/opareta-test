package com.oparate.test.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponse(val status: StatusResponse,val data: List<DataResponse>, )
