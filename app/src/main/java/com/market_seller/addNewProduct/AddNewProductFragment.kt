package com.market_seller.addNewProduct

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.market_seller.common.Constant
import com.market_seller.common.Validation
import com.market_seller.databinding.FragmentAddNewProductBinding


class AddNewProductFragment : Fragment()
{

    private lateinit var binding: FragmentAddNewProductBinding
    private lateinit var viewModel: AddNewProductViewModel
    private lateinit var constant: Constant
    private lateinit var arrayPriceType: ArrayAdapter<String>
    private lateinit var arrayProductType: ArrayAdapter<String>
    private lateinit var image: String
    private lateinit var resultUri: Uri
    private lateinit var someLauncher: ActivityResultLauncher<Intent>
    private lateinit var validation: Validation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentAddNewProductBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)



        initViews()
        clickedViews()
        initViewModel()


    }

    private fun initViews()
    {
        constant = Constant()
        arrayPriceType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, constant.priceType)
        arrayPriceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.searchSpinnerPrice.adapter = arrayPriceType
        binding.searchSpinnerPrice.setTitle("Choose a price type")
        binding.searchSpinnerPrice.selectedItem.toString()
        arrayProductType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, constant.productType)
        arrayProductType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.searchSpinnerProduct.adapter = arrayProductType
        binding.searchSpinnerProduct.setTitle("Choose a product type")
        validation = Validation()
        someLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {

            val dataIntent = it.data!!
            if (it.resultCode == Activity.RESULT_OK && dataIntent.data != null)
            {
                resultUri = dataIntent.data!!
                image = resultUri.toString()
                binding.imgNewProduct.setImageURI(resultUri)
            }
            else
            {
                Toast.makeText(requireContext(), "Please choose an image of product", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clickedViews()
    {
        binding
            .imgNewProduct
            .setOnClickListener {

                openGallery()
            }

        binding
            .btnAddProduct
            .setOnClickListener {

                val title = binding.editTitle.text.toString()
                val price = binding.editPrice.text.toString()
                val priceType = binding.searchSpinnerPrice.selectedItem.toString()
                val productType = binding.searchSpinnerProduct.selectedItem.toString()
                val quantity = binding.numberPicker.progress

                if (validation.checkTitle(requireContext(), title).none())
                {
//                    validation.checkTitle(requireContext(), title)
                    binding.editTitle.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkPrice(requireContext(), price).none())
                {
//                    validation.checkPrice(requireContext(), price)
                    binding.editPrice.requestFocus()
                    return@setOnClickListener
                }

                if (binding.numberPicker.progress == 0)
                {
                    validation.checkQuantity(requireContext(), quantity)
                    return@setOnClickListener
                }

                else
                {
                    retrieveViewModel(image, title, price, priceType, productType, quantity)
                }
            }
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[AddNewProductViewModel::class.java]
    }

    private fun retrieveViewModel(image: String, title: String, price: String, priceType: String, productType: String, quantity: Int)
    {

        binding.loadingAddNewProduct.visibility = View.VISIBLE

        viewModel
            .addNewProduct(image, title, price, priceType, productType, quantity.toString())
            .observe(viewLifecycleOwner)
            {

                if (it)
                {
                    binding.loadingAddNewProduct.visibility = View.GONE
                    Toast.makeText(requireContext(), "Done, product is uploaded", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    binding.loadingAddNewProduct.visibility = View.GONE
                    Toast.makeText(requireContext(), it.toString() + "and Error try a later", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun openGallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        someLauncher.launch(intent)
    }
}