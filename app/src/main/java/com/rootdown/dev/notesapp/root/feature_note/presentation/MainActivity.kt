package com.rootdown.dev.notesapp.root.feature_note.presentation


import android.companion.CompanionDeviceManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.auth.LoginScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.util.Screen
import com.rootdown.dev.notesapp.root.feature_note.presentation.theme.EvolveNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.rootdown.dev.notesapp.BuildConfig
import com.rootdown.dev.notesapp.root.feature_sandbox_cafe.presentation.pong_tooth.PairingScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.util.NavigationItem


@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    private lateinit var db: FirebaseStorage

    private val deviceManager: CompanionDeviceManager by lazy {
        getSystemService(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager
    }
    //val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    //val x = this.applicationContext
    //val bluetoothManager = x.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Firebase.database.useEmulator("10.0.2.2", 9000)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }
        // bluetoothManager.adapter
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Initialize Firebase Auth
        auth = Firebase.auth
        setContent {
            EvolveNoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ){
                        composable(route = Screen.LoginScreen.route){
                            LoginScreen(navController = navController)
                        }
                        composable(route = Screen.NotesScreen.route){
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNotesScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color: Int = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                        composable(route = NavigationItem.PairingScreen.route){
                            PairingScreen(companionDeviceManager = deviceManager)
                        }
                    }
                }
            }
        }
    }
}