package View

import Controller.CarrinhoAdapter
import Item
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapio_padoka.databinding.ActivityCarrinhoBinding
import com.google.firebase.firestore.FirebaseFirestore

class CarrinhoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarrinhoBinding
    private lateinit var adapter: CarrinhoAdapter
    private lateinit var firestore: FirebaseFirestore
    private val listaItensCarrinho = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarrinhoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        adapter = CarrinhoAdapter(listaItensCarrinho, ::atualizarTotal, ::removerItem)
        binding.carrinhotRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.carrinhotRecyclerView.adapter = adapter

        carregarItensCarrinho()

        binding.btnConfirmar.setOnClickListener {
            finalizarCompra()
        }
    }

    private fun carregarItensCarrinho() {
        firestore.collection("carrinho")
            .get()
            .addOnSuccessListener { documentos ->
                listaItensCarrinho.clear()
                for (documento in documentos) {
                    val item = documento.toObject(Item::class.java)
                    item.id = documento.id
                    listaItensCarrinho.add(item)
                }
                adapter.notifyDataSetChanged()
                atualizarTotal()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar carrinho: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun atualizarTotal() {
        val total = listaItensCarrinho.sumOf { it.quantidade?.times(it.preco!!) ?: 0.0 }
        binding.precoTotal.text = "Total: R$ %.2f".format(total)
    }


    private fun removerItem(item: Item) {
        item.id?.let {
            firestore.collection("carrinho").document(it)
                .delete()
                .addOnSuccessListener {
                    listaItensCarrinho.remove(item)
                    adapter.notifyDataSetChanged()
                    atualizarTotal()
                    Toast.makeText(this, "Item removido do carrinho", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Erro ao remover item: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun finalizarCompra() {
        if (listaItensCarrinho.isEmpty()) {
            Toast.makeText(this, "Carrinho vazio. Adicione itens antes de finalizar.", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("pedidos").add(mapOf("itens" to listaItensCarrinho))
            .addOnSuccessListener {
                listaItensCarrinho.clear()
                adapter.notifyDataSetChanged()
                atualizarTotal()
                Toast.makeText(this, "Compra finalizada com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao finalizar compra: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}