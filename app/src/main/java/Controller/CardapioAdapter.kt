import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapio_padoka.databinding.ItemListaBinding


class CardapioAdapter(private val listaItens: MutableList<Item>) :
    RecyclerView.Adapter<CardapioAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemListaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (getItemCount() > 0 ){
            val item = listaItens[position]
            holder.binding.txtTitulo.text = item.nome
            holder.binding.txtDescricao.text = item.descricao
            holder.binding.txtPreco.text = "R$ %.2f".format(item.preco)
            }
        }

    override fun getItemCount() = listaItens.size

    fun atualizarLista(novasTarefas: List<Item>) {
        listaItens.clear()
        listaItens.addAll(novasTarefas)
        notifyDataSetChanged()
    }
    }

