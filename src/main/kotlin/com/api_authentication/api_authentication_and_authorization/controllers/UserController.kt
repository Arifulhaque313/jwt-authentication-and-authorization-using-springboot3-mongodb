package com.api_authentication.api_authentication_and_authorization.controllers

import com.api_authentication.api_authentication_and_authorization.handlers.ApiResponse
import com.api_authentication.api_authentication_and_authorization.handlers.ResponseHandler
import com.api_authentication.api_authentication_and_authorization.models.UserDTO
import com.api_authentication.api_authentication_and_authorization.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun get(): ResponseEntity<ApiResponse> =
        try {
            val users = userService.getAllUsers()
            ResponseHandler.success(users)
        } catch (e: Exception) {
            ResponseHandler.error("An error occurred while fetching users", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<ApiResponse> =
        try {
            val user = userService.getUserById(id)
            ResponseHandler.success(user)
        } catch (e: ResponseStatusException) {
            ResponseHandler.error(e.reason ?: "User not found.", HttpStatus.NOT_FOUND)
        }

    @PostMapping
    fun create(@RequestBody request: UserDTO): ResponseEntity<ApiResponse> =
        try {
            val user = userService.createUser(request)
            ResponseHandler.success(user)
        } catch (e: Exception) {
            ResponseHandler.error(e.message ?: "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR)
//            ResponseHandler.error("An error occurred while creating the user", HttpStatus.INTERNAL_SERVER_ERROR)

        }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: UserDTO): ResponseEntity<ApiResponse> =
        try {
            val user = userService.updateUser(id, request)
            ResponseHandler.success(user)
        } catch (e: ResponseStatusException) {
            ResponseHandler.error(e.reason ?: "User not found", HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseHandler.error("An error occurred while updating the user", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<ApiResponse> =
        try {
            userService.deleteUser(id)
            ResponseHandler.success(mapOf("message" to "User deleted successfully"))
        } catch (e: ResponseStatusException) {
            ResponseHandler.error(e.reason ?: "User not found", HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseHandler.error("An error occurred while deleting the user", HttpStatus.INTERNAL_SERVER_ERROR)
        }
}