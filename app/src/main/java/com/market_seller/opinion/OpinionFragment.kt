package com.market_seller.opinion

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.market_seller.adapter.OpinionAdapter
import com.market_seller.databinding.FragmentOpinionBinding
import com.market_seller.interfaces.Opinion
import com.market_seller.model.OpinionModel

class OpinionFragment : Fragment(), Opinion
{

    private lateinit var binding: FragmentOpinionBinding
    private lateinit var viewModel: OpinionViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentOpinionBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView()
        clickedView()
        initViewModel()
        retrieveViewModel()

    }

    private fun initView()
    {
    }

    private fun clickedView()
    {
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[OpinionViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveViewModel()
    {
        binding.loadingOpinion.visibility = View.VISIBLE
        viewModel
            .opinionUser()
            .observe(viewLifecycleOwner)
            {

                binding.loadingOpinion.visibility = View.GONE

                val opinionAdapter = OpinionAdapter(it, this@OpinionFragment)
                binding.rVOpinion.adapter = opinionAdapter
                binding.rVOpinion.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rVOpinion.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                opinionAdapter.notifyDataSetChanged()
            }
    }

    override fun trueOfOpinion(opinionModel: OpinionModel)
    {
        viewModel
            .clickTrue(opinionModel.randomKey, opinionModel.nameUser, opinionModel.imageUser, opinionModel.commentUser, opinionModel.numberTrue!!.inc(), opinionModel.numberFalse!!)
            .observe(viewLifecycleOwner)
            {

                if (it)
                {
                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun falseOfOpinion(opinionModel: OpinionModel)
    {
        viewModel
            .clickFalse(opinionModel.randomKey, opinionModel.nameUser, opinionModel.imageUser, opinionModel.commentUser, opinionModel.numberTrue!!, opinionModel.numberFalse!!.inc())
            .observe(viewLifecycleOwner)
            {

                if (it)
                {
                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

            }
    }

    override fun toastReplyOfOpinion()
    {
        Toast.makeText(requireContext(), "Soon", Toast.LENGTH_SHORT).show()
    }

}