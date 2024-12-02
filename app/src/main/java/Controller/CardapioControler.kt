
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CardapioControler {

    private val db = FirebaseFirestore.getInstance()

    fun adicionar(Item: Item, onResult: (Boolean, String?) -> Unit) {

        val collectionRef = db.collection("Itens")

        collectionRef.add(Item).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null)
            } else {
                val errorMessage = task.exception?.message
                onResult(false, errorMessage)
            }
        }
    }

    fun listarPorIdUsuario(idUsuario: String?): Query {
        return db.collection("Itens").whereEqualTo("iid", idUsuario)
    }

    fun deletar(documentId: String) {
        db.collection("Itens").document(documentId).delete()
    }

}
