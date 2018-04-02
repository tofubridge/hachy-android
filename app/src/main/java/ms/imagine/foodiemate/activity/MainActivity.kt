package ms.imagine.foodiemate.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ms.imagine.foodiemate.Presenter.FbAuthStatePresenter
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.adapter.ResViewAdapter
import ms.imagine.foodiemate.data.Egg
import ms.imagine.foodiemate.Presenter.FbDatabasePresenter
import ms.imagine.foodiemate.views.IMainView


class MainActivity : BaseActivity(), IMainView {

    internal lateinit var txt: TextView
    internal lateinit var fbdatabase: FbDatabasePresenter
    internal lateinit var fbAuthStatePresenter: FbAuthStatePresenter
    internal lateinit var eggIndex: HashSet<String>

    //RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //List
    private lateinit var list: ArrayList<Egg>;

    override fun onCreate(savedInstanceState: Bundle?) {
        //View System set up
        eggIndex = HashSet();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.txt)

        //toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //fab icon
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val i = Intent(this@MainActivity, CameraActivity::class.java)
            fbdatabase.writeEgg(Egg("cool", System.currentTimeMillis().toString(), "uo"))
            //recyclerView.adapter.notifyDataSetChanged();
            //startActivity(i)
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }




        // FB connection Presenters:
        fbAuthStatePresenter = FbAuthStatePresenter(this, this)
        fbdatabase = FbDatabasePresenter(this, this, fbAuthStatePresenter.userState()!!.uid)


        //Logic
        checkUser()
        list = ArrayList<Egg>();
        ResViewInit(list);
    }


    private fun ResViewInit(coolDataHere: ArrayList<Egg>){
        viewManager = LinearLayoutManager(this)
        viewAdapter = ResViewAdapter(coolDataHere)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        /*recyclerView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                toast(v?.getTag(99).toString());
            }
        })
        */
    }

    private fun checkUser() {
        // todo should we even pass user locally at all?
        var user = intent.extras.get("user") as FirebaseUser?

        when {
            user != null -> txt.text = user.uid
            fbAuthStatePresenter.userState() !=null -> {
                user = fbAuthStatePresenter.userState()
                txt.text = user!!.uid
            }
            else -> {
                txt.text = "null"
                finish()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_signout -> {
                fbAuthStatePresenter.signOut();
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun signOut() {
        val i = Intent(this@MainActivity, FacebookLoginActivity::class.java)
        i.putExtra(TO_SIGN_OUT, true)
        finish()
        startActivity(i)
    }

    override fun retrieveEgg(key: String, egg: Egg){
        if(eggIndex.add(key)){
            list.add(0,egg)
            recyclerView.adapter.notifyDataSetChanged()
        }
    }

    override fun retrieveEggError(e: DatabaseException) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TO_SIGN_OUT = "sign_out"
    }
}

