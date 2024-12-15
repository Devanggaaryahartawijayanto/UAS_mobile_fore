package com.example.uas_mobile.admin

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.FragmentEditCoffeBinding
import com.example.uas_mobile.network.ApiClient
import com.example.uas_mobile.request.CoffeRequest
import com.example.uas_mobile.response.CoffeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCoffeFragment : Fragment() {

    private var _binding: FragmentEditCoffeBinding? = null
    private val binding get() = _binding!!

    private val apiService = ApiClient.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCoffeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data yang dikirim melalui arguments
        val coffeeId = arguments?.getString("id")
        val coffeeName = arguments?.getString("title")
        val coffeeDescription = arguments?.getString("description")
        val coffeeImageUrl = arguments?.getString("imageUrl")
        val coffeePrice = arguments?.getString("price")?.toIntOrNull()

        // Tampilkan data di form edit
        with(binding) {
            title.text = Editable.Factory.getInstance().newEditable(coffeeName)
            description.text = Editable.Factory.getInstance().newEditable(coffeeDescription)
            imageUrl.text = Editable.Factory.getInstance().newEditable(coffeeImageUrl)
            price.text = Editable.Factory.getInstance().newEditable(coffeePrice?.toString())
        }

        binding.updateCoffe.setOnClickListener {
            if (validateInputs()) {
                coffeeId?.let {
                    val updatedRequest = CoffeRequest(
                        name = binding.title.text.toString(),
                        description = binding.description.text.toString(),
                        imageURL = binding.imageUrl.text.toString(),
                        price = binding.price.text.toString().toInt()
                    )
                    updateCoffee(it, updatedRequest)
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val name = binding.title.text.toString()
        val description = binding.description.text.toString()
        val imageUrl = binding.imageUrl.text.toString()
        val price = binding.price.text.toString().toIntOrNull()

        return name.isNotEmpty() && description.isNotEmpty() && imageUrl.isNotEmpty() && price != null && price > 0
    }

    private fun updateCoffee(id: String, request: CoffeRequest) {
        apiService.updateCoffe(id, request).enqueue(object : Callback<CoffeResponse> {
            override fun onResponse(call: Call<CoffeResponse>, response: Response<CoffeResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Coffee updated successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeAdminFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed to update coffee", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CoffeResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
