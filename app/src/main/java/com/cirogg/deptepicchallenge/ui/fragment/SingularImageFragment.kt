package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.FragmentSingularImageBinding
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import com.cirogg.deptepicchallenge.utils.Const.Companion.IMAGE_URL
import com.squareup.picasso.Picasso

class SingularImageFragment : Fragment() {

    private var _binding: FragmentSingularImageBinding? = null
    private val binding get() = _binding!!

    private var imageSingular: ImagesResponse? = null
    private val args: SingularImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSingularImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
    }

    private fun setImage() {
        imageSingular = args.imageSingular
        imageSingular.let {
            Picasso.get()
                .load("$IMAGE_URL${it?.getCleanDate()}/jpg/${imageSingular?.image}.jpg")
                .placeholder(R.drawable.ic_palceholder)
                .into(binding.imageView)
        }
    }

}