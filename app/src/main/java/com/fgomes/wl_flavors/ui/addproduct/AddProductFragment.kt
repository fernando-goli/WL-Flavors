package com.fgomes.wl_flavors.ui.addproduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fgomes.wl_flavors.databinding.AddProductFragmentBinding
import com.fgomes.wl_flavors.util.CurrencyTextWatcher
import com.fgomes.wl_flavors.util.PRODUCT_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding: AddProductFragmentBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
        binding.ivProduct.setImageURI(uri)
    }

    private val viewModel: AddProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerVMEvents()
        setListeners()
    }

    private fun observerVMEvents(){
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner){ drawableResId ->
            binding.ivProduct.setBackgroundResource(drawableResId)
        }

        viewModel.descriptionFieldErrorResId.observe(viewLifecycleOwner){ stringResId ->
            binding.tilDescription.setError(stringResId)
        }

        viewModel.priceFieldErrorResId.observe(viewLifecycleOwner){ stringResId ->
            binding.tilPrice.setError(stringResId)
        }

        viewModel.productCreated.observe(viewLifecycleOwner){ product ->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(PRODUCT_KEY, product)
                popBackStack()
            }
        }
    }

    private fun TextInputLayout.setError (stringResId: Int?){
        error = if(stringResId != null){
            getString(stringResId)
        } else null
    }

    private fun setListeners(){
        binding.ivProduct.setOnClickListener {
            chooseImage()
        }

        binding.btAddProduct.setOnClickListener {
            val description = binding.tieDescription.text.toString()
            val price = binding.tiePrice.text.toString()

            viewModel.createProduct(description, price, imageUri)
        }

        binding.tiePrice.run {
            addTextChangedListener(CurrencyTextWatcher(this) )
        }
    }

    private fun chooseImage() {
        getContent.launch("image/*")
    }

}