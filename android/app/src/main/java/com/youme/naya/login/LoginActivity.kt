package com.youme.naya.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.youme.naya.BaseActivity
import com.youme.naya.MainActivity
import com.youme.naya.R
import com.youme.naya.screens.LoginScreen
import com.youme.naya.ui.theme.AndroidTheme


class LoginActivity : BaseActivity(TransitionMode.NONE) {

    // Firebase
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 1

    private var permissionList = listOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.NFC,
        Manifest.permission.INTERNET,
    )

    private val viewModel: PermissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val (permitted, setPermitted) = remember { mutableStateOf(false) }
            val context = LocalContext.current
            viewModel.loadTerms(context)
            viewModel.loadPrivacy(context)
            AndroidTheme {
                LoginScreen(permitted,viewModel, {
                    setPermitted(checkPermission())
                }) { googleLogin() }
            }
        }
    }

    private fun checkPermission(): Boolean {
        var res = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        for (permission in permissionList) {
            // 28버전 이후에는 WRITE 방식이 변경됨
            if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE
                && Build.VERSION.SDK_INT > Build.VERSION_CODES.P
            )
                continue
            if (checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                res = false;
                requestPermissions(permissionList.toTypedArray(), 0)
            }
            Log.i("Permission Check", "$permission processing -> $res")
        }
        return res;
    }

    // 로그인 객체 생성
    fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignIn()
    }

    // 구글 회원가입
    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "구글 회원가입에 실패하였습니다: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            /*no-op*/
        }
    }

    // account 객체에서 id 토큰 가져온 후 Firebase 인증
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                toMainActivity(auth.currentUser)
            }
        }
    }

    private fun toMainActivity(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}