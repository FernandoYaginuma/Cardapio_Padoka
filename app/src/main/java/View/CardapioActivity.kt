package View

import CardapioAdapter
import CardapioControler
import Item
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapio_padoka.databinding.ActivityCardapioBinding
import com.google.firebase.firestore.FirebaseFirestore

class CardapioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardapioBinding
    private lateinit var entradasAdapter: CardapioAdapter
    private lateinit var pratosPrincipaisAdapter: CardapioAdapter
    private lateinit var bebidasAdapter: CardapioAdapter
    private lateinit var sobremesasAdapter: CardapioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        entradasAdapter = CardapioAdapter(mutableListOf())
        pratosPrincipaisAdapter = CardapioAdapter(mutableListOf())
        bebidasAdapter = CardapioAdapter(mutableListOf())
        sobremesasAdapter = CardapioAdapter(mutableListOf())

        setupRecyclerView(binding.entradasList, entradasAdapter)
        setupRecyclerView(binding.pratosPrincipaisList, pratosPrincipaisAdapter)
        setupRecyclerView(binding.bebidasList, bebidasAdapter)
        setupRecyclerView(binding.sobremesasList, sobremesasAdapter)


        carregarDados()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: CardapioAdapter) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun carregarDados() {
        val db = FirebaseFirestore.getInstance()

        db.collection("itens")
            .whereEqualTo("categoria", "Entradas")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val entradas = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Item::class.java)
                }
                entradasAdapter.atualizarLista(entradas)
            }

        carregarCategoria("Pratos Principais", pratosPrincipaisAdapter)
        carregarCategoria("Bebidas", bebidasAdapter)
        carregarCategoria("Sobremesas", sobremesasAdapter)
    }

    private fun carregarCategoria(categoria: String, adapter: CardapioAdapter) {
        val db = FirebaseFirestore.getInstance()

        db.collection("itens")
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


