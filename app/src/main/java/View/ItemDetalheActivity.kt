package View

import CarrinhoController
import Item
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardapio_padoka.databinding.ActivityItemDetalheBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth


class ItemDetalheActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetalheBinding
    private val carrinhoController = CarrinhoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetalheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemNome = intent.getStringExtra("itemNome") ?: "Item Desconhecido"
        val itemDescricao = intent.getStringExtra("itemDescricao") ?: "Sem descrição disponível"
        val itemPreco = intent.getDoubleExtra("itemPreco", 0.0)
        val itemImagemUrl = intent.getStringExtra("itemImagemUrl")
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "ID do usuário não encontrado"

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
            val item = Item(quantidade, null, itemNome, itemDescricao, itemPreco, itemImagemUrl, null)
            adicionarAoPedido(userId, item)
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

    private fun adicionarAoPedido(idUsuario: String, item: Item) {
        carrinhoController.adicionarItem(idUsuario, item)
        Toast.makeText(
            this,
            "${item.quantidade} x \"${item.nome}\" adicionado ao pedido por R$ %.2f".format(item.preco!! * item.quantidade!!),
            Toast.LENGTH_SHORT
        ).show()
    }
}