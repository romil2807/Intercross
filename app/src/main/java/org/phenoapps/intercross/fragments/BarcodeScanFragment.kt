package org.phenoapps.intercross.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import org.phenoapps.intercross.MainActivity
import org.phenoapps.intercross.data.*
import org.phenoapps.intercross.databinding.FragmentBarcodeScanBinding
import org.phenoapps.intercross.util.FileUtil
import org.phenoapps.intercross.viewmodels.CrossSharedViewModel
import org.phenoapps.intercross.viewmodels.EventsListViewModel
import org.phenoapps.intercross.viewmodels.SettingsViewModel
import org.phenoapps.intercross.viewmodels.WishlistViewModel
import java.util.*

class BarcodeScanFragment: Fragment() {

    private lateinit var mSharedViewModel: CrossSharedViewModel

    private lateinit var mSettingsViewModel: SettingsViewModel

    private lateinit var mBarcodeScanner: DecoratedBarcodeView

    private lateinit var mEventsListViewModel: EventsListViewModel

    private lateinit var mWishlistViewModel: WishlistViewModel

    private lateinit var mBinding: FragmentBarcodeScanBinding

    private lateinit var mCallback: BarcodeCallback

    private lateinit var mWishlist: List<Wishlist>

    private var mEvents = ArrayList<Events>()

    private var mSettings = Settings()

    private var lastText: String? = null

    private var mOrder = 0

    private var mAllowBlank = false

    private fun isCameraAllowed(): Boolean {

        if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) return true

        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), MainActivity.REQ_CAMERA)

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            with(ViewModelProviders.of(this)) {
                mSharedViewModel = this.get(CrossSharedViewModel::class.java)
            }
        }
        isCameraAllowed()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        mWishlistViewModel = ViewModelProviders.of(this,
                object : ViewModelProvider.NewInstanceFactory() {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return WishlistViewModel(WishlistRepository.getInstance(
                                IntercrossDatabase.getInstance(requireContext()).wishlistDao())) as T

                    }
                }).get(WishlistViewModel::class.java)

        mWishlistViewModel.wishlist.observe(viewLifecycleOwner, Observer{
            it?.let {
                mWishlist = it
            }
        })

        mSettingsViewModel = ViewModelProviders.of(this,
                object : ViewModelProvider.NewInstanceFactory() {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return SettingsViewModel(SettingsRepository.getInstance(
                                IntercrossDatabase.getInstance(requireContext()).settingsDao())) as T

                    }
                }).get(SettingsViewModel::class.java)

        mEventsListViewModel = ViewModelProviders.of(this,
                object : ViewModelProvider.NewInstanceFactory() {

                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return EventsListViewModel(EventsRepository.getInstance(
                                IntercrossDatabase.getInstance(requireContext()).eventsDao())) as T

                    }
                }
        ).get(EventsListViewModel::class.java)

        mBinding =
                FragmentBarcodeScanBinding.inflate(inflater, container, false)

        val orderKey = "org.phenoapps.intercross.CROSS_ORDER"
        val blankKey = "org.phenoapps.intercross.BLANK_MALE_ID"
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        mOrder = (pref.getString(orderKey, "0") ?: "0").toInt()
        mAllowBlank = pref.getBoolean(blankKey, false)
        pref.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            when(key) {
                orderKey -> mOrder = (sharedPreferences.getString(key, "0") ?: "0").toInt()
                blankKey -> mAllowBlank = sharedPreferences.getBoolean(key, false)
            }
        }

        mCallback = object : BarcodeCallback {

            override fun barcodeResult(result: BarcodeResult) {

                if (result.text == null) return // || result.text == lastText) return

                lastText = result.text

                //binding.zxingBarcodeScanner.setStatusText(result.text)

                arguments?.let {
                    when(it.getString("mode")) {
                        "single" -> {
                            mSharedViewModel.lastScan.value = result.text.toString()
                            findNavController().popBackStack()
                        }
                        "search" -> {
                            //mSharedViewModel.lastScan.value = result.text.toString()
                            mEvents.forEach { event ->
                                if (event.eventDbId == result.text.toString()) {
                                    findNavController().navigate(BarcodeScanFragmentDirections.actionToEventFragment(event))
                                }
                            }
                        }
                        "continuous" -> {
                            when (mOrder) {
                                0 -> when {
                                    (mSharedViewModel.female.value ?: "").isEmpty() -> {
                                        mSharedViewModel.female.value = result.text.toString()
                                        mBinding.female.setImageBitmap(result.getBitmapWithResultPoints(Color.RED))
                                        if (mAllowBlank && (mSettings.isUUID || mSettings.isPattern)) {
                                            FileUtil(requireContext()).ringNotification(true)
                                            submitCross()
                                        }
                                        else Handler().postDelayed({
                                            mBarcodeScanner.barcodeView.decodeSingle(mCallback)
                                        }, 2000)
                                    }
                                    ((mSharedViewModel.male.value ?: "").isEmpty() && !mAllowBlank) -> {
                                        mSharedViewModel.male.value = result.text.toString()
                                        mBinding.male.setImageBitmap(result.getBitmapWithResultPoints(Color.BLUE))
                                        if (mSettings.isUUID || mSettings.isPattern) {
                                            FileUtil(requireContext()).ringNotification(true)
                                            submitCross()
                                        }
                                        else Handler().postDelayed({
                                            mBarcodeScanner.barcodeView.decodeSingle(mCallback)
                                        }, 2000)

                                    }
                                    ((mSharedViewModel.name.value ?: "").isEmpty() && !(mSettings.isUUID || mSettings.isPattern)) -> {
                                        mSharedViewModel.name.value = result.text.toString()
                                        mBinding.cross.setImageBitmap(result.getBitmapWithResultPoints(Color.GREEN))
                                        FileUtil(requireContext()).ringNotification(true)
                                        submitCross()
                                    }
                                    else -> {
                                        FileUtil(requireContext()).ringNotification(true)
                                        submitCross()
                                    }
                                }
                                1 -> when {
                                    !mAllowBlank && (mSharedViewModel.male.value ?: "").isEmpty() -> {
                                        mSharedViewModel.male.value = result.text.toString()
                                        mBinding.male.setImageBitmap(result.getBitmapWithResultPoints(Color.BLUE))
                                        Handler().postDelayed({
                                            mBarcodeScanner.barcodeView.decodeSingle(mCallback)
                                        }, 2000)
                                    }
                                    (mSharedViewModel.female.value ?: "").isEmpty() -> {
                                        mSharedViewModel.female.value = result.text.toString()
                                        mBinding.female.setImageBitmap(result.getBitmapWithResultPoints(Color.RED))
                                        if (mSettings.isUUID || mSettings.isPattern) {
                                            FileUtil(requireContext()).ringNotification(true)
                                            submitCross()
                                        }
                                        else Handler().postDelayed({
                                            mBarcodeScanner.barcodeView.decodeSingle(mCallback)
                                        }, 2000)

                                    }
                                    (mSharedViewModel.name.value ?: "").isEmpty() && !(mSettings.isUUID || mSettings.isPattern) -> {
                                        mSharedViewModel.name.value = result.text.toString()
                                        mBinding.cross.setImageBitmap(result.getBitmapWithResultPoints(Color.GREEN))
                                        FileUtil(requireContext()).ringNotification(true)
                                        submitCross()
                                    }
                                    else -> {
                                        FileUtil(requireContext()).ringNotification(true)
                                        submitCross()
                                    }
                                }

                            }
                            ""
                        }
                        else -> ""
                    }
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {

            }

        }

        mSettingsViewModel.settings.observe(viewLifecycleOwner, Observer {
            it?.let {
                mSettings = it
            }
        })

        mEventsListViewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let {
                mEvents = ArrayList(it)
            }
        })

        mSharedViewModel.name.value = ""
        mSharedViewModel.female.value = ""
        mSharedViewModel.male.value = ""

        mBarcodeScanner = mBinding.zxingBarcodeScanner

        mBarcodeScanner.barcodeView.apply {

            cameraSettings.isContinuousFocusEnabled = true

            cameraSettings.isAutoTorchEnabled = true

            cameraSettings.isAutoFocusEnabled = true

            cameraSettings.isBarcodeSceneModeEnabled = true

            decodeSingle(mCallback)
        }

        return mBinding.root
    }

    private fun checkWishlist(f: String, m: String, x: String) {

        var isOnList = false
        var min = 0
        var current = 0
        mWishlist.forEach {
            if (it.femaleDbId == f && it.maleDbId == m) {
                isOnList = true
                min = it.wishMin
                current++
            }
        }
        if (isOnList) {
            mEvents.forEach {
                if (it.femaleObsUnitDbId == f && it.maleOBsUnitDbId == m) {
                    current++
                }
            }
        }

        if (current >= min && min != 0) {
            Snackbar.make(mBinding.root, "Wishlist complete for $f and $m : $current/$min", Snackbar.LENGTH_LONG).show()
        } else Snackbar.make(mBinding.root,
                "New Cross Event! $x added.", Snackbar.LENGTH_SHORT).show()
    }

    fun submitCross() {

        var cross =
        when {
            mSettings.isPattern -> {
                var n = mSettings.number
                mSettings.number += 1
                mSettingsViewModel.update(mSettings)
                "${mSettings.prefix}${n.toString().padStart(mSettings.pad, '0')}${mSettings.suffix}"
            }
            mSettings.isUUID -> {
                UUID.randomUUID().toString()
            }
            else -> mSharedViewModel.name.value ?: String()
        }
        mBarcodeScanner.barcodeView.stopDecoding()

        checkWishlist(mSharedViewModel.female.value ?: String(),
                mSharedViewModel.male.value ?: String(),
                cross)

        mEventsListViewModel.addCrossEvent(cross,
                mSharedViewModel.female.value ?: String(), mSharedViewModel.male.value ?: String())

        mSharedViewModel.name.value = ""
        mSharedViewModel.female.value = ""
        mSharedViewModel.male.value = ""

        Handler().postDelayed({
            mBinding.cross.setImageResource(0)
            mBinding.female.setImageResource(0)
            mBinding.male.setImageResource(0)
            mBarcodeScanner.barcodeView.decodeSingle(mCallback)
        }, 2000)

    }

    override fun onResume() {
        super.onResume()

        mBarcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()

        mBarcodeScanner.pause()
    }

}