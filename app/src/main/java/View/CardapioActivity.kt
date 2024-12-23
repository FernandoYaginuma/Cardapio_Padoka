package View

import CardapioAdapter
import Item
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import autenticar_user.login.LoginActivity
import carrinho_compra.CarrinhoActivity
import com.example.cardapio_padoka.databinding.ActivityCardapioBinding
import com.google.firebase.firestore.FirebaseFirestore

class CardapioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardapioBinding
    private lateinit var bebidasquentesAdapter: CardapioAdapter
    private lateinit var bebidasgeladasAdapter: CardapioAdapter
    private lateinit var lanchesAdapter: CardapioAdapter
    private lateinit var salgadosAdapter: CardapioAdapter
    private lateinit var docesAdapter: CardapioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bebidasquentesAdapter = CardapioAdapter(mutableListOf())
        bebidasgeladasAdapter = CardapioAdapter(mutableListOf())
        lanchesAdapter = CardapioAdapter(mutableListOf())
        salgadosAdapter = CardapioAdapter(mutableListOf())
        docesAdapter = CardapioAdapter(mutableListOf())

        setupRecyclerView(binding.bebidasquentesList, bebidasquentesAdapter)
        setupRecyclerView(binding.bebidasgeladasList, bebidasgeladasAdapter)
        setupRecyclerView(binding.lanchesList, lanchesAdapter)
        setupRecyclerView(binding.salgadosList, salgadosAdapter)
        setupRecyclerView(binding.docesList, docesAdapter)

        carregarDados()

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCarrinho.setOnClickListener {
            val intent = Intent(this, CarrinhoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: CardapioAdapter) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun carregarDados() {
        carregarCategoria("bebidasquentes", bebidasquentesAdapter)
        carregarCategoria("bebidasgeladas", bebidasgeladasAdapter)
        carregarCategoria("lanches", lanchesAdapter)
        carregarCategoria("salgados", salgadosAdapter)
        carregarCategoria("doces", docesAdapter)
    }

    private fun carregarCategoria(categoria: String, adapter: CardapioAdapter) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Item")
            .whereEqualTo("categoria", categoria)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val itens = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Item::class.java)
                }
                adapter.atualizarLista(itens)
            }
    }
}