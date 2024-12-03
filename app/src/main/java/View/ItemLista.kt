package View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cardapio_padoka.databinding.ActivityItemListaBinding

class ItemLista : AppCompatActivity() {
    private lateinit var binding: ActivityItemListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando o clique do botão para iniciar ItemDetalheActivity
        binding.btnDetal.setOnClickListener {
            val intent = Intent(this, ItemDetalheActivity::class.java)
            startActivity(intent)
        }
    }
}
