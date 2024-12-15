package com.example.uas_mobile.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_mobile.R
import com.example.uas_mobile.database.CoffeEntity
import com.example.uas_mobile.database.CoffeRoomDatabase
import com.example.uas_mobile.model.Coffe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffeAdapter(
    private val dataCoffe: List<Coffe>?,
    private val database: CoffeRoomDatabase?, // Database untuk keranjang
    private val onItemLongClick: (Coffe) -> Unit
) : RecyclerView.Adapter<CoffeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.gambar)
        private val nameView: TextView = view.findViewById(R.id.name)
        private val descView: TextView = view.findViewById(R.id.description)
        private val priceView: TextView = view.findViewById(R.id.price)
        private val cartBtn: ImageView = view.findViewById(R.id.cart)

        fun bind(coffe: Coffe) {
            // Menggunakan Glide untuk memuat gambar
            Glide.with(itemView.context)
                .load(coffe.imageURL)
                .into(imageView)

            // Menampilkan informasi lainnya
            nameView.text = coffe.name
            descView.text = coffe.description
            priceView.text = "Rp ${coffe.price}"

            // Observasi untuk memeriksa apakah item ada di keranjang
            database?.coffeDao()?.isCoffeeInCart(coffe.id)?.observeForever { isInCart ->
                cartBtn.setImageResource(
                    if (isInCart) R.drawable.ic_checklist else R.drawable.ic_add
                )
            }

            // Menambahkan atau menghapus item dari keranjang
            cartBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = database?.coffeDao()
                    val existingCoffe = dao?.getCoffeeById(coffe.id)

                    if (existingCoffe != null) {
                        dao.delete(existingCoffe) // Hapus jika sudah ada di keranjang
                    } else {
                        val coffeEntity = CoffeEntity(
                            id = coffe.id,
                            name = coffe.name,
                            description = coffe.description,
                            price = coffe.price,
                            imageURL = coffe.imageURL
                        )
                        dao?.insert(coffeEntity) // Tambahkan jika belum ada
                    }
                }
            }

            // Event Long Click
            itemView.setOnLongClickListener {
                onItemLongClick(coffe)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coffe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataCoffe?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataCoffe?.get(position)?.let { holder.bind(it) }
    }
}
