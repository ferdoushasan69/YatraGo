package com.dmmeta.nolapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.banner_eight
import nolapp.composeapp.generated.resources.banner_eleven
import nolapp.composeapp.generated.resources.banner_fifteen
import nolapp.composeapp.generated.resources.banner_five
import nolapp.composeapp.generated.resources.banner_four
import nolapp.composeapp.generated.resources.banner_fourteen
import nolapp.composeapp.generated.resources.banner_nine
import nolapp.composeapp.generated.resources.banner_one
import nolapp.composeapp.generated.resources.banner_seven
import nolapp.composeapp.generated.resources.banner_six
import nolapp.composeapp.generated.resources.banner_sixteen
import nolapp.composeapp.generated.resources.banner_ten
import nolapp.composeapp.generated.resources.banner_thirteen
import nolapp.composeapp.generated.resources.banner_three
import nolapp.composeapp.generated.resources.banner_tweleve
import nolapp.composeapp.generated.resources.banner_two
import org.jetbrains.compose.resources.painterResource

@Composable
fun getBannerList(): List<Painter> {
    return listOf(
        painterResource(Res.drawable.banner_one),
        painterResource(Res.drawable.banner_two),
        painterResource(Res.drawable.banner_three),
        painterResource(Res.drawable.banner_four),
        painterResource(Res.drawable.banner_five),
        painterResource(Res.drawable.banner_six),
        painterResource(Res.drawable.banner_seven),
        painterResource(Res.drawable.banner_eight),
        painterResource(Res.drawable.banner_nine),
        painterResource(Res.drawable.banner_ten),
        painterResource(Res.drawable.banner_eleven),
        painterResource(Res.drawable.banner_tweleve),
        painterResource(Res.drawable.banner_thirteen),
        painterResource(Res.drawable.banner_fourteen),
        painterResource(Res.drawable.banner_fifteen),
        painterResource(Res.drawable.banner_sixteen)
    )
}