package com.rootdown.dev.notesapp.root.feature_note.presentation.pong_tooth

import android.bluetooth.le.ScanResult
import android.companion.AssociationRequest
import android.companion.BluetoothLeDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.IntentSender
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.util.regex.Pattern

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PairingScreen(
    companionDeviceManager: CompanionDeviceManager
) {
    val (pairingStatus, setPairingStatus) = remember {
        val initialState = if(companionDeviceManager.associations
                .isNotEmpty()) {
            PairingStatus.Paired
        } else {
            PairingStatus.NotPaired
        }
        mutableStateOf(initialState)
    }
    val (deviceAddress, setDeviceAddress) = remember {
        val initialState = companionDeviceManager.associations
            .firstOrNull() ?: "unknown"
        mutableStateOf(initialState)
    }

    val contract = ActivityResultContracts.StartIntentSenderForResult()
    val activityResultLauncher =
        rememberLauncherForActivityResult(contract = contract) {
            it.data
                ?.getParcelableExtra<ScanResult>(CompanionDeviceManager.EXTRA_DEVICE)
                ?.let { scanResult ->
                    val device = scanResult.device
                    setPairingStatus(PairingStatus.Paired)
                    setDeviceAddress(device.address)
                }
        }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Device pairing",
            style = MaterialTheme.typography.h2
        )

        Text(
            "Pairing status: ${pairingStatus.name}",
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            "Device address: $deviceAddress",
            style = MaterialTheme.typography.subtitle2
        )

        val btnEnabled = when (pairingStatus) {
            PairingStatus.Paired, PairingStatus.NotPaired -> true
            else -> false
        }
        Button(
            onClick = {
                Log.w("*PAIR$", "")
                if (pairingStatus == PairingStatus.NotPaired) {
                    val associationRequest = buildAssociationRequest()
                    companionDeviceManager.associate(
                        associationRequest,
                        object : CompanionDeviceManager.Callback() {
                            override fun onDeviceFound(chooserLauncher: IntentSender?) {
                                chooserLauncher?.let {
                                    val request = IntentSenderRequest.Builder(it).build()
                                    activityResultLauncher.launch(request)
                                }
                            }

                            override fun onFailure(error: CharSequence?) {
                                setPairingStatus(PairingStatus.PairingFailed)
                            }
                        }, null)
                } else if (pairingStatus == PairingStatus.Paired) {
                    companionDeviceManager.disassociate(deviceAddress)
                }
            }
        ) {
            val label = when (pairingStatus) {
                PairingStatus.Paired -> "Forget device"
                PairingStatus.NotPaired -> "Start pairing"
                PairingStatus.PairingFailed -> "Error!"
                PairingStatus.Pairing -> "Pairing..."
            }
            Text(label)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun buildAssociationRequest(): AssociationRequest {
    val deviceFilter = BluetoothLeDeviceFilter.Builder()
        .setNamePattern(Pattern.compile("rd_eid"))
        .build()
    return AssociationRequest.Builder()
        .addDeviceFilter(deviceFilter)
        .setSingleDevice(true)
        .build()
}

enum class PairingStatus {
    NotPaired, Pairing, Paired, PairingFailed
}