package leonov.ru.utils.ui

import androidx.fragment.app.FragmentManager

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

fun showAlertDialog(fragmentManager: FragmentManager, title: String?, message: String?) {
    AlertDialogFragment.newInstance(title, message)
        .show(fragmentManager, DIALOG_FRAGMENT_TAG)
}