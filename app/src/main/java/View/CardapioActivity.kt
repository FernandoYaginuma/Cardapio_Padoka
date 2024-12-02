package View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardapio_padoka.R

class CardapioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTarefasBinding
    private lateinit var ctrl : AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTarefasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()
        binding.txtUsuarioAutenticado.text = ctrl.usuarioAutenticado()

        binding.txtSair.setOnClickListener {
            ctrl.logout()
            val it = Intent(
                this@TarefasActivity,
                MainActivity::class.java
            )
            startActivity(it)
        }

    }
}

