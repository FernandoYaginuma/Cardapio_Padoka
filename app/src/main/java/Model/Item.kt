class Item {
    var id: String? = null
    var nome: String? = null
    var descricao: String? = null
    var preco: Double? = null
    var imageURL: String? = null
    var categoria: String? = null

    constructor()
    constructor(id: String?, nome: String?, descricao: String?, preco: Double?, imageURL: String?, categoria: String?) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.preco = preco
        this.imageURL = imageURL
        this.categoria = categoria
    }
}
