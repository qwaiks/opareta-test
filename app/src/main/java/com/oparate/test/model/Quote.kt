package com.oparate.test.model

import kotlinx.serialization.Serializable

@Serializable
data class Quote(val price: Double, val last_updated: String,)