package carrinho_compra

import Item
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapio_padoka.databinding.ActivityItemCarrinhoBinding

class CarrinhoAdapter(
    private val itensCarrinho: MutableList<Item>,
    private val atualizarTotal: () -> Unit,
    private val removerItem: (Item) -> Unit
) : RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder>() {

    class CarrinhoViewHolder(val binding: ActivityItemCarrinhoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrinhoViewHolder {
        val binding = ActivityItemCarrinhoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarrinhoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarrinhoViewHolder, position: Int) {
        val item = itensCarrinho[position]

        holder.binding.nomeItem.text = item.nome
        holder.binding.precoItem.text = "R$ %.2f".format(item.preco)
        holder.binding.quantidadeItem.text = item.quantidade.toString()

        holder.binding.btnAdicionarItem.setOnClickListener {
            item.quantidade = item.quantidade!! + 1
            notifyItemChanged(position)
            atualizarTotal()
        }

        holder.binding.btnRemoveItem.setOnClickListener {
            if (item.quantidade!! > 1) {
                item.quantidade = item.quantidade!! - 1
                notifyItemChanged(position)
                atualizarTotal()
            } else {
                Toast.makeText(holder.itemView.context, "Quantidade mínima é 1", Toast.LENGTH_SHORT).show()
            }
        }

        holder.binding.btnLixeira.setOnClickListener {
            removerItem(item)
        }

        //  a imagem do item

    }

    override fun getItemCount(): Int {
        return itensCarrinho.size
    }


}