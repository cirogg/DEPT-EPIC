package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.FragmentSingularImageBinding
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import com.cirogg.deptepicchallenge.ui.activity.MainActivity
import com.cirogg.deptepicchallenge.utils.Const.Companion.IMAGE_URL
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        setToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
    }

    private fun showImageInfo() {
        MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setMessage(imageSingular.toString())
            .show()
    }

    private fun setToolbar() {
        with(requireActivity() as MainActivity) {
            this.setSupportActionBar(this.binding.toolbar)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.info_image_menu, menu)
        menu.findItem(R.id.showinfo).isVisible = true
        menu.findItem(R.id.play).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.showinfo -> {
                showImageInfo()
                true
            }
            else -> true
        }
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