package com.example.waygo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waygo.data.local.entity.AccessLogEntity
import com.example.waygo.domain.model.User
import com.example.waygo.domain.repository.AccessLogRepository
import com.example.waygo.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accessLogRepository: AccessLogRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val TAG = "AuthViewModel"
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private var _currentUser: User? = null
    private val _userState = MutableLiveData<User?>()
    val userState: LiveData<User?> = _userState

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }


    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
        // [END send_email_verification]
    }

    fun checkAuthStatus(){
        if(auth.currentUser==null){
            _authState.value = AuthState.Unauthenticated
        }else{
            viewModelScope.launch {
                auth.currentUser?.uid?.let { uid ->
                    _currentUser = userRepository.getUserById(uid)
                    _userState.value = _currentUser
                }
                _authState.value = AuthState.Authenticated
            }
        }
    }

    fun login(email : String,password : String){

        //https://firebase.google.com/docs/auth/web/manage-users?hl=es-419
//        val user = auth.currentUser
//
//        user?.let {
//            // Name, email address, and profile photo Url
//            val name = it.displayName
//            val email = it.email
//            val photoUrl = it.photoUrl
//
//            // Check if user's email is verified
//            val emailVerified = it.isEmailVerified
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            val uid = it.uid
//        }

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }


        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
//                if (task.isSuccessful){
//                    _authState.value = AuthState.Authenticated
//                }else{
//                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
//                }

                if (task.isSuccessful) {

                    val user = auth.currentUser

                    if (user != null && user.isEmailVerified) {
                        _authState.value = AuthState.Authenticated
                        viewModelScope.launch {
                            _currentUser = userRepository.getUserById(user.uid)
                            _userState.value = _currentUser
                            val accessLog = AccessLogEntity(
                                userId = user.uid.hashCode(),
                                action = "Login Successful",
                                timestamp = System.currentTimeMillis()
                            )
                            accessLogRepository.insertAccessLog(accessLog)
                        }
                    } else {
                        _authState.value = AuthState.Error("Email address not verified. Please check your email before continuing.")
                        sendEmailVerification()
                        auth.signOut() // opcional: cerrar sesión si no está verificado
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signup(email : String,
               password : String,
               username: String,
               name: String,
               phoneNumber: String,
               birthdate: String,
               address: String,
               country: String,
               acceptEmails: Boolean
    ){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    if (user != null) {
                        val newUser = User(
                            id = 0, // id autogenerado en Room
                            userId = user.uid,
                            username = username,
                            name = name,
                            email = email,
                            phoneNumber = phoneNumber,
                            birthdate = birthdate.toLongOrNull() ?: 0L,
                            address = address,
                            country = country,
                            acceptEmails = acceptEmails
                        )

                        viewModelScope.launch {
                            userRepository.insertUser(newUser)
                            val accessLog = AccessLogEntity(
                                userId = user.uid.hashCode(),
                                action = "Signup Successful",
                                timestamp = System.currentTimeMillis()
                            )
                            accessLogRepository.insertAccessLog(accessLog)
                        }
                    }
                    sendEmailVerification()
                    auth.signOut()
                    _authState.value = AuthState.EmailVerificationSent("Please, confirm your email")
//                    _authState.value = AuthState.Authenticated
                    //sendEmailVerification()
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                val accessLog = AccessLogEntity(
                    userId = user.uid.hashCode(),
                    action = "Logout",
                    timestamp = System.currentTimeMillis()
                )
                accessLogRepository.insertAccessLog(accessLog)
            }
        }
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
    fun resetAuthState() {
        _authState.value = AuthState.Unauthenticated
    }


}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
    data class EmailVerificationSent(val message: String) : AuthState()
}