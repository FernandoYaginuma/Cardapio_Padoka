package autenticar_user.cadastro
import autenticar_user.login.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cardapio_padoka.databinding.ActivityCadastrarUsuarioBinding

class CadastrarUsuarioActivity : AppCompatActivity() {
    private val viewModel: CadastroUsuarioViewModel by viewModels()
    private lateinit var binding: ActivityCadastrarUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewBinding
        binding = ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.campEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = binding.campEmail.text.toString()
                viewModel.verificarEmail(email)
            }
        }

        binding.btnCadastrar.setOnClickListener {
            val email = binding.campEmail.text.toString()
            val senha = binding.campPassword.text.toString()
            val confirmSenha = binding.campConfirmPassword.text.toString()
            viewModel.cadastrarUsuario(email, senha, confirmSenha)
        }

        // Navegação de volta para a tela de login
        binding.btnVoltarLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Opcional: finaliza a atividade atual
        }

        viewModel.status.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })
    }
}
