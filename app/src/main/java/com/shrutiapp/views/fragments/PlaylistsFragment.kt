package com.shrutiapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrutiapp.R
import com.shrutiapp.databinding.FragmentFoldersBinding
import com.shrutiapp.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {
    lateinit var binding: FragmentPlaylistsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

}