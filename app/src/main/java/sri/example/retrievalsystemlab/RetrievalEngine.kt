package sri.example.retrievalsystemlab

class RetrievalEngine {

    private val documents = listOf(

        Document(
            title = "Introduction to Machine Learning",
            category = "Artificial Intelligence",
            content = "Machine learning is a branch of artificial intelligence that enables computers to learn patterns from data and make predictions using algorithms."
        ),

        Document(
            title = "Deep Learning Fundamentals",
            category = "Artificial Intelligence",
            content = "Deep learning uses neural networks with multiple layers to process large amounts of data and solve complex machine learning problems."
        ),

        Document(
            title = "Natural Language Processing",
            category = "Artificial Intelligence",
            content = "Natural language processing enables computers to understand, process and generate human language using machine learning and artificial intelligence."
        ),

        Document(
            title = "Database Management Systems",
            category = "Database",
            content = "Database management systems are software applications used to store, organize, retrieve and manage structured data efficiently."
        ),

        Document(
            title = "Cloud Computing",
            category = "Cloud Technology",
            content = "Cloud computing provides computing resources such as storage, servers, databases and applications through the internet."
        ),

        Document(
            title = "Internet of Things",
            category = "IoT",
            content = "Internet of Things connects physical devices and sensors to the internet so that they can collect, exchange and analyze data."
        ),

        Document(
            title = "Data Science Fundamentals",
            category = "Data Science",
            content = "Data science combines statistics, programming and machine learning to analyze data and discover useful patterns and insights."
        ),

        Document(
            title = "Computer Networks",
            category = "Networking",
            content = "Computer networks allow devices to communicate and share information using communication protocols and network technologies."
        )
    )

    fun search(query: String): List<Document> {

        if (query.isBlank()) {
            return emptyList()
        }

        val queryWords = query
            .lowercase()
            .split("\\s+".toRegex())
            .filter { it.length > 1 }
            .toSet()

        if (queryWords.isEmpty()) {
            return emptyList()
        }

        val results = documents.map { document ->

            val titleWords = tokenize(document.title)
            val categoryWords = tokenize(document.category)
            val contentWords = tokenize(document.content)

            var score = 0.0

            for (word in queryWords) {

                if (titleWords.contains(word)) {
                    score += 40.0
                }

                if (categoryWords.contains(word)) {
                    score += 25.0
                }

                if (contentWords.contains(word)) {
                    score += 15.0
                }
            }

            val normalizedScore = score.coerceAtMost(100.0)

            document.copy(relevanceScore = normalizedScore)
        }
            .filter { it.relevanceScore > 0 }
            .sortedByDescending { it.relevanceScore }
            .take(10)

        return results.mapIndexed { index, document ->
            document.copy(rank = index + 1)
        }
    }

    private fun tokenize(text: String): Set<String> {
        return text
            .lowercase()
            .replace(Regex("[^a-z0-9 ]"), " ")
            .split("\\s+".toRegex())
            .filter { it.length > 1 }
            .toSet()
    }
}
