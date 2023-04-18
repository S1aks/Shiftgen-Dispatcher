package com.s1aks.shiftgen_dispatcher.data

import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.LoginResponse
import com.s1aks.shiftgen_dispatcher.domain.models.TokensData

internal fun LoginResponse.toTokensData(): TokensData =
    TokensData(this.accessToken, this.refreshToken)