package leonov.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import leonov.ru.core.base.BaseActivity
import leonov.ru.model.data.DataModel
import leonov.ru.model.entity.TranslateResult
import leonov.ru.translator.R
import leonov.ru.translator.di.injectDependencies
import leonov.ru.translator.view.detail.DetailActivity
import leonov.ru.translator.view.main.adapter.MainAdapter
import leonov.ru.utils.ui.viewById
import org.koin.androidx.scope.lifecycleScope

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val HISTORY_ACTIVITY_PATH = "ru.leonov.history.view.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyscreen"
private const val REQUEST_CODE = 42

class MainActivity : BaseActivity<DataModel, MainInteractor>() {

    override val layoutRes = R.layout.activity_main
    override lateinit var model: MainViewModel
    private val rvSearchResult by viewById<RecyclerView>(R.id.rv_search_result)

    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {
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

        injectDependencies()
        initModel()
        initRecyclerView()
        initFab()
        checkForUpdates()
    }

    private fun initModel() {
        val model: MainViewModel by lifecycleScope.inject()
        this.model = model
        this.model
            .subscribe()
            .observe(this@MainActivity, Observer {
                renderData(it)
            })
    }

    private fun initRecyclerView() {
        rvSearchResult.layoutManager = LinearLayoutManager(applicationContext)
        rvSearchResult.adapter = adapter
    }

    private fun initFab() {
        val searchFab by viewById<FloatingActionButton>(R.id.search_fab)
        searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private val onSearchClickListener = object : SearchDialogFragment.OnSearchClickListener {
        override fun onClick(searchWord: String) {
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

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateIntent ->
                if (appUpdateIntent.updateAvailability() ==
                    UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
                ) {
                    appUpdateManager.registerListener(stateUpdatedListener)
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateIntent,
                        IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }

    private val stateUpdatedListener = InstallStateUpdatedListener { state ->
        state?.let {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.main_container),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: $resultCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }

}