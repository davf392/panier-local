package com.davf392.panierlocal.repository

import com.davf392.panierlocal.data.member.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class MockMemberRepository : IMemberRepository {

    private val _attendances = MutableStateFlow(
        listOf(
            MemberAttendance(
                "dist-001",
                Member(
                    id = "m1",
                    firstName = "Alice",
                    lastName = "Dupont",
                    email = "alice@example.com",
                    needsRenewal = false,
                    hasArrears = false,
                    notes = "Cotisation à régulariser"
                ),
                basketFormulaIds = listOf(element = "b1"),
                status = AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                distributionId = "dist-001",
                member = Member(
                    id = "m2",
                    firstName = "Bob",
                    lastName = "Martin",
                    email = "bob@example.com",
                    needsRenewal = true,
                    hasArrears = false
                ),
                basketFormulaIds = listOf("b2"),
                status = AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                distributionId = "dist-001",
                member = Member(
                    id = "m3",
                    firstName = "Charlie",
                    lastName = "Durand",
                    email = "charlie@example.com",
                    needsRenewal = false,
                    hasArrears = true
                ),
                basketFormulaIds = listOf("b1", "b3"),
                status = AttendanceStatus.EXPECTED
            )
        )
    )

    override fun getAttendancesForDistribution(distributionId: String): Flow<List<MemberAttendance>> {
        return _attendances.map { list -> list.filter { it.distributionId == distributionId } }
    }

    override suspend fun updateAttendanceStatus(
        memberId: String,
        distributionId: String,
        status: AttendanceStatus
    ) {
        val currentList = _attendances.value
        _attendances.value = currentList.map { attendance ->
            if (attendance.member.id == memberId && attendance.distributionId == distributionId) {
                attendance.copy(status = status, updatedAt = kotlinx.datetime.Clock.System.now().toEpochMilliseconds())
            } else {
                attendance
            }
        }
    }
}
