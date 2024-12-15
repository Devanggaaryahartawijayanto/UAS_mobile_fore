package com.example.uas_mobile.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas_mobile.database.CoffeRoomDatabase
import com.example.uas_mobile.databinding.FragmentUserCartBinding
import com.example.uas_mobile.model.Coffe

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserCartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentUserCartBinding? = null
    private val binding get() = _binding!!
    private var database : CoffeRoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_bookmark, container, false)
        _binding = FragmentUserCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = CoffeRoomDatabase.getDatabase(requireContext())

        binding.rvGambar.layoutManager = GridLayoutManager(requireContext(), 2)

        database?.coffeDao()?.getAllCoffe()?.observe(viewLifecycleOwner) { coffeEntities ->
            // Mengonversi CoffeEntity menjadi Coffe
            val fav = coffeEntities.map { coffeEntity ->
                Coffe(
                    id = coffeEntity.id, // Menggunakan id dari CoffeEntity
                    name = coffeEntity.name, // Menggunakan name dari CoffeEntity
                    description = coffeEntity.description, // Menggunakan description dari CoffeEntity
                    price = coffeEntity.price, // Menggunakan price dari CoffeEntity
                    imageURL = coffeEntity.imageURL // Menggunakan imageUrl dari CoffeEntity
                )
            }

            // Setelah konversi, menggunakan Coffe dalam adapter
            val adapter = CartAdapter(fav, database)
            binding.rvGambar.adapter = adapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserCartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserCartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}