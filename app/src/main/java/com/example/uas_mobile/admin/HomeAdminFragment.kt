package com.example.uas_mobile.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas_mobile.R
import com.example.uas_mobile.database.CoffeRoomDatabase
import com.example.uas_mobile.databinding.FragmentHomeAdminBinding
import com.example.uas_mobile.model.Coffe
import com.example.uas_mobile.network.ApiClient
import com.example.uas_mobile.response.CoffeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeAdminFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private var apiService = ApiClient.getInstance()

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
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCoffes() // Mengambil data Coffe dari API
    }

    private fun fetchCoffes() {
        apiService.getAllCoffe().enqueue(object : Callback<List<Coffe>> {
            override fun onResponse(call: Call<List<Coffe>>, response: Response<List<Coffe>>) {
                if (response.isSuccessful) {
                    val coffes = response.body()
                    if (!coffes.isNullOrEmpty()) {
                        setupRecyclerView(coffes)
                    } else {
                        Toast.makeText(requireContext(), "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Coffe>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(coffes: List<Coffe>) {
        val database = CoffeRoomDatabase.getDatabase(requireContext()) // Ambil instance database
        binding.rvGambar.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGambar.adapter = CoffeAdapter(coffes, database) { coffe ->
            deleteCoffe(coffe)
        }
    }


    private fun deleteCoffe(coffe: Coffe) {
        apiService.deleteCoffe(coffe.id.toString()).enqueue(object : Callback<CoffeResponse> {
            override fun onResponse(call: Call<CoffeResponse>, response: Response<CoffeResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) { // Sesuaikan dengan struktur `CoffeResponse`
                        Toast.makeText(requireContext(), "Coffee deleted successfully", Toast.LENGTH_SHORT).show()
                        fetchCoffes() // Refresh list setelah penghapusan
                    } else {
                        Toast.makeText(requireContext(), "Failed to delete coffee: ${body?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to delete coffee", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CoffeResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeAdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}