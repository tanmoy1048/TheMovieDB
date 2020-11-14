package com.seven.assignment.data

enum class Status {
    FIRST_TIME_RUNNING,
    RUNNING,
    SUCCESS,
    SUCCESS_EMPTY,
    FAILED,
    NOTHING
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null
) {
    companion object {
        val EMPTY = NetworkState(Status.SUCCESS_EMPTY)
        val LOADED = NetworkState(Status.SUCCESS)
        val FIRST_TIME_LOADING = NetworkState(Status.FIRST_TIME_RUNNING)
        val LOADING = NetworkState(Status.RUNNING)
        val DO_NOTHING = NetworkState(Status.NOTHING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}