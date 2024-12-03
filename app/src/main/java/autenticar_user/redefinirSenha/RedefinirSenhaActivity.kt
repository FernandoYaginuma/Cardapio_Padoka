package autenticar_user.redefinirSenha
import autenticar_user.login.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cardapio_padoka.R

class RedefinirSenhaActivity : AppCompatActivity() {
    private lateinit var campEmail: EditText
    private lateinit var btnRecuperarSenha: Button
    private lateinit var btnVoltarLogin: Button
    private val viewModel: RecuperarSenhaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redefinir_senha)

        campEmail = findViewById(R.id.campEmail)
        btnRecuperarSenha = findViewById(R.id.btnRecuperarSenha)
        btnVoltarLogin = findViewById(R.id.btnVoltarLogin)

        btnRecuperarSenha.setOnClickListener {
            val email = campEmail.text.toString()
            if (email.isNotEmpty()) {
                viewModel.verificarEmail(email)
            } else {
                Toast.makeText(this, "Por favor, insira um e-mail.", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltarLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.status.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })
    }
}
