package View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardapio_padoka.databinding.ActivityItemDetalheBinding
import com.bumptech.glide.Glide

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


        itemImagemUrl?.let {
            Glide.with(this)
                .load(it)
                .into(binding.itemImage)
        }

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 100
        binding.numberPicker.value = 1

        binding.addToCartButton.setOnClickListener {
            val quantidade = binding.numberPicker.value
            adicionarAoPedido(itemNome, itemPreco, quantidade)
            val intent = Intent(this, CardapioActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, CardapioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun adicionarAoPedido(nome: String, preco: Double, quantidade: Int) {
        Toast.makeText(
            this,
            "$quantidade x \"$nome\" adicionado ao pedido por R$ %.2f".format(preco * quantidade),
            Toast.LENGTH_SHORT
        ).show()
    }
}
