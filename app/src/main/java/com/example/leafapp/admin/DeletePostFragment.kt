package com.example.leafapp.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.leafapp.AddPostFragmentDirections
import com.example.leafapp.R
import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.databinding.FragmentDeletePostBinding
import com.example.leafapp.databinding.FragmentDetalsBinding
import com.example.leafapp.databinding.FragmentUserHomeBinding
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.notification.MyFirebaseMessagingService
import com.example.leafapp.ui.home.AllFragmentViewModel
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homemenus.CurrItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shashank.sony.fancytoastlib.FancyToast

class DeletePostFragment : Fragment() {

    companion object {
        fun newInstance() = DeletePostFragment()
    }

    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    private val deleteViewModel: DeletePostViewModel by lazy {
        ViewModelProvider(this).get(DeletePostViewModel::class.java)
    }
    private lateinit var binding:FragmentDeletePostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDeletePostBinding.inflate(layoutInflater)
        CurrItem.deleteEnable = true
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(DeletePostFragmentDirections.actionDeletePostFragmentToDetalsFragment(it))
        },viewModel,deleteViewModel)
        var posts = ArrayList<PostClass>()

        deleteViewModel.deletePostRes.observe(viewLifecycleOwner, Observer {
            it?.let {
                FancyToast.makeText(requireContext(),"Post has been deleted successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()
            }
        })

        Firebase.firestore.collection("Posts")
            .addSnapshotListener{value, _ ->
                posts.clear()
                for (document in value!!)
                {
                    var p = PostClass(
                        document.getString("title")!!,
                        document.getString("photo")!!,
                        document.getString("type")!!,
                        "",
                        document.getString("contents")!!,
                        document.id
                    )
                    posts.add(p)
                }
                adapter.notifyDataSetChanged()
            }
        adapter.submitList(posts)
        binding.deletePostRc.adapter = adapter
        return binding.root
    }



}