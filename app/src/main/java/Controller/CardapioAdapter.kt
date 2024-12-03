import View.ItemDetalheActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardapio_padoka.databinding.ActivityItemListaBinding

class CardapioAdapter(private val listaItens: MutableList<Item>) :
    RecyclerView.Adapter<CardapioAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ActivityItemListaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ActivityItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (getItemCount() > 0) {
            val item = listaItens[position]
            holder.binding.txtTitulo.text = item.nome
            holder.binding.txtDescricao.text = item.descricao
            holder.binding.txtPreco.text = "R$ %.2f".format(item.preco)

            Glide.with(holder.itemView.context)
                .load(item.imageURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.imgItem)

            holder.binding.btnDetal.setOnClickListener {
                val intent = Intent(holder.itemView.context, ItemDetalheActivity::class.java).apply {
                    putExtra("itemNome", item.nome)
                    putExtra("itemDescricao", item.descricao)
                    putExtra("itemPreco", item.preco)
                    putExtra("itemImagemUrl", item.imageURL)
                }
                holder.itemView.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount() = listaItens.size

    fun atualizarLista(novosItens: List<Item>) {
        listaItens.clear()
        listaItens.addAll(novosItens)
        notifyDataSetChanged()
    }
}
