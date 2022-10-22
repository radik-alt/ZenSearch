package com.five.zensearch.presentation

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.five.zensearch.App.Companion.getToken
import com.five.zensearch.R
import com.five.zensearch.com.five.zensearch.data.datasource.PostRemoteDataSource
import com.five.zensearch.com.five.zensearch.data.dto.UserDTO
import com.five.zensearch.com.five.zensearch.data.repo_impl.PostRepoImpl
import com.five.zensearch.com.five.zensearch.domain.model.PostModel
import com.five.zensearch.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var auth: FirebaseAuth = Firebase.auth

    val error: MutableLiveData<String> = MutableLiveData()
    private val database = Firebase.database
    private val usersReference = database.getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

//        createUser()
//        createPost()
//        cancelPost()

        requestPermissionLauncher.launch(arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR))

        if (ContextCompat.checkSelfPermission(
                requireNotNull(applicationContext),
                Manifest.permission.READ_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireNotNull(applicationContext),
                Manifest.permission.WRITE_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        return true
    }


    /**
    CREATE USER
     **/
    fun createUser() {
        auth.createUserWithEmailAndPassword("v@emiryan.ru", "qwerty").addOnSuccessListener {
            val user =
                UserDTO(it.user?.uid, "Vova", "Emiryan", 19, "Hach", false, getToken(), "img")
            usersReference.child(user.id.toString()).setValue(user)
        }.addOnFailureListener {
            error.value = it.message
        }
    }

    fun subunsub() {
        val repo = PostRepoImpl(PostRemoteDataSource())

        repo.subscribeUser("EcvBeMsL37W9QRhW3F9fHjAQNtl2","-NEvNRfMLngO1oskX8ju")
        repo.unsubscribeUser("EcvBeMsL37W9QRhW3F9fHjAQNtl2","-NEvNRfMLngO1oskX8ju")
    }

    fun cancelPost() {
        val repo = PostRepoImpl(PostRemoteDataSource())

        repo.cancelPost("-NEyjotGCw28VvqZ4TLh")
    }

    fun createPost() {
        val repo = PostRepoImpl(PostRemoteDataSource())

        val post = PostModel(
            name = "name",
            address = "address",
            description = "description",
            tags = listOf("tags"),
            image = "image",
            isConfirmed = true,
            date = Date(),
            creatorId = "EcvBeMsL37W9QRhW3F9fHjAQNtl2" //auth.currentUser.uid
        )

        repo.createPost(post)
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    fun createReminder() {
        val cr: ContentResolver = requireNotNull(applicationContext).contentResolver
        val values = ContentValues()

        var startMillis: Long = Calendar.getInstance().run {
            set(2022, 9, 14, 7, 30, 0)
            timeInMillis
        }

        var endMillis: Long = Calendar.getInstance().run {
            set(2022, 9, 16, 8, 30, 59)
            timeInMillis
        }

        val cal = Calendar.getInstance()

        cal[Calendar.MONTH] = 9
        cal[Calendar.YEAR] = 2022
        cal[Calendar.DAY_OF_MONTH] = 1
        cal[Calendar.HOUR_OF_DAY] = 13
        cal[Calendar.MINUTE] = 15

        values.put(CalendarContract.Events.DTSTART, cal.timeInMillis)
/*        cal[Calendar.HOUR_OF_DAY] += 2
        values.put(CalendarContract.Events.DTEND, cal.timeInMillis)*/
        values.put(CalendarContract.Events.TITLE, "title")
        values.put(CalendarContract.Events.DESCRIPTION, "description")

        val timeZone = TimeZone.getDefault()
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.id)

        // default calendar
        values.put(CalendarContract.Events.CALENDAR_ID, 1)

        values.put(CalendarContract.Events.HAS_ALARM, true)

        // insert event to calendar
        val uri: Uri? = cr.insert(CalendarContract.Events.CONTENT_URI, values)

        val eventId: Long = uri?.getLastPathSegment()?.toLong() ?: 0L

        val reminder = ContentValues()
        reminder.put(CalendarContract.Reminders.EVENT_ID, eventId)
        reminder.put(CalendarContract.Reminders.MINUTES, 10)
        reminder.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminder)
    }
}
