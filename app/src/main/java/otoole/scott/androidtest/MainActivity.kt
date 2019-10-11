package otoole.scott.androidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import otoole.scott.androidtest.NetworkService.NetworkService

class MainActivity : AppCompatActivity() {

    lateinit var vm : MainActivityViewModel

    var books : List<BookModel>? = null

    lateinit var bookAdapter : BookListAdapter

    lateinit var repo : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //////////////////////////////
        //boilerplate for recycleview
        //////////////////////////////

        val recyclerView = findViewById<RecyclerView>(R.id.book_list_recycle_view)

        repo = NetworkService.getInstance()

        bookAdapter = BookListAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }

        ///////////////////////////////
        //setup vm with factory method
        ///////////////////////////////

        vm = ViewModelProviders
                .of(this, MainActivityViewModel.FACTORY(repo))
                .get(MainActivityViewModel::class.java)

        vm.bookList.observe(this, Observer {
            books = it
            updateListAndTotal(it)
        })

        book_list_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {

                books?.filter {
                    it.title.toString().contains(newText.toString())
                }.let {
                    updateListAndTotal(it)
                }

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun updateListAndTotal(bookList : List<BookModel>?) {
        item_count.text = getString(R.string.total, bookList?.size ?: "0")
        bookAdapter.updateList(bookList)
    }
}
