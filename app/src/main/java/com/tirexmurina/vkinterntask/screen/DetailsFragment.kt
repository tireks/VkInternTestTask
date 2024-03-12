package com.tirexmurina.vkinterntask.screen

import android.graphics.Paint
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.FragmentDetailsBinding
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.presentation.DetailsViewModel
import com.tirexmurina.vkinterntask.presentation.DetailsViewState
import com.tirexmurina.vkinterntask.presentation.ImageViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val viewModel : DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        loadData(args.id) //todo
    }

    private fun loadData(id: String) {
        viewModel.getProductById(id)
    }

    private fun handleState(detailsViewState: DetailsViewState) {
        when(detailsViewState){
            DetailsViewState.Initial -> Unit
            is DetailsViewState.Content -> showContent(detailsViewState.item)
            is DetailsViewState.Error -> showError(detailsViewState.errorMsg)
            DetailsViewState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        with(binding) {
            errorContent.isVisible = false
            contentContainer.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun showError(errorMsg: String) {
        with(binding) {
            progressBar.isVisible = false
            contentContainer.isVisible = false
            errorContent.isVisible = true
            errorText.text = errorMsg
            errorButton.setOnClickListener {
                loadData(args.id) //todo
            }
        }
    }

    private fun showContent(item: Product) {
        with(binding){
            progressBar.isVisible = false
            errorContent.isVisible = false
            contentContainer.isVisible = true
            populateViewPager(item.images)
            scrollBrand.text = item.brand
            scrollTitle.text = item.title
            scrollAvailableNum.text = String.format(resources.getString(R.string.details_stock), item.stock)
            scrollRating.rating = item.rating.toFloat()
            scrollFeedback.text = item.rating
            scrollPriceNew.text = String.format(resources.getString(R.string.pricing_template_string), item.price)
            val oldPrice = (item.price.toInt()*(100+item.discountPercentage.toFloat())/100).toInt().toString()
            scrollPriceOld.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            scrollPriceOld.text = String.format(resources.getString(R.string.pricing_template_string), oldPrice)
            //todo перечеркнуть
            scrollDiscountLabelText.text = String.format(resources.getString(R.string.discount_template_string), item.discountPercentage)
            scrollVendorText.text = item.brand
            scrollDescriptionText.text = item.description
            scrollHideDescriptionButton.text = "Скрыть"
            upNavButton.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }
            scrollVendorView.setOnClickListener {

            }
            scrollHideDescriptionButton.setOnClickListener {
                if (descriptionLayout.isVisible){
                    descriptionLayout.isVisible = false
                    scrollHideDescriptionButton.text = "Раскрыть"
                } else {
                    descriptionLayout.isVisible = true
                    scrollHideDescriptionButton.text = "Скрыть"
                }
            }
        }
    }

    private fun populateViewPager(images: List<String>) {
        val adapter = ImageViewPagerAdapter()
        binding.scrollItemViewpager.adapter = adapter
        binding.scrollItemViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        (binding.scrollItemViewpager.adapter as? ImageViewPagerAdapter)?.pics = images
        binding.indicator.attachToPager(binding.scrollItemViewpager)
    }

    //todo разобраться с списком картинок(самб и картинки - отдельные категории. мб слить их в одну в конвертере? например в один лист)


}