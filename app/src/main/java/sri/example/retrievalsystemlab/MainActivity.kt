package sri.example.retrievalsystemlab

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etQuery: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnClear: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvResultInfo: TextView
    private lateinit var tvResultCount: TextView

    // emptyState is a LinearLayout in XML,
    // so we use View here.
    private lateinit var emptyState: View

    private lateinit var retrievalAdapter: RetrievalAdapter
    private lateinit var retrievalEngine: RetrievalEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initializeViews()
        setupRecyclerView()
        setupRetrievalEngine()
        setupListeners()
    }

    private fun initializeViews() {

        etQuery = findViewById(R.id.etQuery)

        btnSearch = findViewById(R.id.btnSearch)

        btnClear = findViewById(R.id.btnClear)

        recyclerView = findViewById(R.id.recyclerView)

        tvResultInfo = findViewById(R.id.tvResultInfo)

        tvResultCount = findViewById(R.id.tvResultCount)

        emptyState = findViewById(R.id.emptyState)
    }

    private fun setupRecyclerView() {

        retrievalAdapter = RetrievalAdapter(emptyList())

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = retrievalAdapter
        }
    }

    private fun setupRetrievalEngine() {

        retrievalEngine = RetrievalEngine()
    }

    private fun setupListeners() {

        // Search button
        btnSearch.setOnClickListener {
            performSearch()
        }

        // Clear button
        btnClear.setOnClickListener {
            clearSearch()
        }

        // Search using keyboard
        etQuery.setOnEditorActionListener { _, _, _ ->
            performSearch()
            true
        }
    }

    private fun performSearch() {

        val query = etQuery.text.toString().trim()

        // If query is empty
        if (query.isEmpty()) {

            showEmptyState(
                "Please enter a search query."
            )

            return
        }

        // Search documents
        val results = retrievalEngine.search(query)

        // Update RecyclerView
        retrievalAdapter.updateDocuments(results)

        // Show results
        if (results.isEmpty()) {

            tvResultInfo.text = "No relevant documents found"

            tvResultCount.text = "0 Results"

            recyclerView.visibility = View.GONE

            emptyState.visibility = View.VISIBLE

        } else {

            tvResultInfo.text = "Ranked results for \"$query\""

            tvResultCount.text = "${results.size} Results"

            recyclerView.visibility = View.VISIBLE

            emptyState.visibility = View.GONE
        }
    }

    private fun clearSearch() {

        // Clear search box
        etQuery.text.clear()

        // Clear results
        retrievalAdapter.updateDocuments(emptyList())

        // Reset information
        tvResultInfo.text = "Enter a query to retrieve documents"

        tvResultCount.text = "0 Results"

        // Show empty state
        recyclerView.visibility = View.GONE

        emptyState.visibility = View.VISIBLE
    }

    private fun showEmptyState(message: String) {

        tvResultInfo.text = message

        tvResultCount.text = "0 Results"

        retrievalAdapter.updateDocuments(emptyList())

        recyclerView.visibility = View.GONE

        emptyState.visibility = View.VISIBLE
    }
}
