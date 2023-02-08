package com.example.leafapp.homefragments.allFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leafapp.R
import com.example.leafapp.adapters.PostAdapter
import com.example.leafapp.databinding.FragmentAllBinding
import com.example.leafapp.dataclass.PostClass
import com.shashank.sony.fancytoastlib.FancyToast


class AllFragment : Fragment() {


    lateinit var bunding: FragmentAllBinding

    var posts: MutableList<PostClass> = ArrayList()

    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bunding = FragmentAllBinding.inflate(layoutInflater)

        bunding.lifecycleOwner = this

        bunding.allFragmentViewModel = viewModel
        /* viewModel.currPost.observe(viewLifecycleOwner, Observer {
             it?.let {
                 this.findNavController()
                     .navigate(AllFragmentDirections.actionAllFragmentToDetalsFragment())
                 viewModel.endNav()
             }
         })
         val adapter = PostAdapter(PostAdapter.OnClickListener{
             viewModel.startNav(it)
         })*/


        bunding.allRC.layoutManager = LinearLayoutManager(this.requireContext());

        /*if (viewModel.allPosts.value != null) FancyToast.makeText(
            this.requireContext(),
            viewModel.allPosts.value!!.get(0).title,
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            true
        ).show()
        else FancyToast.makeText(
            this.requireContext(), "Notheing", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true
        ).show()*/

        /*adapter.submitList(posts)
        bunding.allRC.adapter = adapter*/
        bunding.allRC.adapter = viewModel.allPosts.value?.let { PostAdapter(it) }


        return bunding.root
    }


}