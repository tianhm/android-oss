package com.kickstarter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.kickstarter.R
import com.kickstarter.databinding.ItemCommentCardBinding
import com.kickstarter.databinding.ItemShowMoreRepliesBinding
import com.kickstarter.models.Comment
import com.kickstarter.ui.data.CommentCardData
import com.kickstarter.ui.viewholders.CommentCardViewHolder
import com.kickstarter.ui.viewholders.KSViewHolder
import com.kickstarter.ui.viewholders.PaginationViewMoreRepliesViewHolder
import com.kickstarter.ui.viewholders.RootCommentViewHolder

class RepliesAdapter(private val delegate: Delegate) : KSListAdapter() {
    interface Delegate :
        CommentCardViewHolder.Delegate,
        PaginationViewMoreRepliesViewHolder.ViewListener

    init {
        insertSection(SECTION_COMMENTS, emptyList<CommentCardData>())
        insertSection(SECTION_SHOW_MORE_REPLIES_PAGINATING, emptyList<Boolean>())
        insertSection(SECTION_ROOT_COMMENT, emptyList<Comment>())
    }

    fun takeData(replies: List<CommentCardData>, shouldViewMoreRepliesCell: Boolean,) {
        if (replies.isNotEmpty()) {
            setSection(SECTION_COMMENTS, replies)
            setSection(SECTION_SHOW_MORE_REPLIES_PAGINATING, listOf(shouldViewMoreRepliesCell))
        }
        submitList(items())
    }

    fun updateRootCommentCell(rootComment: Comment) {
        setSection(SECTION_ROOT_COMMENT, listOf(rootComment))
        submitList(items())
    }

    override fun layout(sectionRow: SectionRow): Int = when (sectionRow.section()) {
        SECTION_COMMENTS -> R.layout.item_comment_card
        SECTION_SHOW_MORE_REPLIES_PAGINATING -> R.layout.item_show_more_replies
        else -> 0
    }

    override fun viewHolder(@LayoutRes layout: Int, viewGroup: ViewGroup): KSViewHolder {
        return when (layout) {
            R.layout.item_comment_card -> CommentCardViewHolder(ItemCommentCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false), delegate, true)
            R.layout.item_show_more_replies -> PaginationViewMoreRepliesViewHolder(
                ItemShowMoreRepliesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false), delegate
            )
            else -> RootCommentViewHolder(ItemCommentCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
        }
    }

    companion object {
        private const val SECTION_COMMENTS = 0
        private const val SECTION_SHOW_MORE_REPLIES_PAGINATING = 1
        private const val SECTION_ROOT_COMMENT = 2
    }
}