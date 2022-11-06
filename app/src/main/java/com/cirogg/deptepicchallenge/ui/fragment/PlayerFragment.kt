package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.FragmentPlayerBinding
import com.cirogg.deptepicchallenge.model.UrlList
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerFragment : Fragment() {


    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val args: PlayerFragmentArgs by navArgs()

    private var urlList: UrlList? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlList = args.urlList
        //Picasso.get().load(urlList!!.listOfUrls[0]).into(binding.imageView)
        lifecycleScope.launch {
            makeSequence()
        }

    }

    private suspend fun makeSequence() {
        var counter = urlList!!.listOfUrls.size

        while (counter > 0) {
            for (listOfUrl in urlList!!.listOfUrls) {
                counter--
                Picasso.get().load(listOfUrl).into(binding.imageView)
                if (counter == 0) {
                    counter = urlList!!.listOfUrls.size
                }
                delay(500L)
            }
        }

    }

}