package com.cirogg.deptepicchallenge.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.FragmentDateListBinding
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.ui.viewmodel.DatesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DateListFragment : Fragment() {

    private var _binding: FragmentDateListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<DatesViewModel>()

    private val datesAdapter by lazy { DatesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        binding.datesListRecyclerView.apply {
            adapter = datesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initObservers() {
        viewModel.dates.observe(viewLifecycleOwner) { r ->
            r.let { response ->
                if (response != null) {
                    datesAdapter.setData(response)
                    binding.progressBarDates.visibility = View.GONE
                    viewModel.getImagesByDate()
                }
            }
        }

        viewModel.datesLoad?.observe(viewLifecycleOwner) { r ->
            r.date.let { date ->
                updateListOfDates(r)
                binding.progressBarDates.visibility = View.GONE

            }
        }
    }

    private fun updateListOfDates(currentDate: DatesResponse){
        val fullDateList = viewModel.dates.value
        fullDateList.let {
            val i = fullDateList?.withIndex()?.find { currentDate.date == it.value.date }?.index
            i?.let {
                fullDateList[i].apply {
                    downStatus = currentDate.downStatus
                    dateImages = currentDate.dateImages
                }
                datesAdapter.setData(fullDateList)
            }
        }
    }

}