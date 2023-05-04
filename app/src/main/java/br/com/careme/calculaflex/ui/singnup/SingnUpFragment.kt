package br.com.careme.calculaflex.ui.singnup

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.careme.calculaflex.R
import br.com.careme.calculaflex.databinding.FragmentSignUpBinding
import br.com.careme.calculaflex.model.NewUser
import br.com.careme.calculaflex.model.RequestState
import br.com.careme.calculaflex.ui.login.LoginFragmentDirections

class SingnUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SingUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        setUpListener()
        registerObservers()
    }

    private fun setUpListener() {
        binding.btCreateAccount.setOnClickListener {
          viewModel.singUp(
              NewUser(
                  binding.etUserNameSignUp.text.toString(),
                  binding.etEmailSignUp.text.toString(),
                  binding.etPhoneSignUp.text.toString(),
                  binding.etPasswordSignUp.text.toString()
              )
          )
        }

        binding.btTerms.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(
                ActivityCompat.getColor(requireContext(), R.color.secondaryBackground))
            builder.setStartAnimations(
                requireContext(),
                R.anim.slide_in_right, R.anim.slide_out_left
            )
            builder.setExitAnimations(
                requireContext(),
                R.anim.slide_in_left, R.anim.slide_out_right
            )

            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireContext(),
                Uri.parse("https://fir-mvvm-37124.web.app/"))
        }
    }

    private fun registerObservers() {
       viewModel.singUpState.observe(viewLifecycleOwner){
           when(it){
               is RequestState.Loading ->{
                   Toast.makeText(context,"Carregando", Toast.LENGTH_SHORT).show()
               }
               is RequestState.Error -> {
                   Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
               }
               is RequestState.Success -> {
                   val action = SingnUpFragmentDirections.actionSingnUpFragmentToBetterFuelFragment()
                   findNavController().navigate(action)
               }
               else -> {

               }
           }
       }
    }
}