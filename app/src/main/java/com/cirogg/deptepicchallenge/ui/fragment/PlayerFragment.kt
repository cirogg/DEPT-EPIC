package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.cirogg.deptepicchallenge.databinding.FragmentPlayerBinding
import com.cirogg.deptepicchallenge.model.UrlList
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifDrawable
import java.io.IOException


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
        lifecycleScope.launch {
            try {
                val gif = GifDrawable(urlList!!.byteArray)
                gifImageView.setImageDrawable(gif)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}