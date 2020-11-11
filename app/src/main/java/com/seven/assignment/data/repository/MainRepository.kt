package com.seven.assignment.data.repository

import com.google.gson.Gson
import com.seven.assignment.data.remote.ApiService
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val apiService: ApiService,
    gson: Gson
) : BaseDataSource(gson) {

}