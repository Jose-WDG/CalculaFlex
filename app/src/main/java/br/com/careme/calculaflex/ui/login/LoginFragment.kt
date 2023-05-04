package br.com.careme.calculaflex.ui.login

import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController

import br.com.careme.calculaflex.R
import br.com.careme.calculaflex.databinding.FragmentLogInBinding
import br.com.careme.calculaflex.model.RequestState

class LoginFragment : Fragment(R.layout.fragment_log_in) {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLogInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLogInBinding.bind(view)

        registerObserver()

        setListener()


    }

    private fun setListener() {
        binding.btLogin.setOnClickListener {
            viewModel.singIn(binding.etEmailLogin.text.toString(),binding.etPasswordLogin.text.toString())
        }

        binding.tvNewAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSingnUpFragment()
            findNavController().navigate(action)
        }
    }

    private fun registerObserver() {
       viewModel.loginState.observe(viewLifecycleOwner){
           when (it){
               is RequestState.Loading ->{
                    Toast.makeText(context,"Carregando",Toast.LENGTH_SHORT).show()
               }
               is RequestState.Error -> {
                   Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
               }
               is RequestState.Success -> {
                   val action = LoginFragmentDirections.actionLoginFragmentToBetterFuelFragment(binding.etEmailLogin.text.toString())
                   findNavController().navigate(action)
               }
               else -> {

               }
           }
       }
    }
}