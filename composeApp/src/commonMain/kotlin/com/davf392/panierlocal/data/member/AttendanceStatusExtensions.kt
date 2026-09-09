package com.davf392.panierlocal.data.member

import org.jetbrains.compose.resources.StringResource
import panierlocal.composeapp.generated.resources.Res
import panierlocal.composeapp.generated.resources.status_absent
import panierlocal.composeapp.generated.resources.status_collected
import panierlocal.composeapp.generated.resources.status_expected
import panierlocal.composeapp.generated.resources.status_redirected

fun AttendanceStatus.toResource(): StringResource {
    return when (this) {
        AttendanceStatus.EXPECTED -> Res.string.status_expected
        AttendanceStatus.COLLECTED -> Res.string.status_collected
        AttendanceStatus.ABSENT -> Res.string.status_absent
        AttendanceStatus.REDIRECTED -> Res.string.status_redirected
    }
}
