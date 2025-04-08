package com.api_authentication.api_authentication_and_authorization.services

import com.api_authentication.api_authentication_and_authorization.models.User
import com.api_authentication.api_authentication_and_authorization.models.UserDTO
import com.api_authentication.api_authentication_and_authorization.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

interface UserService {
    fun getAllUsers(): List<User>
    fun getUserById(id: String): User
    fun createUser(userDTO: UserDTO): User
    fun updateUser(id: String, userDTO: UserDTO): User
    fun deleteUser(id: String)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun getAllUsers(): List<User> =
        userRepository.findAll()

    override fun getUserById(id: String): User =
        userRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        }

    override fun createUser(userDTO: UserDTO): User {
        val user = User(
            name = userDTO.name,
            age = userDTO.age,
            address = userDTO.address
        )
        return userRepository.save(user)
    }

    override fun updateUser(id: String, userDTO: UserDTO): User {
        val user = userRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        }
        user.name = userDTO.name
        user.age = userDTO.age
        user.address = userDTO.address
        return userRepository.save(user)
    }

    override fun deleteUser(id: String) {
        val user = userRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        }
        userRepository.delete(user)
    }
}