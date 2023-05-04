package br.com.careme.calculaflex.model

import com.google.firebase.firestore.Exclude

data class NewUser(
    val userName:String? = null,
    val email:String? = null,
    val phone: String? = null,
    @get:Exclude val password: String? = null
)