package View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardapio_padoka.R
import com.example.cardapio_padoka.databinding.ActivityItemDetalheBinding

class ItemDetalheActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetalheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetalheBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val itemNome = intent.getStringExtra("itemNome") ?: "Item Desconhecido"
        val itemDescricao = intent.getStringExtra("itemDescricao") ?: "Sem descrição disponível"
        val itemPreco = intent.getDoubleExtra("itemPreco", 0.0)
        val itemImagemUrl = intent.getStringExtra("itemImagemUrl")


        binding.itemName.text = itemNome
        binding.itemDescription.text = itemDescricao
        binding.itemPrice.text = "R$ %.2f".format(itemPreco)


        binding.addToCartButton.setOnClickListener {
            adicionarAoPedido(itemNome, itemPreco)
        }
    }

    private fun adicionarAoPedido(nome: String, preco: Double) {

        Toast.makeText(
            this,
            "Item \"$nome\" adicionado ao pedido por R$ %.2f".format(preco),
            Toast.LENGTH_SHORT
        ).show()
    }
}
