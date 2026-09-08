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
                Member("m1", "Alice", "Dupont", "alice@example.com", false, false),
                listOf("b1"),
                AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                "dist-001",
                Member("m2", "Bob", "Martin", "bob@example.com", true, false),
                listOf("b2"),
                AttendanceStatus.EXPECTED
            ),
            MemberAttendance(
                "dist-001",
                Member("m3", "Charlie", "Durand", "charlie@example.com", false, true),
                listOf("b1", "b3"),
                AttendanceStatus.EXPECTED
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
