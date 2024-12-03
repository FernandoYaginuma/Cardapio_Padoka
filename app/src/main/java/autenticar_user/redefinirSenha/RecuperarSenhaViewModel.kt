package autenticar_user.redefinirSenha

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class RecuperarSenhaViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    fun verificarEmail(email: String) {
        if (!isValidEmail(email)) {
            _status.value = "Por favor, insira um e-mail válido."
            return
        }

        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    Log.d("RecuperarSenhaViewModel", "Métodos de autenticação: $signInMethods")
                    if (!signInMethods.isNullOrEmpty()) {
                        redefinirSenha(email)
                    } else {
                        _status.value = "E-mail não cadastrado. Por favor, cadastre-se."
                    }
                } else {
                    _status.value = "Erro ao verificar e-mail: ${task.exception?.message}"
                    Log.e("RecuperarSenhaViewModel", "Erro ao verificar e-mail", task.exception)
                }
            }
    }

    private fun redefinirSenha(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = "Instruções de redefinição de senha enviadas para $email"
                } else {
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> "E-mail não encontrado."
                        else -> "Erro ao enviar e-mail de redefinição: ${task.exception?.message}"
                    }
                    _status.value = errorMessage
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
