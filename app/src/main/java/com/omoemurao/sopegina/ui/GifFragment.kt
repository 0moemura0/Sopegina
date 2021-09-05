package com.omoemurao.sopegina.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omoemurao.sopegina.GifTypes
import com.omoemurao.sopegina.MainViewModel
import com.omoemurao.sopegina.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifFragment : Fragment(R.layout.fragment_gif) {

    private var type: GifTypes? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getParcelable(ARG_PARAM_TYPE)
        }
        viewModel.setType(type)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image: ImageView = view.findViewById(R.id.image)
        val previous: ImageButton = view.findViewById(R.id.left)
        val next: ImageButton = view.findViewById(R.id.right)

        val title: TextView = view.findViewById(R.id.tv_title)
        val votes: TextView = view.findViewById(R.id.tv_votes)
        val author: TextView = view.findViewById(R.id.tv_author)
        val icFavorite: ImageView = view.findViewById(R.id.iv_favorite)
        val progressBar: ProgressBar = view.findViewById(R.id.image_progress)

        previous.isEnabled = false
        previous.isClickable = false

        previous.setOnClickListener {
            viewModel.gifQueueId.value = viewModel.gifQueueId.value?.dec()
            viewModel.getNextGif()
        }
        next.setOnClickListener {
            viewModel.gifQueueId.value = viewModel.gifQueueId.value?.inc()
            viewModel.getNextGif()
        }

        viewModel.gifQueueId.observe(viewLifecycleOwner, {
            previous.isEnabled = it != 0
            previous.isClickable = it != 0
        })

        viewModel.gifCurrent.observe(viewLifecycleOwner, { it ->
            it.handle {
                success { gif ->
                    image.visibility = View.VISIBLE
                    votes.visibility = View.VISIBLE
                    icFavorite.visibility = View.VISIBLE
                    author.visibility = View.VISIBLE
                    next.isClickable = true
                    next.isEnabled = true
                    val imageUri = gif.gifURL?.toUri()
                    Glide.with(requireContext())
                        .asGif()
                        .listener(object : RequestListener<GifDrawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<GifDrawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return true
                            }

                            override fun onResourceReady(
                                resource: GifDrawable?,
                                model: Any?,
                                target: Target<GifDrawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }
                        })
                        .load(imageUri)
                        .into(image)

                    title.text = gif.description
                    votes.text = gif.votes.toString()
                    author.text = gif.author
                }
                loading {
                    icFavorite.visibility = View.GONE
                    votes.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    image.visibility = View.GONE
                    title.text = ""
                    votes.text = ""
                    author.text = ""
                }
                error { msg, _ ->
                    Log.e("TAG", msg)
                    Toast.makeText(view.context, msg, Toast.LENGTH_SHORT).show()
                    icFavorite.visibility = View.GONE
                    votes.visibility = View.GONE
                    image.visibility = View.VISIBLE
                    next.isClickable = false
                    next.isEnabled = false
                    progressBar.visibility = View.GONE
                    Glide
                        .with(view.context)
                        .load(R.drawable.ic_error)
                        .centerCrop()
                        .into(image)
                    title.text = ""
                    votes.text = ""
                    author.text = ""
                }
            }
        })

    }

    companion object {
        private const val ARG_PARAM_TYPE = "type"

        @JvmStatic
        fun newInstance(param1: GifTypes) =
            GifFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_TYPE, param1)
                }
            }
    }
}