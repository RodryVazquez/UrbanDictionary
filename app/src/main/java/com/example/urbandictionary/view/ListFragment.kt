package com.example.urbandictionary.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment(), ShareItemClickListener {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()

    private val itemListAdapter = ItemListAdapter(this)

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        binding.inputSearchItem.addTextChangedListener { afterTextChanged ->
            val query = afterTextChanged.toString()
            if(query.isNotEmpty()){
                viewModel.searchUrbanItems(query)
            }
        }

        binding.listUrbanItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemListAdapter
        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.action_sorting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.thumbUp -> {
                val data = itemListAdapter.currentList.sortedByDescending { t -> t.thumbsUp }
                itemListAdapter.submitList(data)
                itemListAdapter.notifyDataSetChanged()
                true
            }

            R.id.thumbDown -> {
                val data = itemListAdapter.currentList.sortedByDescending { t-> t.thumbsDown }
                itemListAdapter.submitList(data)
                itemListAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel.itemList.observe(viewLifecycleOwner, Observer { items ->
            items?.let {
                binding.listUrbanItems.visibility = View.VISIBLE
                itemListAdapter.submitList(items)
            }
        })

        viewModel.loadError.observe(viewLifecycleOwner, Observer { isError ->



            isError?.let {
                binding.errorLegend.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.progressTask.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.errorLegend.visibility = View.GONE
                    binding.listUrbanItems.visibility = View.GONE
                }
            }
        })
    }

    override fun shareItemListener(itemCard: ItemCard) {
        val sendIntent = Intent().apply {
            putExtra(Intent.EXTRA_TEXT, itemCard.permalink)
            action = Intent.ACTION_SEND
            type = "text/plain"
        }
        startActivity(sendIntent)

    }
}