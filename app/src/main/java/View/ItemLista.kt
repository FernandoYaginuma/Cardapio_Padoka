package View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardapio_padoka.R
import com.example.cardapio_padoka.databinding.ActivityItemDetalheBinding

class ItemLista : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetalheBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityItemDetalheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDetal.setOnClickListener {
            val intent = Intent(this, ItemDetalheActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}