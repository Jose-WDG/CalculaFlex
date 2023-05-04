package br.com.careme.calculaflex.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.careme.calculaflex.model.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel: ViewModel() {
    private var auth = FirebaseAuth.getInstance()

    val loginState = MutableLiveData<RequestState<FirebaseUser>>()

    val resetPager = MutableLiveData<RequestState<String>>()

    fun singIn(email: String, password: String){
        loginState.value = RequestState.Loading

        if (validateFields(email,password)){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            loginState.value = RequestState.Success(auth.currentUser!!)
                        }else{
                            loginState.value = RequestState.Error(Throwable(it.exception?.message ?: "Não foi possivel realizar o login"))
                        }
                    }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        if (email.isEmpty()){
            loginState.value = RequestState.Error(throwable = Throwable("E-mail não pode ser vazio"))
            return false
        }

        if (password.isEmpty()){
            loginState.value = RequestState.Error(throwable = Throwable("Senha não pode ser vazia"))
            return false
        }

        if (password.length < 6){
            loginState.value = RequestState.Error(throwable = Throwable("Senha deve ter pelo menos 6 caracteres"))
            return false
        }

        return true
    }
}