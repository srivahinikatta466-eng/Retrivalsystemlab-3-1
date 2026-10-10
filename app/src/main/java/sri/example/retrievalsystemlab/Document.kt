package sri.example.retrievalsystemlab

data class Document(
    val title: String,
    val category: String,
    val content: String,
    var relevanceScore: Double = 0.0,
    var rank: Int = 0
)
