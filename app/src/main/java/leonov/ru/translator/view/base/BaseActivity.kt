package leonov.ru.translator.view.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.loading_layout.*
import leonov.ru.translator.R
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.entity.TranslateResult
import leonov.ru.translator.utils.network.isOnline
import leonov.ru.translator.utils.ui.AlertDialogFragment
import leonov.ru.translator.viewmodel.BaseViewModel
import leonov.ru.translator.viewmodel.Interactor

abstract class BaseActivity<T : DataModel, I : Interactor<T>> : AppCompatActivity() {

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }

    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
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
            progress_bar_horizontal.visibility = View.VISIBLE
            progress_bar_round.visibility = View.GONE
            progress_bar_horizontal.progress = dataModel.progress
        } else {
            progress_bar_horizontal.visibility = View.GONE
            progress_bar_round.visibility = View.VISIBLE
        }
    }

    private fun showErrorResult(dataModel: DataModel.Error) {
        showViewWorking()
        showAlertDialog(getString(R.string.error_stub), dataModel.error.message)
    }

    private fun showViewWorking() {
        loading_frame_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = View.VISIBLE
    }

}