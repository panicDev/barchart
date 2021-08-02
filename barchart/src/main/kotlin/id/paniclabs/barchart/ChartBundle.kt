package id.paniclabs.barchart

internal data class ChartBundle(
    val item: ChartItem,
    val config: ChartConfig,
    val isSelected: Boolean = false
)