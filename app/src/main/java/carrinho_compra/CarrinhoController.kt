
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CarrinhoController {

    private val db = FirebaseFirestore.getInstance()

    fun listarPorIdUsuario(idUsuario: String?): Query {
        return db.collection("Usuario").whereEqualTo("uid", idUsuario)
    }

    fun deletar(documentId: String) {
        db.collection("Usuario").document(documentId).delete()
    }
}
