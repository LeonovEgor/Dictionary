package leonov.ru.core.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.google.android.material.snackbar.Snackbar
import leonov.ru.core.R
import leonov.ru.core.databinding.LoadingLayoutBinding
import leonov.ru.core.viewmodel.BaseViewModel
import leonov.ru.core.viewmodel.Interactor
import leonov.ru.model.data.DataModel
import leonov.ru.model.entity.TranslateResult
import leonov.ru.utils.network.NetworkStatus
import leonov.ru.utils.ui.showAlertDialog
import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity<T : DataModel, I : Interactor<T>> : ScopeActivity() {

    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = true

    private val snackBar: Snackbar by lazy {
        val root = window.decorView.findViewById<View>(android.R.id.content)
        Snackbar.make(
            root,
            R.string.dialog_message_device_is_offline,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    private lateinit var binding: LoadingLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        NetworkStatus(this).isOnline().observe(this@BaseActivity, {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                noNetworkReaction()
            } else {
                onlineReaction()
            }
        })
    }

    private fun noNetworkReaction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            startActivityForResult(panelIntent, 42)
        } else {
            snackBar.show()
        }
    }

    private fun onlineReaction() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            snackBar.dismiss()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            supportFragmentManager,
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun renderData(dataModel: DataModel) {
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
                supportFragmentManager,
                getString(R.string.dialog_tittle_sorry),
                getString(R.string.empty_server_response_on_success)
            )
        } else {
            setDataToAdapter(dataModel.data)
        }
    }

    abstract fun setDataToAdapter(data: List<TranslateResult>)

    private fun showLoadingProcess(dataModel: DataModel.Loading) {
        showViewLoading()
        if (dataModel.progress != null) {
            binding.progressBarHorizontal.visibility = View.VISIBLE
            binding.progressBarRound.visibility = View.GONE
            binding.progressBarHorizontal.progress = dataModel.progress!!
        } else {
            binding.progressBarHorizontal.visibility = View.GONE
            binding.progressBarRound.visibility = View.VISIBLE
        }
    }

    private fun showErrorResult(dataModel: DataModel.Error) {
        showViewWorking()
        showAlertDialog(
            supportFragmentManager,
            getString(R.string.error_stub),
            dataModel.error.message
        )
    }

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = View.VISIBLE
    }

}