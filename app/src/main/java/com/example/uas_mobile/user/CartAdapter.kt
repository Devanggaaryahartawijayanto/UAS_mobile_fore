package com.example.uas_mobile.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.R
import com.example.uas_mobile.database.CoffeEntity
import com.example.uas_mobile.database.CoffeRoomDatabase
import com.example.uas_mobile.databinding.CoffeUserBinding
import com.example.uas_mobile.model.Coffe
import com.squareup.picasso.Picasso

class CartAdapter(
    private val listCoffee: List<Coffe>,
    private val database: CoffeRoomDatabase?
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CoffeUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CoffeEntity) {
            with(binding) {
                name.text = data.name
                description.text = data.description
                price.text = "Rp ${data.price}"

                // Menampilkan gambar menggunakan Picasso
                Picasso.get()
                    .load(data.imageURL)
                    .fit()
                    .centerCrop()
                    .into(gambar)

                // Periksa apakah item ada di keranjang
                database?.coffeDao()?.isCoffeeInCart(data.id)?.observeForever { isInCart ->
                    cart.setImageResource(
                        if (isInCart) R.drawable.ic_checklist else R.drawable.ic_add
                    )
                }

                // Tambah atau hapus dari keranjang
                cart.setOnClickListener {
                    Thread {
                        val dao = database?.coffeDao()
                        val coffeeInCart = dao?.getCoffeeById(data.id)
                        if (coffeeInCart != null) {
                            dao.delete(coffeeInCart)
                        } else {
                            val coffeeEntity = CoffeEntity(
                                id = data.id,
                                name = data.name,
                                description = data.description,
                                price = data.price,
                                imageURL = data.imageURL
                            )
                            dao?.insert(coffeeEntity)
                        }
                    }.start()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CoffeUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listCoffee.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coffe = listCoffee[position]
        val coffeeEntity = CoffeEntity(
            id = coffe.id,
            name = coffe.name,
            description = coffe.description,
            price = coffe.price,
            imageURL = coffe.imageURL
        )
        holder.bind(coffeeEntity)
    }

}
