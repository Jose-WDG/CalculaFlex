package br.com.careme.calculaflex.ui.singnup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.careme.calculaflex.model.NewUser
import br.com.careme.calculaflex.model.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SingUpViewModel: ViewModel() {
    private var auth = FirebaseAuth.getInstance()
    private val db =  FirebaseFirestore.getInstance()

    val singUpState = MutableLiveData<RequestState<FirebaseUser>>()


    fun singUp(newUser: NewUser){
        singUpState.value = RequestState.Loading

        if (validateFields(newUser)){
            auth.createUserWithEmailAndPassword(newUser.email ?: "",newUser.password ?: "")
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        saveFirestore(newUser)
                    }else{
                        singUpState.value = RequestState.Error(
                            Throwable(
                            it.exception?.message ?: "Não foi possivel criar o usuario"
                        )
                        )
                    }
                }
        }
    }

    private fun saveFirestore(newUser: NewUser) {
        db.collection("users")
            .document(auth.currentUser?.uid ?: "")
            .set(newUser)
            //.add(newUser)
            .addOnSuccessListener {
                sendEmailVerification()
            }.addOnFailureListener {
                singUpState.value = RequestState.Error(
                    Throwable(it.message)
                )
            }
    }

    private fun sendEmailVerification() {
        auth.currentUser?.let{ user ->
            user.sendEmailVerification().addOnCompleteListener {
                singUpState.value = RequestState.Success(user)
            }
        }
    }

    private fun validateFields(newUser: NewUser): Boolean {
        return true
    }
}