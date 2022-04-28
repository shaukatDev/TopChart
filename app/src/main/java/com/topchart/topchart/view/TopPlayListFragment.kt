package com.topchart.topchart.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mymusic.view.adapter.SongAdapter
import com.topchart.R
import com.topchart.databinding.FragmentTopPlaylistBinding
import com.topchart.topchart.Results
import com.topchart.topchart.TopSongs
import com.topchart.topchart.di.Injection
import com.topchart.topchart.view.listener.OnPaginationScrollListner
import com.topchart.topchart.view.listener.OnSongClickListener
import com.topchart.topchart.viewmodel.TopPlayListViewModel


class TopPlayListFragment : Fragment(), OnSongClickListener {
    private val TAG = "TopPlayListFragment"

    lateinit var binding: FragmentTopPlaylistBinding
    private lateinit var adapter: SongAdapter

    private var allSongs: ArrayList<Results> = ArrayList()
    private var rvDataSource: ArrayList<Results> = ArrayList()

    private val NUM_SONGS_PER_PAGE = 20
    private val PAGE_START = 1
    private var CURRENT_PAGE = PAGE_START
    private var TOTAL_PAGES = 3

    val viewModel by viewModels<TopPlayListViewModel> {
        Injection.provideViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopPlaylistBinding.inflate(inflater, container, false);

        setupRecyclerView()
        setupViewModel()

        return binding.root
    }


    /**
     * Setup Recycler view
     */
    private fun setupRecyclerView() {
        //set layout manager
        val numberOfColumns = 2
        val layoutManager = GridLayoutManager(activity, numberOfColumns)
        binding.recyclerView.setLayoutManager(layoutManager)

        //load more scroll to bottom
        // curretly splitting offline data into pages
        binding.recyclerView.addOnScrollListener(object : OnPaginationScrollListner(layoutManager) {
            override fun loadMoreSongs(fromIndex: Int) {
                CURRENT_PAGE++;
                Toast.makeText(activity, "Loading More...", Toast.LENGTH_SHORT).show()
                loadSongs(fromIndex)
            }

            override fun isLastPage(): Boolean {
                if (CURRENT_PAGE == TOTAL_PAGES) {
                    return true
                }
                return false
            }
        })

        //set adapter
        adapter = SongAdapter(viewModel.feed.value?.feed?.songs ?: emptyList(), this)
        binding.recyclerView.adapter = adapter
    }


    /**
     * Setup ViewModel
     */
    private fun setupViewModel() {
        viewModel.feed.observe(viewLifecycleOwner, renderFeed)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)

    }

    /**
     * Update recycleview datasource
     * @param fromIndex
     */
    private fun loadSongs(fromIndex: Int) {
        val lastIndex = allSongs.count()
        val toIndex = fromIndex + NUM_SONGS_PER_PAGE
        if (lastIndex > fromIndex) {
            if (toIndex > lastIndex) {
                rvDataSource.addAll(allSongs.subList(fromIndex, lastIndex))
            } else {
                rvDataSource.addAll(allSongs.subList(fromIndex, toIndex))
            }
            adapter.update(rvDataSource)
        }
    }

    /**
     * Observe feed
     */
    private val renderFeed = Observer<TopSongs> {
        Log.v(TAG, "data updated $it")
        binding.layoutError.root.visibility = View.GONE
        it?.feed?.songs?.let { it1 ->
            allSongs.addAll(it1)
            loadSongs(0)
        }
    }

    /**
     * Observe newtork request
     */
    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.progressBar.visibility = visibility
    }

    /**
     * Observe error message
     */
    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onError $it")
        binding.layoutError.root.visibility = View.VISIBLE
        binding.layoutError.textViewError.text = "Error $it"
    }


    /**
     * Add SongDetailFragment
     * @param url
     */
    private fun songDetail(url: String) {

        val bundle = Bundle()
        bundle.putString("url", url)

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val songDetailFragment = SongDetailFragment()
        songDetailFragment.arguments = bundle
        transaction?.add(R.id.container, songDetailFragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }


    override fun onSongClick(url: String) {
        songDetail(url)
    }


    /**
     * Clear recycle view datasoure
     * retrive data from repository
     */
    override fun onResume() {
        super.onResume()

        allSongs.clear()
        rvDataSource.clear()

        viewModel.loadFeed()

    }


    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }



}