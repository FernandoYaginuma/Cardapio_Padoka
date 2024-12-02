package autenticar_user.redefinirSenha
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RecuperarSenhaViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    fun verificarEmail(email: String) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (!signInMethods.isNullOrEmpty()) {
                        redefinirSenha(email)
                    } else {
                        _status.value = "E-mail não cadastrado. Por favor, cadastre-se."
                    }
                } else {
                    _status.value = "Erro ao verificar e-mail."
                }
            }
    }

    private fun redefinirSenha(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = "Instruções de redefinição de senha enviadas para $email"
                } else {
                    _status.value = "Erro ao enviar e-mail de redefinição."
                }
            }
    }
}
