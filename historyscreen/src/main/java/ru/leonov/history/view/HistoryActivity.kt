package ru.leonov.history.view

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import leonov.ru.model.data.DataModel
import leonov.ru.model.entity.TranslateResult
import leonov.ru.core.base.BaseActivity
import ru.leonov.history.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<DataModel, HistoryInteractor>() {

    override val model: HistoryViewModel by viewModel()
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setActionbarHomeButtonAsUp()
        iniViewModel()
        initViews()
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniViewModel() {
        if (rv_history.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.subscribe().observe(this@HistoryActivity, Observer { renderData(it) })
    }

    private fun initViews() {
        rv_history.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<TranslateResult>) {
        adapter.setData(data)
    }
}
