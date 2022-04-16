package com.tiooooo.nontonyuk.ui.base


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.tiooooo.core.domain.model.general.ArgumentWrapper
import com.tiooooo.core.ui.BaseActivity
import com.tiooooo.core.utils.IntentExtra
import com.tiooooo.core.utils.extensions.getJSON
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    var route: String = ""
    var args: ArgumentWrapper = ArgumentWrapper()
    private var menu: Menu? = null

    override fun getLayoutId(): Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObject()
    }

    private fun initObject() {
        intent.getJSON(IntentExtra.ARGS, ArgumentWrapper::class.java)?.let {
            args = it
        }

        intent.getStringExtra(IntentExtra.ROUTE)?.let {
            route = it
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.nav_host_fragment).popBackStack()) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        this.menu = menu
        menuInflater.inflate(R.menu.menu_fav, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun setFavoriteButton(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_active)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_deactive)
        }
    }

    override fun onDestroy() {
        menu = null
        super.onDestroy()
    }


}