package leonov.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import leonov.ru.translator.R
import leonov.ru.model.data.DataModel
import leonov.ru.model.entity.TranslateResult
import leonov.ru.utils.network.isOnline
import leonov.ru.core.base.BaseActivity
import leonov.ru.translator.di.injectDependencies
import leonov.ru.translator.view.detail.DetailActivity
import leonov.ru.translator.view.main.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val HISTORY_ACTIVITY_PATH = "ru.leonov.history.view.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyscreen"

class MainActivity : BaseActivity<DataModel, MainInteractor>() {

    override lateinit var model: MainViewModel
    private lateinit var splitInstallManager: SplitInstallManager

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: TranslateResult) {
                startActivity(
                    DetailActivity.getIntent(this@MainActivity, data)
                )
            }
        }


    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    override fun setDataToAdapter(data: List<TranslateResult>) {
        adapter.setData(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
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
        this.model = model
        this.model
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
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                loadHistoryModule()
                true
            }
            R.id.menu_search -> {
                showSearchHistoryDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadHistoryModule() {
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
        val request = SplitInstallRequest
            .newBuilder()
            .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
            .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener {
                val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Couldn't download feature: " + it.message,
                    Toast.LENGTH_LONG
                ).show()
            }

    }

    private fun showSearchHistoryDialog() {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(onSearchHistoryClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }

    private val onSearchHistoryClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                model.getData(searchWord, false)
            }
        }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}