package autenticar_user.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class CadastroUsuarioViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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

    fun cadastrarUsuario(nome: String, email: String, senha: String, confirmSenha: String) {
        if (senha != confirmSenha) {
            _status.value = "As senhas não coincidem"
            return
        }

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid ?: return@addOnCompleteListener
                    val carrinho = listOf(
                        hashMapOf(
                            "nomeItem" to "",
                            "quantidade" to 0,
                            "preco" to 0.0,
                            "valorTotal" to 0.0
                        )
                    )

                    val userMap = hashMapOf(
                        "nome" to nome,
                        "email" to email,
                        "senha" to senha,
                        "carrinho" to carrinho
                    )

                    firestore.collection("usuarios")
                        .document(userId)
                        .set(userMap)
                        .addOnSuccessListener {
                            _status.value = "Cadastro realizado com sucesso e salvo no Firestore"
                        }
                        .addOnFailureListener { exception ->
                            _status.value = "Erro ao salvar no Firestore: ${exception.message}"
                        }
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
