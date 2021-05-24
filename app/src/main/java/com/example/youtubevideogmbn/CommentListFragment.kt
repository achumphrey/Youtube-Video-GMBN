package com.example.youtubevideogmbn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 */
class CommentListFragment : Fragment() {

    private var commentList = arrayListOf<String>()
    lateinit var arrayAdapter: ArrayAdapter<*>
    lateinit var commentListView: ListView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coment_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        makeCommentList()

        val view = view

        commentListView = view?.findViewById(R.id.commentList)!!

        arrayAdapter = ArrayAdapter(
            requireContext().applicationContext,
            android.R.layout.simple_list_item_1,
            commentList
        )
        commentListView.adapter = arrayAdapter

        commentListView.setOnItemClickListener {
                _,
                _,
                position,
                _ ->
            Toast.makeText(
                requireContext().applicationContext,
                commentList[position],
                Toast.LENGTH_SHORT).show()

        }
    }

    private fun makeCommentList() {
        for (i in 1..10)
            commentList.add("Comment $i by Author $i")
    }
}