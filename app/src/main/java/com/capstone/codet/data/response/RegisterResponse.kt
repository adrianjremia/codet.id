package com.capstone.codet.data.response

import com.google.gson.annotations.SerializedName


data class RegisterResponse (
    val message: String? = null,
    val user: User? = null
)


data class User (
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null
)
