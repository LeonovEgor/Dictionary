package leonov.ru.translator.view.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import leonov.ru.translator.R
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.entity.TranslateResult
import leonov.ru.translator.utils.network.isOnline
import leonov.ru.translator.utils.sound.SoundHelper
import leonov.ru.translator.view.base.BaseActivity
import leonov.ru.translator.view.detail.DetailActivity
import leonov.ru.translator.view.main.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<DataModel, MainInteractor>() {

    private lateinit var mainViewModel: MainViewModel

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: TranslateResult) {
                startActivity(
                    DetailActivity.getIntent(this@MainActivity, data)
                )
            }
        }


    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initModel()
        initRecyclerView()
        initFab()
    }

    private fun initRecyclerView() {
        rv_search_result.layoutManager = LinearLayoutManager(applicationContext)
        rv_search_result.adapter = adapter
    }

    private fun initModel() {
        val model: MainViewModel by viewModel()
        mainViewModel = model
        mainViewModel
            .subscribe()
            .observe(this@MainActivity, Observer {
                renderData(it)
            })
    }

    private fun initFab() {
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    mainViewModel.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                showSuccessResult(dataModel)
            }
            is DataModel.Loading -> {
                showLoadingProcess(dataModel)
            }
            is DataModel.Error -> {
                showErrorResult(dataModel)
            }
        }
    }

    private fun showSuccessResult(dataModel: DataModel.Success) {
        showViewWorking()

        if (dataModel.data.isNullOrEmpty()) {
            showAlertDialog(
                getString(R.string.dialog_tittle_sorry),
                getString(R.string.empty_server_response_on_success)
            )
        } else {
            adapter.setData(dataModel.data)
        }
    }

    private fun showLoadingProcess(dataModel: DataModel.Loading) {
        showViewLoading()
        if (dataModel.progress != null) {
            progress_bar_horizontal.visibility = VISIBLE
            progress_bar_round.visibility = GONE
            progress_bar_horizontal.progress = dataModel.progress
        } else {
            progress_bar_horizontal.visibility = GONE
            progress_bar_round.visibility = VISIBLE
        }
    }

    private fun showErrorResult(dataModel: DataModel.Error) {
        showViewWorking()
        showAlertDialog(getString(R.string.error_stub), dataModel.error.message)
    }

    private fun showViewWorking() {
        loading_frame_layout.visibility = GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}