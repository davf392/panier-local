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
                    phoneNumber = "0677445533",
                    needsRenewal = false,
                    hasArrears = false,
                ),
                basketFormulaIds = listOf(element = "o5"),
                status = AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                distributionId = "dist-001",
                member = Member(
                    id = "m2",
                    firstName = "Bob",
                    lastName = "Martin",
                    email = "bob@example.com",
                    phoneNumber = "0687192837",
                    notes = "Cotisation à régulariser",
                    needsRenewal = false,
                    hasArrears = false
                ),
                basketFormulaIds = listOf("f4"),
                status = AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                distributionId = "dist-001",
                member = Member(
                    id = "m3",
                    firstName = "Charlie",
                    lastName = "Durand",
                    email = "charlie@example.com",
                    phoneNumber = "0688330923",
                    needsRenewal = false,
                    hasArrears = true
                ),
                basketFormulaIds = listOf("v2", "f2"),
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

    override suspend fun getMemberById(memberId: String): Member? {
        return _attendances.value.find { it.member.id == memberId }?.member
    }

    override suspend fun updateMemberNotes(memberId: String, notes: String) {
        _attendances.value = _attendances.value.map { attendance ->
            if (attendance.member.id == memberId) {
                attendance.copy(member = attendance.member.copy(notes = notes))
            } else {
                attendance
            }
        }
    }

    override suspend fun getAttendanceForMember(memberId: String, distributionId: String): MemberAttendance? {
        return _attendances.value.find { it.member.id == memberId && it.distributionId == distributionId }
    }
}
