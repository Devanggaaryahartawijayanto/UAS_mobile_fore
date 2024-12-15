package com.example.uas_mobile.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas_mobile.R
import com.example.uas_mobile.database.CoffeRoomDatabase
import com.example.uas_mobile.databinding.FragmentUserHomeBinding
import com.example.uas_mobile.model.Coffe
import com.example.uas_mobile.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentUserHomeBinding? = null
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
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_home, container, false)
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCoffes()
    }

    private fun fetchCoffes() {
        apiService.getAllCoffe().enqueue(object : Callback<List<Coffe>> {
            override fun onResponse(call: Call<List<Coffe>>, response: Response<List<Coffe>>) {
                if (response.isSuccessful) {
                    val photos = response.body()
                    if (photos != null) {
                        setupRecyclerView(photos)
                    }
                }
            }

            override fun onFailure(call: Call<List<Coffe>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error : ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(photos : List<Coffe>) {
        binding.rvGambar.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGambar.adapter = CoffeUserAdapter(photos, CoffeRoomDatabase.getDatabase(requireContext()))
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}