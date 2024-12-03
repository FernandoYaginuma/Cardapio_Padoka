import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CarrinhoController {

    private val db = FirebaseFirestore.getInstance()

    fun listarPorIdUsuario(idUsuario: String?): Query {
        return db.collection("Carrinho").whereEqualTo("idUsuario", idUsuario)
    }

    fun adicionarItem(idUsuario: String, item: Item) {
        val carrinhoItem = hashMapOf(
            "idUsuario" to idUsuario,
            "nome" to item.nome,
            "preco" to item.preco,
            "quantidade" to item.quantidade,
            "imageURL" to item.imageURL
        )

        db.collection("Carrinho").add(carrinhoItem)
    }

    fun deletar(documentId: String) {
        db.collection("Carrinho").document(documentId).delete()
    }
}
