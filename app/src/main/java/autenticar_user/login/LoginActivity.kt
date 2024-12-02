package autenticar_user.login
import autenticar_user.cadastro.CadastrarUsuarioActivity
import autenticar_user.redefinirSenha.RedefinirSenhaActivity
import View.CardapioActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cardapio_padoka.R

class LoginActivity : AppCompatActivity() {
    private lateinit var campEmail: EditText
    private lateinit var campPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var btnRecuperarSenha: Button
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        campEmail = findViewById(R.id.campEmail)
        campPassword = findViewById(R.id.campPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        btnRecuperarSenha = findViewById(R.id.btnRecuperarSenha)

        btnLogin.setOnClickListener {
            val email = campEmail.text.toString()
            val password = campPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, CadastrarUsuarioActivity::class.java)
            startActivity(intent)
        }

        btnRecuperarSenha.setOnClickListener {
            val intent = Intent(this, RedefinirSenhaActivity::class.java)
            startActivity(intent)
        }

        viewModel.loginStatus.observe(this, Observer { status ->
            if (status == "Login bem-sucedido") {
                val intent = Intent(this, CardapioActivity::class.java)
                startActivity(intent)
                finish() // Finaliza a atividade de login
            } else {
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
