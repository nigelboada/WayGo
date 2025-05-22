package com.example.waygo.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.waygo.R
import com.example.waygo.utils.FormValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    var phoneNumber by mutableStateOf("")

    var address by mutableStateOf("")

    var country by mutableStateOf("")

    // Campos del formulario
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var storeName by mutableStateOf("")
        private set

    var storeLocation by mutableStateOf("")
        private set

    var mobile by mutableStateOf("")
        private set

    var pinCode by mutableStateOf("")
        private set

    // Mensajes de error (null si no hay error)
    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    var storeNameError by mutableStateOf<String?>(null)
        private set

    var storeLocationError by mutableStateOf<String?>(null)
        private set

    var mobileError by mutableStateOf<String?>(null)
        private set

    var pinCodeError by mutableStateOf<String?>(null)
        private set

    var birthdate by mutableStateOf("")
        private set

    var acceptEmails by mutableStateOf(false)
        private set


    // ======================
    //  Setters de cada campo
    // ======================
    fun onEmailChanged(newValue: String) {
        email = newValue
        Log.d("RegisterViewModel", "Email cambiado: $newValue")
    }

    fun onPasswordChanged(newValue: String) {
        password = newValue
        Log.d("RegisterViewModel", "Contraseña cambiada: $newValue")
    }

    fun onConfirmPasswordChanged(newValue: String) {
        confirmPassword = newValue
        Log.d("RegisterViewModel", "Confirmación de contraseña cambiada: $newValue")
    }

    fun onStoreNameChanged(newValue: String) {
        storeName = newValue
        Log.d("RegisterViewModel", "Nombre de la tienda cambiado: $newValue")
    }

    fun onStoreLocationChanged(newValue: String) {
        storeLocation = newValue
        Log.d("RegisterViewModel", "Ubicación de la tienda cambiada: $newValue")
    }

    fun onMobileChanged(newValue: String) {
        mobile = newValue
        Log.d("RegisterViewModel", "Número móvil cambiado: $newValue")
    }

    fun onPinCodeChanged(newValue: String) {
        pinCode = newValue
        Log.d("RegisterViewModel", "Código PIN cambiado: $newValue")
    }

    // Llamado desde la UI al presionar "Registrar"
    fun onRegisterClicked(): Boolean {
        val allValid = validateAllFields()

        // Log de la validación de campos
        Log.d("RegisterViewModel", "Intentando registrar al usuario...")
        if (allValid) {
            // Mostrar Toast
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
            Log.d("RegisterViewModel", "Registro exitoso")
            return true
        } else {
            Log.d("RegisterViewModel", "Registro fallido, hay errores en el formulario")
        }

        return false
    }

    fun onPhoneNumberChanged(newPhone: String) { phoneNumber = newPhone }

    fun onAddressChanged(newAddress: String) { address = newAddress }

    fun onCountryChanged(newCountry: String) { country = newCountry }

    fun onBirthdateChanged(newValue: String) { birthdate = newValue }

    fun onAcceptEmailsChanged(newValue: Boolean) { acceptEmails = newValue }

    // ==================================
    //  Lógica para validar todos los campos
    // ==================================
    fun validateAllFields(): Boolean {
        val isEmailValid = FormValidationUtils.validateUserEmail(email)
        val isPasswordValid = FormValidationUtils.validatePassword(password)
        val isConfirmPasswordValid = FormValidationUtils.validateConfirmationPassword(password, confirmPassword)
        val isStoreNameValid = FormValidationUtils.validateStoreName(storeName)
        val isStoreLocationValid = true
        val isMobileValid = true
        val isPinValid = true

        // Logs para las validaciones de cada campo
        Log.d("RegisterViewModel", "🔍 Validación de Campos:")
        Log.d("RegisterViewModel", "📧 Email: $email → ¿Válido? $isEmailValid")
        Log.d("RegisterViewModel", "🔑 Password: $password → ¿Válido? $isPasswordValid")
        Log.d("RegisterViewModel", "🔁 Confirm Password: $confirmPassword → ¿Válido? $isConfirmPasswordValid")
        Log.d("RegisterViewModel", "🏪 Store Name: $storeName → ¿Válido? $isStoreNameValid")
        Log.d("RegisterViewModel", "📍 Store Location: $storeLocation → ¿Válido? $isStoreLocationValid")
        Log.d("RegisterViewModel", "📱 Mobile: $mobile → ¿Válido? $isMobileValid")
        Log.d("RegisterViewModel", "📌 Pin Code: $pinCode → ¿Válido? $isPinValid")

        // Asignamos el recurso que corresponda según cada validación
        emailError = if (!isEmailValid) context.getString(R.string.error_invalid_email) else null
        passwordError = if (!isPasswordValid) context.getString(R.string.error_password_too_short) else null
        confirmPasswordError = if (!isConfirmPasswordValid) context.getString(R.string.error_password_mismatch) else null
        storeNameError = if (!isStoreNameValid) context.getString(R.string.error_required_field) else null
        storeLocationError = if (!isStoreLocationValid) context.getString(R.string.error_required_field) else null
        mobileError = if (!isMobileValid) context.getString(R.string.error_mobile_digits) else null
        pinCodeError = if (!isPinValid) context.getString(R.string.error_pin_digits) else null

        // Devuelve true si todo es válido
        return isEmailValid && isPasswordValid && isConfirmPasswordValid &&
                isStoreNameValid && isStoreLocationValid &&
                isMobileValid && isPinValid
    }

    fun clearFields() {
        storeName = ""
        email = ""
        password = ""
        confirmPassword = ""
        phoneNumber = ""
        address = ""
        country = ""
        birthdate = ""
        acceptEmails = false
    }

}
