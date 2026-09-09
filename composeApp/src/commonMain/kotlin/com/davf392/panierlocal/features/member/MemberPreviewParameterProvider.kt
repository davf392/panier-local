package com.davf392.panierlocal.features.member

import com.davf392.panierlocal.data.member.Member
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class MemberPreviewParameterProvider : PreviewParameterProvider<Member> {
    override val values: Sequence<Member> = sequenceOf(
        Member(
            id = "m1",
            firstName = "Camille",
            lastName = "Benali",
            email = "camille.benali@example.com",
            phoneNumber = "0601020304",
            needsRenewal = false,
            hasArrears = false,
            notes = "Cotisation à jour, panier sans gluten."
        ),
        Member(
            id = "m2",
            firstName = "Yassin",
            lastName = "Traoré",
            email = "yassin.traore@example.com",
            phoneNumber = "0612345678",
            needsRenewal = true,
            hasArrears = true,
            notes = "Chèque de régularisation en attente."
        ),
        Member(
            id = "m3",
            firstName = "Léa",
            lastName = "Chen",
            email = "lea.chen@example.com",
            needsRenewal = false,
            hasArrears = false,
            notes = null
        )
    )
}