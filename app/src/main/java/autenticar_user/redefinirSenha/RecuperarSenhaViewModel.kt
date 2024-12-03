package autenticar_user.redefinirSenha

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore

class RecuperarSenhaViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    fun verificarEmail(inputEmail: String) {
        if (!isValidEmail(inputEmail)) {
            _status.value = "Por favor, insira um e-mail válido."
            return
        }

        val currentUser = auth.currentUser
        if (currentUser == null) {
            _status.value = "Usuário não autenticado."
            return
        }

        firestore.collection("usuarios")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val emailCadastrado = document.getString("email")

                    if (emailCadastrado == inputEmail) {
                        redefinirSenha(inputEmail)
                    } else {
                        _status.value = "O e-mail não corresponde ao cadastrado."
                    }
                } else {
                    _status.value = "Documento do usuário não encontrado."
                }
            }
            .addOnFailureListener { exception ->
                _status.value = "Erro ao buscar documento: ${exception.message}"
                Log.e("RecuperarSenhaViewModel", "Erro ao buscar documento", exception)
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