package leonov.ru.core.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.loading_layout.*
import leonov.ru.core.R
import leonov.ru.core.viewmodel.BaseViewModel
import leonov.ru.core.viewmodel.Interactor
import leonov.ru.model.data.DataModel
import leonov.ru.model.entity.TranslateResult
import leonov.ru.utils.network.OnlineLiveData
import leonov.ru.utils.ui.showAlertDialog

abstract class BaseActivity<T : DataModel, I : Interactor<T>> : AppCompatActivity() {

    protected abstract val layoutRes: Int
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(this@BaseActivity, Observer<Boolean> {
            isNetworkAvailable = it
            if (!it) {
                snackBar.show()
            } else {
                snackBar.dismiss()
            }
        })
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
            progress_bar_horizontal.visibility = View.VISIBLE
            progress_bar_round.visibility = View.GONE
            progress_bar_horizontal.progress = dataModel.progress!!
        } else {
            progress_bar_horizontal.visibility = View.GONE
            progress_bar_round.visibility = View.VISIBLE
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
        loading_frame_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = View.VISIBLE
    }

}