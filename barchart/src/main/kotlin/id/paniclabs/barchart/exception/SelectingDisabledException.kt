package id.paniclabs.barchart.exception

class SelectingDisabledException : IllegalStateException(
        "Selecting is disabled. If you want to enable it, you can set 'selectable' to true")