package ms.imagine.foodiemate.views

import com.google.firebase.database.DatabaseException
import ms.imagine.foodiemate.data.Egg

interface IMainView {

    fun showEggDetail(egg: Egg)
}