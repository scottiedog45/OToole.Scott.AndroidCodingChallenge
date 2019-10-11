package otoole.scott.androidtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookListAdapter(books : List<BookModel>? = null) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private var receivedBooks = books

    fun updateList(books: List<BookModel>?) {
        receivedBooks = books
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.text_view_for_book_cell_title)
        val author = itemView.findViewById<TextView>(R.id.text_view_for_book_cell_author)
        val image = itemView.findViewById<ImageView>(R.id.image_view_for_book_cell)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.book_cell, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() : Int = receivedBooks?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val book = receivedBooks?.get(position)

        if (book != null) {

            Glide
                .with(holder.image.context)
                .load(book.imageURL)
                .error(R.color.design_default_color_error)
                .dontTransform()
                .fallback(R.drawable.ic_library_books_black_24dp)
                .placeholder(R.drawable.ic_library_books_black_24dp)
                .into(holder.image)

            holder.apply {
                title.text = book.title
                author.text = book.author
            }
        }
    }
}