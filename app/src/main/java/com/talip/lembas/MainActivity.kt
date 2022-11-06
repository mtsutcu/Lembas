package com.talip.lembas


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.talip.lembas.databinding.ActivityMainBinding
import com.talip.lembas.model.MyUser
import com.talip.lembas.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var myUser: MyUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavBar, navController)
        val tempViewModel: MainActivityViewModel by viewModels()
        mainViewModel = tempViewModel

        var badge = binding.bottomNavBar.getOrCreateBadge(R.id.bag)
        badge.isVisible = false
        badge.backgroundColor = resources.getColor(R.color.myRed)
        badge.badgeTextColor = Color.WHITE

        badge.number = 1
        mainViewModel.bagFoodList.observe(this as LifecycleOwner) {
            badge.isVisible = it.isNotEmpty()
            Log.e("Badgee", "$it")
        }


        mainViewModel.user.observe(this as LifecycleOwner) {
            myUser = it
            Log.e("viewc", "LOGIN OBSERVE USER : $it")
            if (myUser.user_id == null) {
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            } else {
            }
        }




        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.explore -> {
                    navController.popBackStack(R.id.main_nav, false)
                    navController.navigate(R.id.homeFragment)
                }
                R.id.search -> {
                    navController.popBackStack(R.id.main_nav, false)
                    navController.navigate(R.id.searchFragment)
                }
                R.id.bag -> {
                    navController.popBackStack(R.id.main_nav, false)
                    navController.navigate(R.id.bagFragment)
                }

                R.id.order -> {
                    navController.popBackStack(R.id.main_nav, false)
                    navController.navigate(R.id.orderFragment)
                }
                R.id.settings -> {
                    navController.popBackStack(R.id.main_nav, false)
                    navController.navigate(R.id.settingsFragment)
                }
            }
            true
        }
    }

    fun getBagFoods() {
        mainViewModel.getBagFoods()
    }

    fun exit() {
        mainViewModel.exit()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.getNot()

    }
}
