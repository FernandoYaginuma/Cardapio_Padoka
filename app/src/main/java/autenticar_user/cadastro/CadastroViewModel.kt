package autenticar_user.cadastro
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class CadastroUsuarioViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun verificarEmail(email: String) {
        this.auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (!signInMethods.isNullOrEmpty()) {
                        _status.value = "E-mail já cadastrado"
                    } else {
                        _status.value = "E-mail disponível"
                    }
                } else {
                    _status.value = "Erro ao verificar e-mail"
                }
            }
    }

    fun cadastrarUsuario(email: String, senha: String, confirmSenha: String) {
        if (senha != confirmSenha) {
            _status.value = "As senhas não coincidem"
            return
        }

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = "Cadastro realizado com sucesso"
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        _status.value = "E-mail já cadastrado"
                    } else {
                        _status.value = "Erro: ${exception?.message}"
                    }
                }
            }
    }
}