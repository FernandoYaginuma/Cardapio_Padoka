import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CarrinhoController {

    private val db = FirebaseFirestore.getInstance()

    fun listarPorIdUsuario(idUsuario: String?): Query {
        return db.collection("usuarios").document(idUsuario!!)
            .collection("carrinho")
    }

    fun adicionarItem(idUsuario: String, item: Item) {
        val carrinhoItem = hashMapOf(
            "nomeItem" to item.nome,
            "descricao" to item.descricao,
            "preco" to item.preco,
            "quantidade" to item.quantidade,
            "imageURL" to item.imageURL,
            "categoria" to item.categoria
        )

        db.collection("usuarios").document(idUsuario)
            .collection("carrinho").add(carrinhoItem)
            .addOnSuccessListener { documentReference ->
                println("Item adicionado ao carrinho com ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Erro ao adicionar item ao carrinho: $e")
            }
    }

    fun deletar(idUsuario: String, documentId: String) {
        db.collection("usuarios").document(idUsuario)
            .collection("carrinho").document(documentId).delete()
            .addOnSuccessListener {
                println("Item removido do carrinho com ID: $documentId")
            }
            .addOnFailureListener { e ->
                println("Erro ao remover item do carrinho: $e")
            }
    }
}