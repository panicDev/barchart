package id.paniclabs.barchart


/**
 * @author   panicDev
 * @email    panic.inc.dev@gmail.com
 */
interface ItemClickListener {
    fun onItemClick(item: ChartItem)
    fun onItemLongClick(item: ChartItem)
}