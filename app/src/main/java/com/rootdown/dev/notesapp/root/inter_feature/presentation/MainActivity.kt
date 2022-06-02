package com.rootdown.dev.notesapp.root.inter_feature.presentation


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
import com.google.firebase.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.auth.LoginScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesScreen
import com.rootdown.dev.notesapp.root.inter_feature.presentation.util.Screen
import com.rootdown.dev.notesapp.root.inter_feature.presentation.theme.EvolveNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.rootdown.dev.notesapp.root.feature_firebase_msg.presentation.dashboard.core.FirebaseMsgScreen
import com.rootdown.dev.notesapp.root.feature_sandbox_cafe.presentation.pong_tooth.PairingScreen
import com.rootdown.dev.notesapp.root.inter_feature.presentation.util.NavigationItem


@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    private val deviceManager: CompanionDeviceManager by lazy {
        getSystemService(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager
    }
    lateinit var mGoogleSignInClient: GoogleSignInClient
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Firebase.database.useEmulator("10.0.2.2", 9000)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = Firebase.auth
        setContent {
            EvolveNoteAppTheme(darkTheme = false) {
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
                        composable(
                            route = NavigationItem.PairingScreen.route
                        ){
                            PairingScreen(companionDeviceManager = deviceManager)
                        }
                        composable(
                            route = Screen.FirebaseMsgScreen.route,
                        ) {
                            FirebaseMsgScreen()
                        }
                    }
                }
            }
        }
    }
}