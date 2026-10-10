package sri.example.retrievalsystemlab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class RetrievalAdapter(
    private var documents: List<Document>
) : RecyclerView.Adapter<RetrievalAdapter.DocumentViewHolder>() {

    class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDocumentTitle: TextView =
            itemView.findViewById(R.id.tvDocumentTitle)

        val tvScore: TextView =
            itemView.findViewById(R.id.tvScore)

        val tvCategory: TextView =
            itemView.findViewById(R.id.tvCategory)

        val tvPreview: TextView =
            itemView.findViewById(R.id.tvPreview)

        val tvRank: TextView =
            itemView.findViewById(R.id.tvRank)

        val tvRelevance: TextView =
            itemView.findViewById(R.id.tvRelevance)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocumentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_document, parent, false)

        return DocumentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DocumentViewHolder,
        position: Int
    ) {

        val document = documents[position]

        holder.tvDocumentTitle.text = document.title
        holder.tvCategory.text = document.category
        holder.tvPreview.text = document.content

        holder.tvScore.text = String.format(
            Locale.US,
            "%.0f%%",
            document.relevanceScore
        )

        holder.tvRank.text = "Rank #${document.rank}"

        holder.tvRelevance.text = when {
            document.relevanceScore >= 75 -> "Highly relevant"
            document.relevanceScore >= 45 -> "Relevant"
            else -> "Partially relevant"
        }
    }

    override fun getItemCount(): Int {
        return documents.size
    }

    fun updateDocuments(newDocuments: List<Document>) {
        documents = newDocuments
        notifyDataSetChanged()
    }
}
