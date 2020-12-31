package ru.leonov.history.view

import android.os.Bundle
import android.view.MenuItem
import leonov.ru.core.base.BaseActivity
import leonov.ru.model.data.DataModel
import leonov.ru.model.entity.TranslateResult
import ru.leonov.history.R
import ru.leonov.history.databinding.ActivityHistoryBinding
import ru.leonov.history.di.injectDependencies

class HistoryActivity : BaseActivity<DataModel, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        if (binding.rvHistory.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val historyModel: HistoryViewModel by inject()
        model = historyModel
        model.subscribe().observe(this@HistoryActivity, { renderData(it) })
    }

    private fun initViews() {
        binding.rvHistory.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<TranslateResult>) {
        adapter.setData(data)
    }

}