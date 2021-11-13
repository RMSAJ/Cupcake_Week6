package com.example.cupcake.model

import android.view.animation.Transformation
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

private const val PRICE_PER_CUPCAKE = 2.00

private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {
    // definnig the the variables
  private val _quantity = MutableLiveData<Int>()
    val  quantity: LiveData<Int> = _quantity
  private val _cupCakeFlavor = MutableLiveData<String>()
    val cupCakeFlavor: LiveData<String> = _cupCakeFlavor
  private val _pickUpDate = MutableLiveData<String>()
    val pickUpDate: LiveData<String> = _pickUpDate
  private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }
    val dateOptions = getPickupOptions()
// reset everything when on initialisation
    init {
        resetOrder()
    }
// store the amount of cakes chosen by the user
  fun setQuantity(numCakes: Int){
    _quantity.value = numCakes
    updatePrice()
  }
// store the falvor of cake chosen by the user
 fun setflavor(flavCakes: String){
   _cupCakeFlavor.value = flavCakes
 }
// store the  date chosen by the user
 fun setDate(pickDate: String){
   _pickUpDate.value = pickDate
    updatePrice()
 }
// for putting a defult value for the flavor
 fun hasNoFlavorSet(): Boolean {
     return _cupCakeFlavor.value.isNullOrEmpty()
 }
// for updating the date based on locale date of the user
 private fun getPickupOptions(): List<String> {
     val options = mutableListOf<String>()
     val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
     val calendar = Calendar.getInstance()
     repeat(4){
         options.add(formatter.format(calendar.time))
         calendar.add(Calendar.DATE, 1)
     }
     return options
 }
// reset everything
 fun resetOrder() {
  _quantity.value = 0
  _cupCakeFlavor.value = ""
  _pickUpDate.value = dateOptions[0]
  _price.value = 0.0
 }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _pickUpDate.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice

    }

}