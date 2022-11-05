package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.FragmentPhotosBinding
import com.cirogg.deptepicchallenge.model.ImagesList
import com.cirogg.deptepicchallenge.model.response.ImagesResponse

class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    private var imageList: ImagesList? = null
    private val args: PhotosFragmentArgs by navArgs()

    private val imagesAdapter by lazy { ImagesAdapter{image->seeImage(image)} }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setImages()
    }

    private fun setUpRecyclerView() {
        binding.photosFragmentRecyclerView.apply {
            adapter = imagesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private fun setImages() {
        imageList = args.imageList
        imageList.let { imagesAdapter.diff.submitList(it?.imagesList) }
    }

    private fun seeImage(image: ImagesResponse) {
        val bundle = Bundle().apply {
            putParcelable("imageSingular", image)
        }
        findNavController().navigate(R.id.action_photosFragment_to_singularImageFragment, bundle)
    }

}