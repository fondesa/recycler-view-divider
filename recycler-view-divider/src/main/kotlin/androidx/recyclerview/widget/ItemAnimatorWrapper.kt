package androidx.recyclerview.widget

import com.fondesa.recyclerviewdivider.log.logWarning

public class ItemAnimatorWrapper(private val originalAnimator: RecyclerView.ItemAnimator) : RecyclerView.ItemAnimator() {
    override fun getMoveDuration(): Long = originalAnimator.moveDuration

    override fun setMoveDuration(moveDuration: Long) {
        originalAnimator.moveDuration = moveDuration
    }

    override fun getAddDuration(): Long = originalAnimator.addDuration

    override fun setAddDuration(addDuration: Long) {
        originalAnimator.addDuration = addDuration
    }

    override fun getRemoveDuration(): Long = originalAnimator.removeDuration

    override fun setRemoveDuration(removeDuration: Long) {
        originalAnimator.removeDuration = removeDuration
    }

    override fun getChangeDuration(): Long = originalAnimator.changeDuration

    override fun setChangeDuration(changeDuration: Long) {
        originalAnimator.changeDuration = changeDuration
    }

    internal override fun setListener(listener: ItemAnimatorListener?) {
        originalAnimator.setListener(listener)
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo = originalAnimator.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)

    override fun recordPostLayoutInformation(state: RecyclerView.State, viewHolder: RecyclerView.ViewHolder): ItemHolderInfo =
        originalAnimator.recordPostLayoutInformation(state, viewHolder)

    override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
        logWarning("LYRA animation finished")
        originalAnimator.onAnimationFinished(viewHolder)

        // TODO

        viewHolder.itemView.post { viewHolder.itemView.requestLayout() }
    }

    override fun onAnimationStarted(viewHolder: RecyclerView.ViewHolder) {
        originalAnimator.onAnimationStarted(viewHolder)
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean =
        originalAnimator.canReuseUpdatedViewHolder(viewHolder)

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>): Boolean =
        originalAnimator.canReuseUpdatedViewHolder(viewHolder, payloads)

    override fun obtainHolderInfo(): ItemHolderInfo = originalAnimator.obtainHolderInfo()

    override fun animateDisappearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo?
    ): Boolean = originalAnimator.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo)

    override fun animateAppearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo?,
        postLayoutInfo: ItemHolderInfo
    ): Boolean = originalAnimator.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo)

    override fun animatePersistence(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean = originalAnimator.animatePersistence(viewHolder, preLayoutInfo, postLayoutInfo)

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean = originalAnimator.animateChange(oldHolder, newHolder, preLayoutInfo, postLayoutInfo)

    override fun runPendingAnimations() {
        originalAnimator.runPendingAnimations()
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        logWarning("LYRA endAnimation")

        originalAnimator.endAnimation(item)
    }

    override fun endAnimations() {
        logWarning("LYRA endAnimations")

        originalAnimator.endAnimations()
    }

    override fun isRunning(): Boolean = originalAnimator.isRunning
}
