package com.example.mendeleevapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mendeleevapp.databinding.FragmentMainBinding
import com.example.mendeleevapp.domain.model.Element
import com.example.mendeleevapp.ui.model.PeriodGroup
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val adapter = ElementsAdapter {
        navigateToCurrentStock(it)
    }
    private val model: MainFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide
            .with(binding.root)
            .load("https://purepng.com/public/uploads/large/periodic-table-bee.png")
            .into(binding.ivTable)
        initChipGroup()
        setupChipGroupListener()
        observeElements()
        observeViewModel()
        searchViewListener()
        initRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun initChipGroup() {
        binding.chipGroupView.isHorizontalScrollBarEnabled = false

        val periodGroups = getPeriodGroups() // Получить список периодов и их групп из вашей модели

        for (periodGroup in periodGroups) {
            val chip = Chip(binding.chipGroup.context)
            chip.text = periodGroup.periodName // Установить текст чипа как имя периода
            chip.isClickable = true
            chip.isCheckable = true
            chip.tag = periodGroup.groupNumber // Сохранить номер группы в свойстве tag чипа

            binding.chipGroup.addView(chip)
        }
    }

    private fun searchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                model.searchElements(newText)
                return true
            }
        })
    }

    private fun setupChipGroupListener() {
        binding.chipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            if (checkedId == View.NO_ID) {
                showAllElements()
            } else {
                val selectedChip = chipGroup.findViewById<Chip>(checkedId)
                val selectedGroupNumber = selectedChip?.tag as? Int
                model.filterElementsByGroup(selectedGroupNumber)
            }
        }
    }

    private fun observeNoChipSelected() {
        model.filterElementsByGroup(null)
    }

    private fun showAllElements() {
        binding.chipGroup.clearCheck()
        observeNoChipSelected()
    }

    private fun observeElements() {
        model.elements.observe(viewLifecycleOwner) { elements ->
            adapter.submitList(elements)
        }
    }

    private fun observeViewModel() {
        model.elements.observe(viewLifecycleOwner) { elements ->
            adapter.submitList(elements)
        }
    }

    private fun initRecyclerView() {
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(context)
    }

    private fun navigateToCurrentStock(element: Element) {
        val action = MainFragmentDirections.actionMainFragmentToElementFragment(element)
        findNavController().navigate(action)
    }

    private fun getPeriodGroups(): List<PeriodGroup> {
        return listOf(
            PeriodGroup("1 период", 1),
            PeriodGroup("2 период", 2),
            PeriodGroup("3 период", 3),
            PeriodGroup("4 период", 4),
            PeriodGroup("5 период", 5),
            PeriodGroup("6 период", 6),
            PeriodGroup("7 период", 7)
        )
    }
}