package com.seven.assignment.data.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
	@SerializedName("status_message") val statusMessage : String,
	@SerializedName("success") val success : Boolean,
	@SerializedName("status_code") val statusCode : Int
)