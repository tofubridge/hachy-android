/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.imagine.foodiemate.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_facebook.*
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.utils.Eulog

/**
 * Demonstrate Firebase Authentication using a Facebook access token.
 */
class FacebookLoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth


    // mAuth declared in BaseActivity
    private var mCallbackManager: CallbackManager? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)
        btn_sub_facebook.setOnClickListener(this)
        btn_sub_device.setOnClickListener(this)

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()
        button_facebook_login.setReadPermissions("email", "public_profile")
        button_facebook_login.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Eulog.d(TAG + "facebook:onSuccess:" + loginResult)
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Eulog.d(TAG + "facebook:onCancel")
                onLoginStatusChanged()
            }

            override fun onError(error: FacebookException) {
                Eulog.d(TAG + "facebook:onError"+ error)
                onLoginStatusChanged()
            }
        })
    }

    // Check if User is logged in on start
    public override fun onStart() {
        super.onStart()
        Eulog.w("entered on startup")
        onLoginStatusChanged()
    }

    // Received Result from FB
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleAnynomousSignIn() {
        pb1.visibility = View.VISIBLE
        mAuth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Eulog.d(TAG + "signInAnonymously:success")
                        onLoginStatusChanged()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInAnonymously:failure", task.exception)
                        toast(getString(R.string.auth_failed))
                        onLoginStatusChanged()
                    }
                }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Eulog.d(TAG + "handleFacebookAccessToken:" + token)
        pb1.visibility = View.VISIBLE

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Eulog.d(TAG + "signInWithCredential:success")
                        onLoginStatusChanged()
                    } else {
                        // If sign in fails, display a message to the user.
                        Eulog.d(TAG + "signInWithCredential:failure\n" + task.exception)
                        toast(getString(R.string.auth_failed))
                        onLoginStatusChanged()
                    }
                    pb1.visibility = View.GONE
                }
    }


    private fun onLoginStatusChanged() {
        pb1.visibility = View.GONE
        val user = mAuth.currentUser
        if (user != null) {
            val i = Intent(this@FacebookLoginActivity, MainActivity::class.java)
            i.putExtra(getString(R.string.tag_user), user)
            finish()
            startActivity(i)
        } else {
            toast(getString(R.string.log_out))
            // user would be expected to know that they are logged out
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_sub_facebook -> button_facebook_login.performClick()
            R.id.btn_sub_device -> handleAnynomousSignIn()
        }
    }

    companion object {
        private val TAG = "FacebookLogin"
    }
}
