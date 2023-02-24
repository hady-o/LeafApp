package com.example.leafapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.FragmentAddPostBinding
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin

class AddPostFragment : Fragment() {

    companion object {
        fun newInstance() = AddPostFragment()
    }

    private lateinit var viewModel: AddPostViewModel
    private lateinit var binding:FragmentAddPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)


        val markwon: Markwon = Markwon.builder(requireContext()) // automatically create Glide instance
            .usePlugin(ImagesPlugin.create())
            .usePlugin(GlideImagesPlugin.create(requireContext())) // use supplied Glide instance
            .usePlugin(GlideImagesPlugin.create(Glide.with(requireContext())))
            .usePlugin(HtmlPlugin.create())
            .build()

// set markdown
        markwon.setMarkdown(binding.markdownTxt, "# Advanced-Android-Track-SubProjects\n\n <img src=\"https://article.picturethisai.com/wp-content/uploads/2021/07/1-13.jpg?w=620\" width=\"100%\" style=\"border-radius:50%\">\n# Repository for The Sup-Projects for the Egypt-FWD (Egypt Future Work is Digital) for Advanced Android Track With kotlin language\n\n<br>\n\n# Part 1:\n## Lesson #1\n * [Dice Roller App](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%231/Lesson%20%231/DiceRoller)\n## Lesson #2\n * [About Me App](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%231/Lesson%20%232/AboutMeApp)\n * [Color My View](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%231/Lesson%20%232/ColorMyView)\n## Lesson #3\n * [Trivial App](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%231/Lesson%20%233/TrivialApp)\n## Lesson #4\n * [DessertPusher](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%231/Lesson%20%234/DessertPusher)\n## Lesson #5\n * [Guess It](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%231/Lesson%20%235/GuessIt)\n\n<br>\n\n# Part 2: \n## Lesson #1\n * [Sleep Tracker](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%232/Lesson%231/SleepTracker)\n\n## Lesson #2\n * [SleepTrackerWithRecyclerview](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%232/Lesson%232/SleepTrackerWithRecyclerview)\n\n## Lesson #3\n * [MarsRealEstate](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%232/Lesson%233/MarsRealEstate)\n\n## Lesson #4\n * [DevBytes](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%232/Lesson%234/DevBytes)\n\n## Lesson #5\n * [DGD](https://github.com/HadyAhmed00/Advanced-Android-Track-SubProjects/tree/main/Part%232/Lesson%235/DGD)\n")

        return binding.root
    }



}