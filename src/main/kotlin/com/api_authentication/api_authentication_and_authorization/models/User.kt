package com.api_authentication.api_authentication_and_authorization.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id
    val id: String?,
    var name: String,
    var age: Number,
    var address: String
)