package com.api_authentication.api_authentication_and_authorization.models

data class UserDTO(
    val name: String,
    val age: Number,
    val address: String
){
    init{
        require(name.isNotBlank()){"Name cannot be null or blank"}
        require(age.toInt() in 18..100) { "Age must be between 18 to 100" }
        require(address.isNotBlank()){"Address cannot be null or blank"}
    }
}