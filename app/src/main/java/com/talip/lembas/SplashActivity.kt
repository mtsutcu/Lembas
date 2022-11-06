package com.talip.lembas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.talip.lembas.databinding.ActivitySplashBinding
import com.talip.lembas.model.MyUser
import com.talip.lembas.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    private lateinit var myUser: MyUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splashText1 = binding.textViewLembas1
        val splashText2 = binding.textViewLembas2

        val tempViewModel: SplashViewModel by viewModels()
        viewModel = tempViewModel

        viewModel.user.observe(this as LifecycleOwner) {
            myUser = it
            Log.e("viewc", "LOGIN OBSERVE USER : $it")
            if (myUser.user_id == null) {
                Handler().postDelayed({
                    val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            } else {
                Handler().postDelayed({
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)

        splashText1.startAnimation(anim)
        splashText2.startAnimation(anim)
    }
}