package com.davf392.panierlocal.repository

import com.davf392.panierlocal.data.member.MemberAttendance
import kotlinx.coroutines.flow.Flow

interface IMemberRepository {
    fun getAttendancesForDistribution(distributionId: String): Flow<List<MemberAttendance>>
    suspend fun updateAttendanceStatus(memberId: String, distributionId: String, status: com.davf392.panierlocal.data.member.AttendanceStatus)
    suspend fun getMemberById(memberId: String): com.davf392.panierlocal.data.member.Member?
    suspend fun updateMemberNotes(memberId: String, notes: String)
}
