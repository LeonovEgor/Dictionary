package leonov.ru.translator.view.base

import leonov.ru.translator.model.data.DataModel

interface View {

    fun renderData(dataModel: DataModel)

}
