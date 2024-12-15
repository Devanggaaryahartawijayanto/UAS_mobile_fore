package com.example.uas_mobile.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.FragmentAddCoffeBinding
import com.example.uas_mobile.network.ApiClient
import com.example.uas_mobile.request.CoffeRequest
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
 * Use the [AddCoffeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCoffeFragment : Fragment() {

    private var _binding: FragmentAddCoffeBinding? = null
    private val binding get() = _binding!!

    private val apiService = ApiClient.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCoffeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            addPhoto.setOnClickListener {
                val name = name.text.toString()
                val description = description.text.toString()
                val imageUrl = imageUrl.text.toString()
                val price = price.text.toString()

                if (name.isNotEmpty() && description.isNotEmpty() && imageUrl.isNotEmpty() && price.isNotEmpty()) {
                    val request = CoffeRequest(
                        name = name,
                        description = description,
                        imageURL = imageUrl,
                        price = price.toInt()
                    )
                    addCoffee(request)
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addCoffee(request: CoffeRequest) {
        apiService.createCoffe(request).enqueue(object : Callback<CoffeResponse> {
            override fun onResponse(call: Call<CoffeResponse>, response: Response<CoffeResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Coffee added successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeAdminFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed to add coffee", Toast.LENGTH_SHORT).show()
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