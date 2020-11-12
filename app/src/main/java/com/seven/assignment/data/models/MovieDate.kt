package com.seven.assignment.data.models

import com.google.gson.annotations.SerializedName


data class MovieDate(
	@SerializedName("maximum") val maximum: String,
	@SerializedName("minimum") val minimum: String
)