package br.com.hardcoded.gildit.ui.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.hardcoded.gildit.R
import br.com.hardcoded.gildit.model.Thing

class LinksRecycleViewAdapter(val links: Array<Thing.Link>) : RecyclerView.Adapter<LinkViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LinkViewHolder(
      LayoutInflater.from(parent.context)
          .inflate(R.layout.list_row_submission, parent, false)
  )

  override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
    links[position].let {
      holder.submissionTitle.text = it.title
      holder.submissionAuthor.text = it.author
      holder.submissionSubreddit.text = it.subreddit
    }
  }

  override fun getItemCount() = links.size
}

class LinkViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

  val submissionTitle: TextView
  val submissionAuthor: TextView
  val submissionSubreddit: TextView

  init {
    submissionTitle = view.findViewById(R.id.submissionTitle) as TextView
    submissionAuthor = view.findViewById(R.id.submissionAuthor) as TextView
    submissionSubreddit = view.findViewById(R.id.submissionSubreddit) as TextView
  }
}
