package com.fgsguedes.gildit.ui.activity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fgsguedes.gildit.R
import com.fgsguedes.gildit.model.Thing

class LinksRecycleViewAdapter(private val context: Context, private val links: Array<Thing.Link>) : RecyclerView.Adapter<LinkViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LinkViewHolder(
      LayoutInflater.from(parent.context)
          .inflate(R.layout.list_row_submission, parent, false)
  )

  override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
    links[position].apply {
      holder.lintTitle.text = title
      holder.linkSubmissionDetails.text = context.getString(R.string.linkSubmissionDetails, author, subreddit)
    }
  }

  override fun getItemCount() = links.size
}

class LinkViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

  val lintTitle = view.findViewById(R.id.linkTitle) as TextView
  val linkSubmissionDetails = view.findViewById(R.id.linkSubmissionDetails) as TextView
}
