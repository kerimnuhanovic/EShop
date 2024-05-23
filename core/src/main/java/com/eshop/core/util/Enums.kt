package com.eshop.core.util

sealed class OrderStatus(val name: String) {
    object Pending : OrderStatus("pending")
    object Approved: OrderStatus("approved")
    object Declined: OrderStatus("declined")

    companion object {
        fun toOrderStatus(orderStatus: String): OrderStatus {
            return when (orderStatus) {
                "pending" -> {
                    Pending
                }
                "approved" -> {
                    Approved
                }
                "declined" -> {
                    Declined
                }

                else -> {
                    throw IllegalArgumentException("Function called with illegal argument")
                }
            }
        }

    }
}

sealed class ToastMessage(val message: String) {
    object OrderSubmitted : ToastMessage("Order has been successfully submitted.")
    object ItemDeletionFailed : ToastMessage("Something went wrong and we were unable to delete item.")
    object ItemDeleted : ToastMessage("Item has been successfully removed from your cart.")
    object ReviewNotSubmitted : ToastMessage("Something went wrong and we were not able to submit review.")
    object ReviewSubmitted : ToastMessage("Review has been successfully submitted.")

}