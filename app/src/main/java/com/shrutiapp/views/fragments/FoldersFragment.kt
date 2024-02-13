package com.shrutiapp.views.fragments

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrutiapp.R
import com.shrutiapp.adapters.FoldersAdapter
import com.shrutiapp.data.Folders
import com.shrutiapp.data.Song
import com.shrutiapp.databinding.FragmentFoldersBinding
import com.shrutiapp.interfaces.RecyclerViewClickListener
import com.shrutiapp.utils.MyConstants
import java.io.File


class FoldersFragment : Fragment(), RecyclerViewClickListener {
    lateinit var binding: FragmentFoldersBinding

    private lateinit var adapter: FoldersAdapter
    private lateinit var songs: MutableList<Song>
    private lateinit var folders: MutableList<String>
    private lateinit var llm: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoldersBinding.inflate(inflater, container, false)

        initSetup()
        return binding.root
    }

    private fun initSetup() {
        folders = mutableListOf()
        songs = mutableListOf()
        llm = LinearLayoutManager(context)

        adapter = FoldersAdapter(folders, this)
        binding.foldersRecyclerView.adapter = adapter
        binding.foldersRecyclerView.layoutManager = llm


    }


    override fun onResume() {
        super.onResume()

        showFolders()
    }

    private fun showFolders() {
        songs = getFoldersFunc()
        Log.d("fadsfgfdg", "" + songs.size + "  " + folders.size);
        if (folders.size > 0) {
            adapter.notifyDataSetChanged()
//            binding.yesData.visibility = View.VISIBLE
//            binding.noData.visibility = View.GONE
        } else {
//            binding.yesData.visibility = View.GONE
//            binding.noData.visibility = View.VISIBLE
        }
    }

    private fun getFoldersFunc(): MutableList<Song> {
        val audioList: MutableList<Song> = mutableListOf()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToNext()) {
            do {
                // id ka column index nikaal lo and phir us index pe jo rkha he usko get kr lo cursor.getstring se
                val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                val displayName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                val size =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                val duration =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                val path =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                val dateAdded =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED))
                val audio = Song(id, title, displayName, size, duration, path, dateAdded)
                val file = File(path)
                val subString = file.parentFile?.name
                if (!folders.contains(subString)) {
                    if (subString != null) {
                        folders.add(subString)
                    }
                }
                audioList.add(audio)
            } while (cursor.moveToNext())
        }
        return audioList
    }


    override fun onClick(position: Int, type: String) {

        if (type.equals(MyConstants.FOLDERS)) {
            var bundle = Bundle()
            bundle.putString(MyConstants.FOLDER_NAME, folders[position])
            Toast.makeText(context, folders[position], Toast.LENGTH_SHORT).show()

        }

    }

}