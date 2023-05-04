package br.com.careme.calculaflex.ui.betterfuel

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.careme.calculaflex.R
import br.com.careme.calculaflex.databinding.FragmentBetterFuelBinding
import br.com.careme.calculaflex.databinding.FragmentSignUpBinding

class BetterFuelFragment : Fragment(R.layout.fragment_better_fuel) {

    private lateinit var binding: FragmentBetterFuelBinding

    val args: BetterFuelFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBetterFuelBinding.bind(view)
        Toast.makeText(requireContext(), args.userEmail,
            Toast.LENGTH_LONG).show()
        binding.btCalculate.setOnClickListener {
            Toast.makeText(requireContext(), "Calculando ${args.userEmail}", Toast.LENGTH_LONG).show()
        }
        binding.btSignOut.setOnClickListener {
            val action = BetterFuelFragmentDirections.actionBetterFuelFragment2ToLoginFragment()
            findNavController().navigate(action)
        }
    }
}