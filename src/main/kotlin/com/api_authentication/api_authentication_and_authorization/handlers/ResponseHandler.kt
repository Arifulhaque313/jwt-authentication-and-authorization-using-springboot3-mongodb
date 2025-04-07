package com.api_authentication.api_authentication_and_authorization.handlers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

class ResponseHandler{
    companion object{  // need to know about companion object in details

        fun success(data: Any, message: String = "Request was succcessful"): ResponseEntity<ApiResponse>{
            val response = ApiResponse(
                status =  HttpStatus.OK.toString(),
                message = message,
                data = data
            )
            return ResponseEntity(response, HttpStatus.OK)
        }

        fun error(message: String, status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ResponseEntity<ApiResponse>{
            val response = ApiResponse(
                status = status.toString(),
                message = message,
                data = null
            )
            return ResponseEntity(response, status)
        }
    }
}