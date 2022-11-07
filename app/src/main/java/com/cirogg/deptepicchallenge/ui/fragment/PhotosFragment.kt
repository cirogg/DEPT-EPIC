package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.FragmentPhotosBinding
import com.cirogg.deptepicchallenge.model.ImagesList
import com.cirogg.deptepicchallenge.model.UrlList
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import com.cirogg.deptepicchallenge.ui.activity.MainActivity
import com.cirogg.deptepicchallenge.ui.viewmodel.DatesViewModel
import com.cirogg.deptepicchallenge.ui.viewmodel.DownloadViewModel
import com.cirogg.deptepicchallenge.utils.Const
import com.cirogg.deptepicchallenge.utils.Const.Companion.IMAGE_URL
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    private var imageList: ImagesList? = null
    private val args: PhotosFragmentArgs by navArgs()

    private val imagesAdapter by lazy {
        ImagesAdapter{ image -> seeImage(image) }
    }

    private var menuToolbar: Menu? = null

    private val viewModel by viewModel<DownloadViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        setToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setImages()
        initObservers()
        downloadAndSaveImage()
    }

    private fun initObservers(){
        viewModel.totalImagesCounter.observe(viewLifecycleOwner) {
            Log.d("SIZEEEEE", "$it listSize: ${imageList!!.imagesList!!.size}")
            if (it == imageList!!.imagesList!!.size){
                setPlayOnToolbar()
            }
        }
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

    private fun setPlayOnToolbar() {
        menuToolbar?.findItem(R.id.play)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.play -> {
                navigateToPlayer()
                true
            }
            else -> true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.info_image_menu, menu)
        menuToolbar = menu
        menu.findItem(R.id.showinfo).isVisible = false
        menu.findItem(R.id.play).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setToolbar() {
        with(requireActivity() as MainActivity) {
            this.setSupportActionBar(this.binding.toolbar)
        }
        setHasOptionsMenu(true)
    }

    private fun navigateToPlayer() {
        var listOfUrls = mutableListOf<String>()
        var urlList = UrlList(listOfUrls)
        imageList?.imagesList?.let {
            it.let {
                for (imagesResponse in it) {
                    listOfUrls.add("${IMAGE_URL}${imagesResponse?.getCleanDate()}/jpg/${imagesResponse?.image}.jpg")
                }
            }
        }
        val bundle = Bundle().apply {
            putParcelable("urlList", urlList)
        }
        findNavController().navigate(R.id.action_photosFragment_to_playerFragment, bundle)
    }

    private fun downloadAndSaveImage() {
        viewModel.downloadImage(imageList!!, requireContext())
    }


}