package com.vlatko.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vlatko.presentation.R
import com.vlatko.presentation.base.BaseFragment
import com.vlatko.presentation.ui.gone
import com.vlatko.presentation.ui.visible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_search_cities.*


class SearchFragment : BaseFragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search_cities, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
        compositeDisposable = CompositeDisposable()
        clCity.gone()
        actSearch.apply {
            requestFocus()
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 0) tvMessage.gone()
                    else getData(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }

        clCity.setOnClickListener {

        }

        ibAddToFavorite.setOnClickListener {
            viewModel.addFavoriteCity(tvCityName.text.toString())
            findNavController().popBackStack()
        }
    }

    private fun getData(s: String? = "") {
        viewModel.searchForCity(s)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                clCity.visible()
                tvMessage.gone()
                tvCityName.text = it.name
                tvCurrentTemp.text ="${ it.main?.temp?.toInt().toString()}º"
                tvMinMaxTemp.text = "${it.main?.tempMax?.toInt().toString()}º / ${it.main?.tempMin?.toInt().toString()}º" },
                {
                clCity.gone()
                tvMessage.apply {
                    visible()
                    text = viewModel.parseError(it)
                }
            }).addTo(compositeDisposable)
    }
}