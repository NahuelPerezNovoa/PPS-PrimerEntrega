package com.example.pps_primerentrega.utils

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

object FirebaseAuthErrorHandler {

    fun getErrorMessage(exception: Exception): String {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> "El correo electrónico o la contraseña son inválidos."
            is FirebaseAuthUserCollisionException -> "Ya existe una cuenta registrada con este correo."
            is FirebaseAuthWeakPasswordException -> "La contraseña es demasiado débil."
            is FirebaseAuthInvalidUserException -> "No se encontró un usuario con el correo ingresado."
            is FirebaseAuthRecentLoginRequiredException -> "Se requiere haber iniciado sesión recientemente para realizar esta operación."
            is FirebaseNetworkException -> "Hubo un problema con la conexión a Internet."
            else -> "Ocurrió un error inesperado."
        }
    }
}