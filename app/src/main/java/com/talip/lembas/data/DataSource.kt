package com.talip.lembas.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.core.ActivityScope
import com.talip.lembas.R
import com.talip.lembas.model.*
import kotlinx.coroutines.tasks.await

class DataSource(var fAuth: FirebaseAuth, var databaseRef: DatabaseReference) {
    var user = MutableLiveData<MyUser>()
    var campaignList = MutableLiveData<List<String>>()
    var restaurantList = MutableLiveData<List<Restaurant>>()
    var orders = MutableLiveData<Order>()
    var ad = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
    var appctx = fAuth.app.applicationContext



    fun getRestaurants(): MutableLiveData<List<Restaurant>> {
        isLoading.value = true
        databaseRef.child("restaurants").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var resList = ArrayList<Restaurant>()

                for (s in snapshot.children) {
                    var res = Restaurant(null, null, null, null, null, null, null)
                    var foodList = ArrayList<String>()

                    if (s != null) {
                        for (y in s.children) {
                            when (y.key) {
                                "food_id" -> {
                                    for (i in y.children) {
                                        if (i != null) {
                                            foodList.add(i.key.toString())
                                        }
                                    }
                                    res.food_id = foodList
                                }
                                "restaurant_id" -> res.restaurant_id = y.value.toString()
                                "restaurant_name" -> res.restaurant_name = y.value.toString()
                                "image_url" -> res.image_url = y.value.toString()
                                "min_price" -> res.min_price = y.value.toString()
                                "time" -> res.time = y.value.toString()
                                "rating" -> res.rating = y.value.toString().toFloat()

                            }
                        }
                    }
                    resList.add(res)
                    Log.e("res", "Restaurant: $res")
                }
                if (resList.isNotEmpty()) {
                    isLoading.value = false
                }
                restaurantList.value = resList


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })






        return restaurantList

    }


    fun getCampaigns(): MutableLiveData<List<String>> {
        databaseRef.child("campaigns").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<String>()

                for (s in snapshot.children) {
                    var item = s.getValue(String::class.java)
                    if (item != null) {
                        list.add(item)
                    }
                }
                Log.e("camp", "$list")
                campaignList.value = list
                Log.e("camp", "Camp List Data source : ${campaignList.value}")
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return campaignList
    }


    suspend fun createUserWithEmail(
        email: String,
        password: String,
        address: String,
        context: Context,
        view: View
    ) {
        try {
            isLoading.value = true
            var result = fAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    databaseRef.child("addresses").child(result.user!!.uid).setValue(address)
                        .addOnCompleteListener { it ->
                            if (it.isSuccessful) {

                                Toast.makeText(
                                    context,
                                    R.string.sendVerifyEmail,
                                    Toast.LENGTH_SHORT
                                ).show()
                                Navigation.findNavController(view).popBackStack()
                                isLoading.value = false


                            }
                        }
                }
            }
        } catch (e: Exception) {
            isLoading.value = false
            var alert = AlertDialog.Builder(context)
            alert.setTitle(R.string.error)
            alert.setMessage("${e.message} ")

            alert.setPositiveButton(R.string.ok) { s, d ->
            }
            alert.create().show()
        }


    }

    fun updateAddress(address: String) {
        databaseRef.child("addresses").child(user.value!!.user_id.toString()).setValue(address)
    }

    fun getAddress(): MutableLiveData<String> {
        databaseRef.child("addresses").child(user.value!!.user_id.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ad.value = snapshot.getValue(String::class.java)
                    Log.e("addr", " Address Data source: ${ad.value}")

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        return ad
    }


    fun sendPasswordLink(email: String, context: Context, view: View) {
        try {
            fAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(
                        view,
                        R.string.resetLink,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Navigation.findNavController(view).popBackStack()
                } else {
                    Snackbar.make(
                        view,
                        R.string.resetLinkError,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            var alert = AlertDialog.Builder(context)
            alert.setTitle(R.string.error)
            alert.setMessage("${e.message} ")

            alert.setPositiveButton(R.string.ok) { s, d -> }
            alert.create().show()
        }

    }


    suspend fun login(email: String, password: String, context: Context): MutableLiveData<MyUser> {
        isLoading.value = true

        try {
            var result = fAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user!!.isEmailVerified) {
                user.value = MyUser(result.user?.uid, result.user?.email)

            } else {

                fAuth.signOut()
                isLoading.value = false
                Toast.makeText(context, R.string.verifyEmail, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {


            var alert = AlertDialog.Builder(context)
            alert.setTitle(R.string.error)
            alert.setMessage(R.string.checkEmail)
            alert.setPositiveButton(R.string.ok) { s, d -> }

            alert.create().show()
            isLoading.value = false

        }

        return user
    }

    fun currentUser(): MutableLiveData<MyUser> {
        val userFirebase = fAuth.currentUser
        if (userFirebase?.isEmailVerified == true) {
            val myUser = MyUser(userFirebase.uid, userFirebase.email)
            user.value = myUser
            Log.e("viewc", "Data source current user : ${user.value}")
        } else {
            user.value = MyUser(null, null)
        }
        return user
    }

    fun exit() {
        fAuth.signOut()
        user.value = MyUser(null, null)
    }

    fun searchRestaurant(searchText: String): MutableLiveData<List<Restaurant>> {
        isLoading.value= true
        databaseRef.child("restaurants").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var resList = ArrayList<Restaurant>()

                for (s in snapshot.children) {
                    var res = Restaurant(null, null, null, null, null, null, null)
                    var foodList = ArrayList<String>()

                    if (s != null) {
                        for (y in s.children) {
                            when (y.key) {
                                "food_id" -> {
                                    for (i in y.children) {
                                        if (i != null) {
                                            foodList.add(i.key.toString())
                                        }
                                    }
                                    res.food_id = foodList
                                }
                                "restaurant_id" -> res.restaurant_id = y.value.toString()
                                "restaurant_name" -> res.restaurant_name = y.value.toString()
                                "image_url" -> res.image_url = y.value.toString()
                                "min_price" -> res.min_price = y.value.toString()
                                "time" -> res.time = y.value.toString()
                                "rating" -> res.rating = y.value.toString().toFloat()

                            }
                        }
                    }
                    if (res.restaurant_name!!.lowercase().contains(searchText.lowercase())) {
                        resList.add(res)
                    }
                    Log.e("res", "Restaurant: $res")
                }
                restaurantList.value = resList

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        isLoading.value = false
        return restaurantList

    }

    fun createOrder(order: Order, context: Context, view: View) {
        try {
            databaseRef.child("orders").child(user.value!!.user_id.toString()).setValue(order)
                .addOnCompleteListener {
                    Navigation.findNavController(view).popBackStack()
                }
        } catch (e: Exception) {
            var alert = AlertDialog.Builder(context)
            alert.setTitle(R.string.error)
            alert.setMessage("${e.message} ")
            alert.setPositiveButton(R.string.ok) { s, d ->
            }
            alert.create().show()
        }


    }


    fun getOrder(): MutableLiveData<Order> {
        try {
            databaseRef.child("orders").child(user.value!!.user_id.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var order = Order(null, null, null)
                        for (o in snapshot.children) {
                            when (o.key) {
                                "order_id" -> order.order_id = o.value.toString()
                                "bagList" -> {
                                    order.bagList = o.value as List<Bag>?

                                }
                                "orderState" -> order.orderState = o.value.toString().toInt()
                            }
                        }
                        Log.e("Orderr", "Order data source : $order")
                        if (order.orderState != 0 && order.order_id != null) {


                        }
                        orders.value = order
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        } catch (e: Exception) {
            Log.e("Orderr", "Order data source error: $e")

            var order = Order(null, null, null)
            orders.value = order
        }
        Log.e("Orderr", "Order data source mutable : ${orders.value}")

        return orders

    }

    fun getNot() {
        try {
            databaseRef.child("orders").child(user.value!!.user_id.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var order = Order(null, null, null)
                        for (o in snapshot.children) {
                            when (o.key) {
                                "order_id" -> order.order_id = o.value.toString()
                                "bagList" -> {
                                    order.bagList = o.value as List<Bag>?

                                }
                                "orderState" -> order.orderState = o.value.toString().toInt()
                            }
                        }
                        Log.e("Orderr", "Order data source : $order")
                        if (order.orderState != 0 && order.order_id != null) {
                            createNotification(order.orderState!!)

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        } catch (e: Exception) {
            Log.e("Orderr", "Order data source error: $e")


        }
        Log.e("Orderr", "Order data source mutable : ${orders.value}")


    }


    fun deleteOrder() {
        try {
            databaseRef.child("orders").child(user.value!!.user_id.toString()).removeValue()
        } catch (e: Exception) {

        }
    }


    fun createNotification(state: Int) {
        var language = Resources.getSystem().getConfiguration().locale.getLanguage();

        val builder: NotificationCompat.Builder

        val nm =
            appctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            var channel: NotificationChannel? = nm.getNotificationChannel("id")

            if (channel == null) {
                channel = NotificationChannel("id", "name", NotificationManager.IMPORTANCE_HIGH)
                channel.description = "description"
                nm.createNotificationChannel(channel)
            }

            builder = NotificationCompat.Builder(appctx, "id")
            var message = ""
            if (language == "en") {
                when (state) {
                    1 -> message = "Your order is preparing"
                    2 -> message = "Your order is on its way"
                    3 -> message = "Your order was delivered"
                }
            } else {
                when (state) {
                    1 -> message = "Siparişiniz hazırlanıyor"
                    2 -> message = "Siparişiniz yolda"
                    3 -> message = "Siparişiniz teslim edildi"
                }
            }
            builder.setContentTitle("Lembas").setContentText(message)
                .setSmallIcon(R.drawable.icon_fastfood)
                .setAutoCancel(false)


        } else {

            builder = NotificationCompat.Builder(appctx)
            var message = ""
            if (language == "en") {
                when (state) {
                    1 -> message = "Your order is preparing"
                    2 -> message = "Your order is on its way"
                    3 -> message = "Your order was delivered"
                }
            } else {
                when (state) {
                    1 -> message = "Siparişiniz hazırlanıyor"
                    2 -> message = "Siparişiniz yolda"
                    3 -> message = "Siparişiniz teslim edildi"
                }
            }

            builder.setContentTitle("Lembas").setContentText(message)
                .setSmallIcon(R.drawable.icon_fastfood)
                .setAutoCancel(true).priority = Notification.PRIORITY_HIGH

        }

        nm.notify(1, builder.build())


    }

}