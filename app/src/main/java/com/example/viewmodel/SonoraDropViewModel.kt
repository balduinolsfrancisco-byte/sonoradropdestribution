package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class SonoraDropViewModel : ViewModel() {

    private val _artistProfile = MutableStateFlow(SampleData.defaultArtist)
    val artistProfile: StateFlow<ArtistProfile> = _artistProfile.asStateFlow()

    private val _releases = MutableStateFlow(SampleData.initialReleases)
    val releases: StateFlow<List<Release>> = _releases.asStateFlow()

    private val _selectedRelease = MutableStateFlow<Release?>(null)
    val selectedRelease: StateFlow<Release?> = _selectedRelease.asStateFlow()

    private val _splits = MutableStateFlow(SampleData.initialSplits)
    val splits: StateFlow<List<RoyaltySplit>> = _splits.asStateFlow()

    private val _payouts = MutableStateFlow(SampleData.initialPayouts)
    val payouts: StateFlow<List<PayoutTransaction>> = _payouts.asStateFlow()

    private val _availableBalance = MutableStateFlow(845.20)
    val availableBalance: StateFlow<Double> = _availableBalance.asStateFlow()

    private val _pendingBalance = MutableStateFlow(320.40)
    val pendingBalance: StateFlow<Double> = _pendingBalance.asStateFlow()

    private val _analyticsTimeframe = MutableStateFlow("7D")
    val analyticsTimeframe: StateFlow<String> = _analyticsTimeframe.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _releaseFilter = MutableStateFlow("All")
    val releaseFilter: StateFlow<String> = _releaseFilter.asStateFlow()

    fun selectRelease(release: Release?) {
        _selectedRelease.value = release
    }

    fun setFilter(filter: String) {
        _releaseFilter.value = filter
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setTimeframe(tf: String) {
        _analyticsTimeframe.value = tf
    }

    fun addRelease(release: Release) {
        _releases.value = listOf(release) + _releases.value
    }

    fun addSplit(split: RoyaltySplit) {
        _splits.value = _splits.value + split
    }

    fun removeSplit(splitId: String) {
        _splits.value = _splits.value.filter { it.id != splitId }
    }

    fun requestPayout(amount: Double, method: String, destination: String): Boolean {
        if (amount <= 0 || amount > _availableBalance.value) return false
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newTx = PayoutTransaction(
            id = "PO-${Random().nextInt(9000) + 1000}",
            date = dateFormat.format(Date()),
            amount = amount,
            method = method,
            destination = destination,
            status = "Processing",
            reference = "REF-${Random().nextInt(900000) + 100000}-SD"
        )
        _availableBalance.value -= amount
        _payouts.value = listOf(newTx) + _payouts.value
        return true
    }

    fun updateArtistProfile(profile: ArtistProfile) {
        _artistProfile.value = profile
    }
}
