package com.davf392.panierlocal.features.member

import com.davf392.panierlocal.data.member.AttendanceStatus
import com.davf392.panierlocal.data.member.Member
import com.davf392.panierlocal.data.member.MemberAttendance
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class MemberAttendancePreviewParameterProvider : PreviewParameterProvider<MemberAttendance> {
    override val values: Sequence<MemberAttendance> = sequenceOf(
        MemberAttendance(
            distributionId = "d1",
            member = Member(
                id = "m1",
                firstName = "Camille",
                lastName = "Benali",
                email = "camillebenali@example.com",
                needsRenewal = true,
                hasArrears = false,
                notes = "Cotisation à régulariser"
            ),
            basketFormulaIds = listOf("b1"),
            status = AttendanceStatus.EXPECTED
        ),
        MemberAttendance(
            distributionId = "d1",
            member = Member(
                id = "m1",
                firstName = "Yassin",
                lastName = "Traoré",
                email = "yassintraore@example.com",
                needsRenewal = true,
                hasArrears = false,
                notes = "Abonnement à renouveler"
            ),
            basketFormulaIds = listOf("b1"),
            status = AttendanceStatus.ABSENT
        ),
        MemberAttendance(
            distributionId = "d1",
            member = Member(
                id = "m1",
                firstName = "Léa",
                lastName = "Chen",
                email = "leachen@example.com",
                needsRenewal = true,
                hasArrears = false
            ),
            basketFormulaIds = listOf("b1"),
            status = AttendanceStatus.COLLECTED
        )
    )
}