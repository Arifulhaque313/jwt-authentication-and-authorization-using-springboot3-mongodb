package com.api_authentication.api_authentication_and_authorization.controllers

import com.api_authentication.api_authentication_and_authorization.handlers.ApiResponse
import com.api_authentication.api_authentication_and_authorization.handlers.ResponseHandler
import com.api_authentication.api_authentication_and_authorization.models.User
import com.api_authentication.api_authentication_and_authorization.models.UserDTO
import com.api_authentication.api_authentication_and_authorization.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository
){
    @GetMapping
    fun get():ResponseEntity<ApiResponse> =
        try {
            val users = userRepository.findAll()
            ResponseHandler.success(users)
        }catch (e:Exception){
            ResponseHandler.error("An error occurred while fetching users", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<ApiResponse> =
        try {
            val user = userRepository.findById(id).orElseThrow{
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
            }
            ResponseHandler.success(user)
        }catch (e:ResponseStatusException){
            ResponseHandler.error(e.reason ?:"User not found.", HttpStatus.NOT_FOUND)
        }

    @PostMapping
    fun create(@RequestBody request: UserDTO): ResponseEntity<ApiResponse> =
        try {
            val newUser = User(
                id = null,
                name = request.name,
                age = request.age,
                address = request.address
            )
            val saveUser = userRepository.save(newUser)
            ResponseHandler.success(saveUser)
        } catch (e: Exception){
            ResponseHandler.error("An error occurred while createing the user", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @PutMapping
    fun update(@PathVariable id: String, @RequestBody request: UserDTO): ResponseEntity<ApiResponse> =
        try{
            val existingUser = userRepository.findById(id).orElseThrow{
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found")
            }

            existingUser.apply {
                name = request.name;
                age = request.age;
                address = request.address
            }

            val updateUser = userRepository.save(existingUser)
            ResponseHandler.success(updateUser)
        }catch (e:ResponseStatusException){
            ResponseHandler.error(e.reason?:"User not found.", HttpStatus.NOT_FOUND)
        }catch (e:Exception){
            ResponseHandler.error("An error occurred while updateing the user.", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:String):ResponseEntity<ApiResponse> =
        try{
            val user = userRepository.findById(id).orElseThrow{
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            }

            userRepository.delete(user)
            ResponseHandler.success(mapOf("message" to "user deleted successfully"))
        }catch (e: ResponseStatusException){
            ResponseHandler.error(e.reason?: "User not found", HttpStatus.NOT_FOUND)
        }catch (e: Exception){
            ResponseHandler.error("An error occurred wile deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR)
        }
}